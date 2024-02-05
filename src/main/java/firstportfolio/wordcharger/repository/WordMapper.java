package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.WordDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WordMapper {

    String findByWordId(Integer wordId);

    Integer findByWord(String word);



}
