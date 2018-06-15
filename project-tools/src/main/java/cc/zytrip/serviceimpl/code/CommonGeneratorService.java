package cc.zytrip.serviceimpl.code;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.zytrip.dao.mapper.CommonSqlMapper;
import cc.zytrip.service.code.ICommonCodeGenerator;
import freemarker.template.Configuration;

/**
 * Created by ASUS on 2016/10/15.
 */
@Service("commonGeneratorService")
public class CommonGeneratorService {
	private static final Logger logger = LogManager.getLogger(CommonGeneratorService.class);

	// @Value("${templatesDir}")
	public String templatesDir = "E:/zytrip/project-tools";

	private static final String FIELD_TYPE_STRING = String.class.getSimpleName();
	private static final String FIELD_TYPE_INTEGER = Integer.class.getSimpleName();
	private static final String FIELD_TYPE_LONG = Long.class.getSimpleName();
	private static final String FIELD_TYPE_DOUBLE = Double.class.getSimpleName();
	private static final String FIELD_TYPE_DATE = Date.class.getSimpleName();
	private static final String FIELD_TYPE_BLOB = Blob.class.getSimpleName();
	private static final String FIELD_TYPE_BOOLEAN = Boolean.class.getSimpleName();

	private Map<String, Object> targetClazz = new HashMap<String, Object>();

	// private ICommonCodeGenerator[] objs = {new CommonModelGenerator(workspace),
	// new CommonXmlMapperGenerator(workspace), new
	// CommonJavaMapperGenerator(workspace)};

	private Configuration configuration; // FreeMarker配置
	private String pageEncoding = "UTF-8";

	@Autowired
	private CommonSqlMapper commonSqlMapper;

	/**
	 * 创建和调整配置,这个在生命周期中只做一次
	 */
	public CommonGeneratorService() {
		configuration = new Configuration(Configuration.VERSION_2_3_23);
		try {
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().replaceAll("%20",
					" ");
			configuration.setDirectoryForTemplateLoading(new File(path.substring(1) + "ftl"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		configuration.setEncoding(Locale.getDefault(), pageEncoding);
	}

	/**
	 * 获取实体bean名称
	 *
	 * @param tableName
	 * @return
	 */
	private String tableToBeanName(String tableName, boolean firstUpperCase) {
		StringBuffer sb = new StringBuffer(tableName);
		if (firstUpperCase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '_') {
				sb.setCharAt(i + 1, Character.toUpperCase(sb.charAt(i + 1)));
				sb.deleteCharAt(i);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取字段名称
	 *
	 * @param columnName
	 * @return
	 */
	private String columnToPropertyName(String columnName, boolean firstUpperCase) {
		StringBuffer sb = new StringBuffer(columnName);
		if (firstUpperCase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '_') {
				sb.setCharAt(i + 1, Character.toUpperCase(sb.charAt(i + 1)));
				sb.deleteCharAt(i);
			}
		}
		return sb.toString();
	}

	private List<Map<String, String>> getMysqlFields(String tableName) {
		String sql = "SELECT column_name, data_type, column_comment "
				+ "FROM Information_schema.columns WHERE table_Name='" + tableName
				+ "' AND table_schema = (SELECT DATABASE())";

		List<Map<String, Object>> list = commonSqlMapper.selectBySQL(sql);

		List<Map<String, String>> fields = new ArrayList<Map<String, String>>();

		for (Map<String, Object> map : list) {
			String fieldName = map.get("column_name").toString();
			String fieldType = map.get("data_type").toString().toLowerCase();
			String fieldRemark = map.get("column_comment").toString();

			String propertyName = columnToPropertyName(fieldName.toLowerCase(), false);
			String propertyType = mysqlSqlType2JavaType(fieldType);

			// 替换get/set方法中的属性名
			String propertyNameGS = columnToPropertyName(fieldName.toLowerCase(), true);

			Map<String, String> fieldMap = new HashMap<String, String>();
			fieldMap.put("name", fieldName);
			fieldMap.put("type", mysqlSqlType2MapperType(fieldType));
			fieldMap.put("remark", fieldRemark);
			fieldMap.put("propertyName", propertyName);
			fieldMap.put("propertyType", propertyType);
			fieldMap.put("propertyNameGS", propertyNameGS);
			fields.add(fieldMap);
		}
		return fields;
	}

	private List<Map<String, String>> getPostgresqlFields(String tableName) {
		String sql = "SELECT " + "attrelid :: regclass," + " attname," + " atttypid :: regtype, " + " description "
				+ "FROM" + " pg_attribute " + " LEFT JOIN pg_description on  (attrelid = objoid and attnum=objsubid) "
				+ " WHERE " + " attnum > 0 " + " AND attrelid = '" + tableName + "' :: regclass "
				+ "and atttypid!='-' :: regtype";

		List<Map<String, Object>> list = commonSqlMapper.selectBySQL(sql);

		List<Map<String, String>> fields = new ArrayList<Map<String, String>>();

		for (Map<String, Object> map : list) {
			String fieldName = map.get("attname").toString();
			String fieldType = map.get("atttypid").toString().toLowerCase();

			String fieldRemark = "";
			if (map.get("description") != null) {
				fieldRemark = map.get("description").toString();
			}

			String propertyName = columnToPropertyName(fieldName.toLowerCase(), false);
			String propertyType = postgresqlSqlType2JavaType(fieldType);

			// 替换get/set方法中的属性名
			String propertyNameGS = columnToPropertyName(fieldName.toLowerCase(), true);

			Map<String, String> fieldMap = new HashMap<String, String>();
			fieldMap.put("name", fieldName);
			fieldMap.put("type", postgresqlSqlType2MapperType(fieldType));
			fieldMap.put("remark", fieldRemark);
			fieldMap.put("propertyName", propertyName);
			fieldMap.put("propertyType", propertyType);
			fieldMap.put("propertyNameGS", propertyNameGS);
			fields.add(fieldMap);
		}
		return fields;

	}

	private String getPostgresqlTableDescription(String tableName) {
		String sql = "SELECT DISTINCT " + "   description " + "FROM " + "   pg_attribute "
				+ "LEFT JOIN pg_description ON attrelid = objoid " + "WHERE " + "   objsubid = 0 " + "AND attrelid = '"
				+ tableName + "' :: regclass";
		List<Map<String, Object>> list = commonSqlMapper.selectBySQL(sql);
		if (list.size() == 0)
			return "";
		else
			return String.valueOf(list.get(0).get("description"));
	}

	private String mysqlSqlType2MapperType(String sqlType) {
		String mapperType = null;
		if ("int".equalsIgnoreCase(sqlType) || "tinyint".equalsIgnoreCase(sqlType)) {
			mapperType = "INTEGER";
		} else if ("long".equalsIgnoreCase(sqlType)) {
			mapperType = "DOUBLE";
		} else if (("decimal".equals(sqlType)) || ("number".equals(sqlType))) {
			mapperType = "DOUBLE";
		} else if (("datetime".equals(sqlType)) || ("date".equals(sqlType))) {
			mapperType = "DATE";
		} else if (("image".equals(sqlType)) || ("blob".equals(sqlType))) {
			mapperType = "BLOB";
		} else {
			mapperType = "VARCHAR";
		}
		return mapperType;
	}

	private String postgresqlSqlType2MapperType(String sqlType) {
		String mapperType = null;
		if ("integer".equalsIgnoreCase(sqlType) || ("smallint".equals(sqlType)) || ("bigint".equals(sqlType))) {
			mapperType = "INTEGER";
		} else if ("long".equalsIgnoreCase(sqlType)) {
			mapperType = "DOUBLE";
		} else if ("boolean".equalsIgnoreCase(sqlType)) {
			mapperType = "BIT";
		} else if (("decimal".equals(sqlType)) || ("numeric".equals(sqlType)) || ("real".equals(sqlType))
				|| (sqlType.indexOf("double")) > -1) {
			mapperType = "DOUBLE";
		} else if (("datetime".equals(sqlType)) || ("date".equals(sqlType)) || (sqlType.indexOf("timestamp")) > -1) {
			mapperType = "DATE";
		} else if (("image".equals(sqlType)) || ("blob".equals(sqlType))) {
			mapperType = "BLOB";
		} else {
			mapperType = "VARCHAR";
		}
		return mapperType;
	}

	private String mysqlSqlType2JavaType(String sqlType) {
		String propType = null;
		if ("int".equalsIgnoreCase(sqlType) || "tinyint".equalsIgnoreCase(sqlType)) {
			propType = FIELD_TYPE_INTEGER;
		} else if ("long".equalsIgnoreCase(sqlType)) {
			propType = FIELD_TYPE_LONG;
		} else if (("decimal".equals(sqlType)) || ("number".equals(sqlType)) || ("double".equals(sqlType))) {
			propType = FIELD_TYPE_DOUBLE;
		} else if (("datetime".equals(sqlType)) || ("date".equals(sqlType)) || ("timestamp".equals(sqlType))) {
			propType = FIELD_TYPE_DATE;
		} else if (("image".equals(sqlType)) || ("blob".equals(sqlType))) {
			propType = FIELD_TYPE_BLOB;
		} else {
			propType = FIELD_TYPE_STRING;
		}
		return propType;
	}

	private String postgresqlSqlType2JavaType(String sqlType) {
		String propType = null;

		if ("integer".equalsIgnoreCase(sqlType) || ("smallint".equals(sqlType)) || ("bigint".equals(sqlType))) {
			propType = FIELD_TYPE_INTEGER;
		} else if ("long".equalsIgnoreCase(sqlType)) {
			propType = FIELD_TYPE_LONG;
		} else if ("boolean".equalsIgnoreCase(sqlType)) {
			propType = FIELD_TYPE_BOOLEAN;
		} else if (("decimal".equals(sqlType)) || ("numeric".equals(sqlType)) || ("real".equals(sqlType))
				|| (sqlType.indexOf("double")) > -1) {
			propType = FIELD_TYPE_DOUBLE;
		} else if (("datetime".equals(sqlType)) || ("date".equals(sqlType)) || (sqlType.indexOf("timestamp")) > -1) {
			propType = FIELD_TYPE_DATE;
		} else if (("image".equals(sqlType)) || ("blob".equals(sqlType))) {
			propType = FIELD_TYPE_BLOB;
		} else {
			// else if(("character varying".equals(sqlType)) || ("text".equals(sqlType)) ||
			// ("character".equals(sqlType))) {
			propType = FIELD_TYPE_STRING;
		}
		return propType;
	}

	public void createCode(String tableName, String DBMS, List<ICommonCodeGenerator> objs) {
		String modelName = tableName;
		String tableNameM = tableName;
		if (tableName.startsWith("t_") && !tableName.startsWith("t_o_")) {
			modelName = tableName.substring(2);
		} else if (tableName.startsWith("zx_")) {
			modelName = tableName.substring(3);
		} else if (tableName.startsWith("t_o_")) {
			modelName = tableName.substring(7);
		}
		targetClazz.put("tableName", tableNameM);
		targetClazz.put("modelName", tableToBeanName(modelName, true));
		if (null != DBMS && "mysql".equals(DBMS.toLowerCase())) {
			targetClazz.put("fields", getMysqlFields(tableName));
			targetClazz.put("tableDesc", "");
		} else {
			targetClazz.put("fields", getPostgresqlFields(tableName));
			targetClazz.put("tableDesc", getPostgresqlTableDescription(tableName));
		}
		targetClazz.put("serviceName", tableToBeanName(modelName, false));
		for (ICommonCodeGenerator codeGenerator : objs) {
			try {
				codeGenerator.excute(configuration, targetClazz);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}