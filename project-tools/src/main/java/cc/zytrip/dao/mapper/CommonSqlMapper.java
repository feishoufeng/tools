package cc.zytrip.dao.mapper;

import java.util.List;
import java.util.Map;

public interface CommonSqlMapper {
    public List<Map<String, Object>> selectBySQL(String sql);
}