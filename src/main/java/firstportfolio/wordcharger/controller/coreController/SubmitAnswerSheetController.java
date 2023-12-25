package firstportfolio.wordcharger.controller.coreController;

import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.WordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SubmitAnswerSheetController {
    private final WordMapper wordMapper;

    @PostMapping("/submitAnswerSheet")
    @ResponseBody
    public Map<String, String> submitAnswerSheetControllerMethod(@RequestParam String vocabulary, @RequestParam String userAnswer){

        log.info("voca==========={}", vocabulary);
        log.info("userAnswer======={}", userAnswer);
        Map<String, String> trueOrFalseMap = new ConcurrentHashMap<>();

        //voca = 영단어가 뭐였는지. userAnswer = 사용자가 뭘 선택했는지.
        WordDTO findedVoca = wordMapper.findByVoca(vocabulary);
        String correct = findedVoca.getCorrect();
        if (!userAnswer.equals(correct)) {
            trueOrFalseMap.put("trueOrFalseBox", "incorrect");
        } else {
            trueOrFalseMap.put("trueOrFalseBox", "correct");
        }

        return trueOrFalseMap;
    }
}
