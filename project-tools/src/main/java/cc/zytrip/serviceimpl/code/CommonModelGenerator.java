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
public class CommonModelGenerator extends CommonCodeGenerator  implements ICommonCodeGenerator {
    private String pageEncoding = "UTF-8";

    private String targetFileDir;

    public CommonModelGenerator(String workspace,String thirdPack) {
        workspace += "/src/main/java";
        if(super.spaceMap.get("modelPath")==null){
            super.createPath(workspace,thirdPack);
        }
        this.targetFileDir = super.spaceMap.get("modelPath");
        File dir = new File(this.targetFileDir);
        if(!dir.exists() || !dir.isDirectory())
            dir.mkdirs();
    }

    // @Autowired
    // private SqlSessionFactoryBean sqlSessionFactory;

    // private Connection conn = null;

//	@Override
//	public void createCode(String tableName) throws Exception {
    // getConnection();
    // DatabaseMetaData dbmd = conn.getMetaData();
    // ResultSet rs = dbmd.getColumns(null, null, tableName, null);
    // while (rs.next()) {
    // System.out.println(rs.getString("COLUMN_NAME") + "----"
    // + rs.getString("REMARKS"));
    // }

	/* 1，获取模板 */
//        Template temp = configuration.getTemplate("Java_Entity.ftl", pageEncoding);
//        temp.setEncoding(pageEncoding);
//
    // // 填充Map
//        Map<String, Object> targetClazz = new HashMap<String, Object>();
//        targetClazz.put("modelName", tableToBeanName(tableName));
//        getFields(tableName);
//        targetClazz.put("fields", fields);
//        targetClazz.put("getSetFields", getSetFields);
//
//        String targetFileDir = srcDir + "/cn";
//        String fileName = targetFileDir + "/"+ tableToBeanName(tableName) + ".java";
//        File target = new File(fileName);
//        Writer out = new OutputStreamWriter(new FileOutputStream(target), pageEncoding);
//        temp.process(targetClazz, out);
//        out.flush();
//        out.close();
//	}

    // private void getConnection() throws Exception {
    // SqlSession sqlSession = sqlSessionFactory.getObject().openSession();
    // if (conn == null) {
    // conn = sqlSession.getConnection();
    // }
    // }

    @Override
    public void excute(Configuration conf, Map<String, Object> clazz) throws Exception {
		/* 1，获取模板 */
        Template temp = conf.getTemplate("Java_Entity.ftl", pageEncoding);
        clazz.putAll(super.spaceMap);
        String fileName = targetFileDir + "/"+ clazz.get("modelName") + ".java";
        File target = new File(fileName);
        Writer out = new OutputStreamWriter(new FileOutputStream(target), pageEncoding);
        temp.process(clazz, out);
        out.flush();
        out.close();
    }
}
