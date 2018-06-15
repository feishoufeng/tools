package cc.zytrip.serviceimpl.code;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZHANGYANG on 2016/10/30.
 */
public class CommonCodeGenerator {

	public Map<String, String> spaceMap = new HashMap<String, String>();

	public void createPath(String workspace, String thPack) {
		String thDir = "/" + thPack.replace(".", "/");
		// spaceMap.put("modelPath",workspace+"/com/cbg/model/"+thDir);
		spaceMap.put("modelPath", workspace + "/com/cbg/model/");
		spaceMap.put("xmlPath", workspace + "/com/cbg/dao/" + thDir);
		spaceMap.put("mapperPath", workspace + "/com/cbg/dao/" + thDir);
		// spaceMap.put("servicePath",workspace+"/com/cbg/service/impl/"+thDir);
		spaceMap.put("servicePath", workspace + "/com/cbg/service/impl/");
		// spaceMap.put("modelPack","com.cbg.model."+thPack);
		spaceMap.put("modelPack", "com.cbg.model");
		spaceMap.put("mapperPack", "com.cbg.dao." + thPack);
		// spaceMap.put("servicePack","com.cbg.service.impl."+thPack);
		spaceMap.put("servicePack", "com.cbg.service.impl");
		return;
	}
}
