package cc.zytrip.service;

import cc.zytrip.model.Property;
import com.alibaba.fastjson.JSON;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TanTest {

    @Test
    public void getDigital() throws Exception{
        String url="http://www.tansoole.com/";
        Document doc = Jsoup.connect(url).get();
        Elements sideList = doc.select(".sidelist");
        List<Property> propertyList = new ArrayList<>();
        for(Element side:sideList){
            Elements dls = side.select("dl");
            for(Element dl:dls){
                Elements lis = dl.select("li");
                for(Element li:lis){
                    Property p = new Property();
                    p.setMenuName(li.select("a").first().text());
                    p.setUrl(li.select("a").first().attr("href"));
                    propertyList.add(p);
                }
            }
        }

        propertyList.stream().filter(p->p.getUrl()!=null&&!"".equals(p.getUrl())).forEach(p->{
            try {
                System.out.println(p.getMenuName());
                Document doc1 = Jsoup.connect(p.getUrl()).get();
                if(doc1.select("ul.list").size()>0) {
                    String url1 = "http://www.tansoole.com" + doc1.select("ul.list").first().select("li").first().select("div.p_img").first().select("a").first().attr("href");
                    Document doc2 = Jsoup.connect(url1).get();
                    if(doc2.select("#propertyParameterContent").size()==1){
                        Elements pros = doc2.select("#propertyParameterContent").first().select(".proTitleA");
                        for(Element pro:pros){
                            String name=pro.text();
                            if(name.endsWith(":")){
                                name = name.substring(0,name.length()-1);
                            }
                            p.addNames(name);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            p.setUrl(null);
        });
        String res = JSON.toJSONString(propertyList);
        System.out.println(res);
        System.out.println("结束");
        return;
    }

}
