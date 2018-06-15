package cc.zytrip.service.code;

import java.util.Map;

import freemarker.template.Configuration;

/**
 * Created by ASUS on 2016/10/15.
 */
public interface ICommonCodeGenerator {
    void excute(Configuration conf, Map<String, Object> clazz) throws Exception;
}
