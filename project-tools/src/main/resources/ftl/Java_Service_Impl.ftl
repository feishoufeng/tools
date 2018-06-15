package ${servicePack};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${mapperPack}.${modelName}Dao;
import ${modelPack}.${modelName};
import com.cbg.service.BaseService;

@Service("${serviceName}Service")
public class ${modelName}Service extends BaseService<${modelName},Integer> {

	@Autowired
	private ${modelName}Dao ${serviceName}Dao;
}