package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.WordDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WordMapper {

    WordDTO findByVoca(String voca);



}
