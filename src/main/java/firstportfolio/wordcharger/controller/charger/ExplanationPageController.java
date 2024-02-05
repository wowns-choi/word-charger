package firstportfolio.wordcharger.controller.charger;

import firstportfolio.wordcharger.DTO.ExampleSentenceDTO;
import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.ExampleSentenceMapper;
import firstportfolio.wordcharger.repository.MeaningMapper;
import firstportfolio.wordcharger.repository.WordMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ExplanationPageController {
    private final WordMapper wordMapper;
    private final MeaningMapper meaningMapper;
    private final ExampleSentenceMapper exampleSentenceMapper;

    @GetMapping("/explanation-page")
    public String explanationPageControllerMethod(@RequestParam String vocabulary, HttpServletRequest request){

        Integer findWordId = wordMapper.findByWord(vocabulary);

        List<String> meaning = meaningMapper.findMeaning(findWordId);
        String mean = meaning.get(0);

        ExampleSentenceDTO exampleSentence = exampleSentenceMapper.findExampleSentence(findWordId);

        String examplesentence1 = exampleSentence.getExampleSentence();
        String examplesentence2 = exampleSentence.getKoreanTranslation();


        request.setAttribute("voca", vocabulary);
        request.setAttribute("correct", mean);
        request.setAttribute("examplesentence1", examplesentence1);
        request.setAttribute("examplesentence2", examplesentence2);

        return "/charger/explanationPage2";
    }
}
