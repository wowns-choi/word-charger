package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.IncludeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IncludeMapper {

    void init();
    void includeInit (String id);

    IncludeDTO findRowById(String id);

    void updateToTrue(@Param("falseFieldName") String falseFieldName,@Param("id")String id);

    void updateToFalse(@Param("firstVoca") String firstVoca, @Param("id") String id);
}