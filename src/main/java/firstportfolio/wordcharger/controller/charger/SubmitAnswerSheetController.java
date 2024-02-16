package firstportfolio.wordcharger.controller.charger;


import firstportfolio.wordcharger.repository.MeaningMapper;
import firstportfolio.wordcharger.repository.UserWordMapper;
import firstportfolio.wordcharger.repository.WordMapper;
import firstportfolio.wordcharger.sevice.charger.SubmitAnswerSheetService;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SubmitAnswerSheetController {
    private final SubmitAnswerSheetService submitAnswerSheetService;

    @PostMapping("/submit-answer-sheet")
    @ResponseBody
    public Map<String, String> submitAnswerSheetControllerMethod(@RequestParam String vocabulary, @RequestParam String userAnswer, HttpServletRequest request){

        Map<String, String> trueOrFalseMap = submitAnswerSheetService.evaluateAnswer(vocabulary, userAnswer, request);
        return trueOrFalseMap;
    }
}
