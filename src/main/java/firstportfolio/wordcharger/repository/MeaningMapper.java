package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.MeaningDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeaningMapper {

    List<String> findMeaning (Integer wordId);

}
