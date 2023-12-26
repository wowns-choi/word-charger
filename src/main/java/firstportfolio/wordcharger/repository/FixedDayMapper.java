package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FixedDayMapper {

    void fixedDayInit (String id);

    Integer findFixedDayByIdAndColumn(@Param("id") String id, @Param("columnName") String columnName);

    void plusOneDay(@Param("ColumnName") String ColumnName, @Param("id") String id);
}
