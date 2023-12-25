package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FixedDayMapper {

    void fixedDayInit (String id);

    String findFixedDayByIdAndColumn(@Param("id") String id, @Param("columnName") String columnName);


}
