package cc.zytrip.serviceimpl.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.zytrip.dao.mapper.CommonSqlMapper;
import cc.zytrip.service.code.ICommonCodeGenerator;

/**
 * Created by ZHANGYANG on 2016/10/19.
 */
@Service("codeService")
public class CodeService {

    @Autowired
    private CommonSqlMapper commonSqlMapper;

    @Autowired
    private  CommonGeneratorService commonGeneratorService;

    public List<String> queryTableName() {
        String sql = "SELECT   tablename:: regclass as table_name   FROM   pg_tables " +
                "WHERE    schemaname='public' " +
                "ORDER   BY   tablename";
        List<Map<String, Object>> mapList = commonSqlMapper.selectBySQL(sql);
        List<String> objects = new ArrayList<String>();
        for (Map<String, Object> map : mapList) {
            objects.add(String.valueOf(map.get("table_name")));
        }

        return objects;
    }

    public Integer createClass(String model,String dao,String daoxml,String service,String path,String tableName,String thPack){
        List<ICommonCodeGenerator> objs = new ArrayList<ICommonCodeGenerator>();
        String workspace = "";
        if (!path.endsWith("/")){
            path+="/";
        }
        if(StringUtils.isNotEmpty(model)){
            workspace = path + model;
            objs.add(new CommonModelGenerator(workspace,  thPack));
        }
        if(StringUtils.isNotEmpty(daoxml)){
            workspace = path + daoxml;
            objs.add(new CommonXmlMapperGenerator(workspace,thPack));
        }
        if(StringUtils.isNotEmpty(dao)){
            workspace = path + dao;
            objs.add(new CommonJavaMapperGenerator(workspace,thPack));
        }
        if(StringUtils.isNotEmpty(service)){
            workspace = path + service;
            objs.add(new CommonServiceGenerator(workspace,thPack));
        }
        commonGeneratorService.createCode(tableName,"postgresql",objs);
        return 1;
    }
}
