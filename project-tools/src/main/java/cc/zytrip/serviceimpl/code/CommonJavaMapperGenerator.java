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
public class CommonJavaMapperGenerator extends CommonCodeGenerator implements ICommonCodeGenerator {
    private String pageEncoding = "UTF-8";

    private String targetFileDir;

    public CommonJavaMapperGenerator(String workspace,String thirdPack) {
        workspace += "/src/main/java";
        if(super.spaceMap.get("mapperPath")==null){
            super.createPath(workspace,thirdPack);
        }
        this.targetFileDir = super.spaceMap.get("mapperPath");
        File dir = new File(this.targetFileDir);
        if(!dir.exists() || !dir.isDirectory())
            dir.mkdirs();
    }

    @Override
    public void excute(Configuration conf, Map<String, Object> clazz) throws Exception {
		/* 1，获取模板 */
        Template temp = conf.getTemplate("Java_Mapper.ftl", pageEncoding);
        clazz.putAll(super.spaceMap);
        String fileName = targetFileDir + "/"+ clazz.get("modelName") + "Dao.java";
        File target = new File(fileName);
        Writer out = new OutputStreamWriter(new FileOutputStream(target), pageEncoding);
        temp.process(clazz, out);
        out.flush();
        out.close();
    }
}
