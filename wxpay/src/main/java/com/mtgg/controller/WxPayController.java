package com.mtgg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.IpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.ext.kit.StrKit;
import com.jpay.vo.AjaxResult;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.jpay.weixin.api.WxPayApiConfigKit;
import com.mtgg.entity.H5ScencInfo;
import com.mtgg.entity.H5ScencInfo.H5;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/wc")
public class WxPayController {


//	@Autowired
//	private WxPayBean wxPayBean;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private AjaxResult result = new AjaxResult();
	private static final String appID="wx2271404c37fe9a1a";
	private static final String secret="";
	private static final String mchID="1398829602";
	private static final String partnerKey="5qdHetmnsgAqAnQt2N2FajtluyL8PsbW";
	private String notify_url = "http://store.santaitech.com/pay/weChat/pub/notice";
	
	public static WxPayApiConfig getApiConfig() {
		return WxPayApiConfig.New()
				       .setAppId(appID)
				       .setMchId(mchID)
				       .setPaternerKey(partnerKey)
				       .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
	}
	
	
	/**
	 * 微信H5 支付--------------------好使
	 * 注意：必须再web页面中发起支付且域名已添加到开发配置中
	 */
	@RequestMapping(value ="/pay.do",method = {RequestMethod.POST,RequestMethod.GET})
	public void wapPay(HttpServletRequest request,HttpServletResponse response){
		System.out.println("--pay start--");
		String ip = IpKit.getRealIp(request);
		if (com.jpay.ext.kit.StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		
		H5ScencInfo sceneInfo = new H5ScencInfo();
		
		H5 h5_info = new H5();
		h5_info.setType("Wap");
		//此域名必须在商户平台--"产品中心"--"开发配置"中添加
		
		h5_info.setWap_url("http://www.xxx.com");
		h5_info.setWap_name("公司官网");
		sceneInfo.setH5_info(h5_info);
		//WxPayApiConfig wxPayApiConfig=getApiConfig();
		Map<String, String> params=WxPayApiConfig.New()
				                           .setAppId(appID)
				                           .setMchId(mchID)
				                           .setBody("IJPay H5支付测试  -By Javen")
				                           .setSpbillCreateIp(ip)
				                           .setTotalFee("520")
				                           .setTradeType(WxPayApi.TradeType.MWEB)
				                           .setNotifyUrl(notify_url)
				                           .setPaternerKey(partnerKey)
				                           .setOutTradeNo(String.valueOf(System.currentTimeMillis()))
				                           .setSceneInfo("{\"h5_info\": {\"type\":\"IOS\",\"app_name\": \"mtgg\",\"package_name\": \"com.tencent.tmgp.sgame\"}}")
				                           .setAttach("IJPay H5支付测试  -By Javen")
				                           .build();
		String xmlResult = WxPayApi.pushOrder(false,params);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			log.error("return_code>"+return_code+" return_msg>"+return_msg);
			throw new RuntimeException(return_msg);
		}
		String result_code = result.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			log.error("result_code>"+result_code+" return_msg>"+return_msg);
			throw new RuntimeException(return_msg);
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
		
		String prepay_id = result.get("prepay_id");
		String mweb_url = result.get("mweb_url");
		
		log.info("prepay_id:"+prepay_id+" mweb_url:"+mweb_url);
		try {
			response.sendRedirect(mweb_url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 公众号支付
	 */
	@RequestMapping(value ="/gzhPay.do",method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView webPay(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("------------------gzhPay start-------------------");
		ModelAndView mv=new ModelAndView();
		String total_fee = request.getParameter("total_fee");
		//获取用户的code
		String code = request.getParameter("code");
		
		String openId=null;
		try {
			List<Object> list = accessToken(code);
			openId=list.get(1).toString();
		} catch (IOException e) {
			System.out.println("--openid获取错误");
		}
		//openid可以通过网页授权获取存到session中从而获取，IJPay是这样做的，我是另一种方式
		 /*openId，采用 网页授权获取 access_token API：SnsAccessTokenApi获取
		String openId = (String) request.getSession().getAttribute("openId");*/
		if (StrKit.isBlank(openId)) {
			return mv;
		}
		if (StrKit.isBlank(total_fee)) {
			return mv;
		}
		String ip = IpKit.getRealIp(request);
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		WxPayApiConfigKit.putApiConfig(getApiConfig());
		Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig()
				                             .setAttach("IJPay 公众号支付测试  -By Javen")
				                             .setBody("IJPay 公众号支付测试  -By Javen")
				                             .setOpenId(openId)
				                             .setSpbillCreateIp(ip)
				                             .setTotalFee(total_fee)
				                             .setTradeType(WxPayApi.TradeType.JSAPI)
				                             .setNotifyUrl(notify_url)
				                             .setOutTradeNo(String.valueOf(System.currentTimeMillis()))
				                             .build();
		String xmlResult = WxPayApi.pushOrder(false,params);
		log.info(xmlResult);
		Map<String, String> resultMap = PaymentKit.xmlToMap(xmlResult);
		
		String return_code = resultMap.get("return_code");
		String return_msg = resultMap.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			return mv;
		}
		String result_code = resultMap.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			return mv;
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
		String prepay_id = resultMap.get("prepay_id");
		String timeStamp=String.valueOf(System.currentTimeMillis() / 1000);
		String nonce_str=String.valueOf(System.currentTimeMillis());
		String packages = "prepay_id="+prepay_id;
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appId", appID);
		packageParams.put("timeStamp", timeStamp);
		packageParams.put("nonceStr", nonce_str);
		packageParams.put("package", "prepay_id=" + prepay_id);
		packageParams.put("signType", "MD5");
		String packageSign = PaymentKit.createSign(packageParams, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
		packageParams.put("paySign", packageSign);
		//下面可以通过Map传入参数获取json字符串，其形式和上面一样，传到前台可以转为json形式再用，也是一个不错的方式，可以参考IJPay
		/*Map<String, String> packageParams = PaymentKit.prepayIdCreateSign(prepay_id);
		String jsonStr = JSON.toJSONString(packageParams);
		mv.addObject("jsonStr",jsonStr);*/
		mv.addObject("appid",  appID);
		mv.addObject("timeStamp", timeStamp);
		mv.addObject("nonceStr", nonce_str);
		mv.addObject("packageValue", packages);
		mv.addObject("paySign", packageSign);
		mv.addObject("success","ok");
		mv.setViewName("pay");
		return mv;
	}
	/**
	 * 通过微信用户的code换取网页授权access_token
	 * @return
	 * @throws IOException
	 * @throws
	 */
	public List<Object> accessToken(String code) throws IOException {
		List<Object> list = new ArrayList<Object>();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				             +  appID + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpResponse res = client.execute(post);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = res.getEntity();
			String str = EntityUtils.toString(entity, "utf-8");
			ObjectMapper mapper=new ObjectMapper();
			Map<String,Object> jsonOb=mapper.readValue(str, Map.class);
			list.add(jsonOb.get("access_token"));
			list.add(jsonOb.get("openid"));
		}
		return list;
	}
	
	
	@RequestMapping(value = "/pay_notify.do",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String pay_notify(HttpServletRequest request) {
		// 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
		
		String xmlMsg = HttpKit.readData(request);
		System.out.println("pay notice---------"+xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
//		String appid  = params.get("appid");
//		//商户号
//		String mch_id  = params.get("mch_id");
		String result_code  = params.get("result_code");
//		String openId      = params.get("openid");
//		//交易类型
//		String trade_type      = params.get("trade_type");
//		//付款银行
//		String bank_type      = params.get("bank_type");
//		// 总金额
//		String total_fee     = params.get("total_fee");
//		//现金支付金额
//		String cash_fee     = params.get("cash_fee");
//		// 微信支付订单号
//		String transaction_id      = params.get("transaction_id");
//		// 商户订单号
//		String out_trade_no      = params.get("out_trade_no");
//		// 支付完成时间，格式为yyyyMMddHHmmss
//		String time_end      = params.get("time_end");
		
		/////////////////////////////以下是附加参数///////////////////////////////////
		
		String attach      = params.get("attach");
//		String fee_type      = params.get("fee_type");
//		String is_subscribe      = params.get("is_subscribe");
//		String err_code      = params.get("err_code");
//		String err_code_des      = params.get("err_code_des");
		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 避免已经成功、关闭、退款的订单被再次更新
//		Order order = Order.dao.getOrderByTransactionId(transaction_id);
//		if (order==null) {
		WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig());
		if(PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())){
			if (("SUCCESS").equals(result_code)) {
				//更新订单信息
				log.warn("更新订单信息:"+attach);
				//发送通知等
				Map<String, String> xml = new HashMap<String, String>();
				xml.put("return_code", "SUCCESS");
				xml.put("return_msg", "OK");
				return PaymentKit.toXml(xml);
			}
		}
//		}
		
		return null;
	}
	
}
