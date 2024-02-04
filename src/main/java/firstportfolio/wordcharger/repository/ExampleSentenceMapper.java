package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.ExampleSentenceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExampleSentenceMapper {

    List<String> findExampleSentence (Integer wordId);

}
