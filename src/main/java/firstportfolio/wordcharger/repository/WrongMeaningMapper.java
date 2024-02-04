package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.WrongMeaningDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WrongMeaningMapper {

    List<String> findWrongMeaning (Integer wordId);

}
