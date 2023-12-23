package firstportfolio.wordcharger.sevice;

import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.WordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class FindVocaService {

    private final WordMapper wordMapper;
    @Transactional
    public Map<String, String> findWordByVocaFromWordTable(String voca){
        Map<String, String> data = new ConcurrentHashMap<>();
        WordDTO findedVocaRow = wordMapper.findByVoca(voca);
        String correct = findedVocaRow.getCorrect();
        data.put("voca", voca);
        data.put("correct", correct);
        return data;
    }

}

