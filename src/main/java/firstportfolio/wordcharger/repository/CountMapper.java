package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.CountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CountMapper {

    void init();

    CountDTO findRowById (String id);

    void CountInit (String id);

    void uploadFixedDayToCount (@Param("columnName") String columnName, @Param("id") String id, @Param("value") String value );

    String findTodayColumnById (String id);

    void deletePulledVoca(@Param("firstVoca") String firstVoca, @Param("id") String id);

    void todayColumnCharger();

    void columnShiftUpdate();
}