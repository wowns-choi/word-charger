package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.WordDTO;

public interface WordMapper {

    WordDTO findByVoca(String voca);

}
