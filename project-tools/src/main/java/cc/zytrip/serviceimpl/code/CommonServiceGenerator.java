package cc.zytrip.serviceimpl.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import cc.zytrip.service.code.ICommonCodeGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;


/**
 * Created by ASUS on 2016/10/15.
 */
public class CommonServiceGenerator extends CommonCodeGenerator implements ICommonCodeGenerator {
    private String pageEncoding = "UTF-8";

    private String targetFileDir;

    public CommonServiceGenerator(String workspace,String thirdPack) {
        workspace += "/src/main/java";
        if(super.spaceMap.get("servicePath")==null){
            super.createPath(workspace,thirdPack);
        }
        this.targetFileDir = super.spaceMap.get("servicePath");
        File dir = new File(this.targetFileDir);
        if(!dir.exists() || !dir.isDirectory())
            dir.mkdirs();
    }

    @Override
    public void excute(Configuration conf, Map<String, Object> clazz) throws Exception {
		/* 1，获取模板 */
        Template temp = conf.getTemplate("Java_Service_Impl.ftl", pageEncoding);
        clazz.putAll(super.spaceMap);
        String fileName = targetFileDir + "/"+ clazz.get("modelName") + "Service.java";
        File target = new File(fileName);
        Writer out = new OutputStreamWriter(new FileOutputStream(target), pageEncoding);
        temp.process(clazz, out);
        out.flush();
        out.close();
    }
}
