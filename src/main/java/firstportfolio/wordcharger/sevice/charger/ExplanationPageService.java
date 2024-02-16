package firstportfolio.wordcharger.sevice.charger;

import firstportfolio.wordcharger.DTO.ExampleSentenceDTO;
import firstportfolio.wordcharger.repository.ExampleSentenceMapper;
import firstportfolio.wordcharger.repository.MeaningMapper;
import firstportfolio.wordcharger.repository.WordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ExplanationPageService {
    private final WordMapper wordMapper;
    private final MeaningMapper meaningMapper;
    private final ExampleSentenceMapper exampleSentenceMapper;
    public void getCorrectionForMistakenWord(String vocabulary, Model model){


        Integer findWordId = wordMapper.findByWord(vocabulary);

        List<String> meaning = meaningMapper.findMeaning(findWordId);
        String mean = meaning.get(0);

        ExampleSentenceDTO exampleSentence = exampleSentenceMapper.findExampleSentence(findWordId);

        String examplesentence1 = exampleSentence.getExampleSentence();
        String examplesentence2 = exampleSentence.getKoreanTranslation();

        model.addAttribute("voca", vocabulary);
        model.addAttribute("correct", mean);
        model.addAttribute("examplesentence1", examplesentence1);
        model.addAttribute("examplesentence2", examplesentence2);

    }

}
