package firstportfolio.wordcharger.controller.charger;


import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.FixedDayMapper;
import firstportfolio.wordcharger.repository.WordMapper;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    private final FixedDayMapper fixedDayMapper;

    @PostMapping("/submit-answer-sheet")
    @ResponseBody
    public Map<String, String> submitAnswerSheetControllerMethod(@RequestParam String vocabulary, @RequestParam String userAnswer, HttpServletRequest request){



        Map<String, String> trueOrFalseMap = new ConcurrentHashMap<>();

        //voca = 영단어가 뭐였는지. userAnswer = 사용자가 뭘 선택했는지.
        WordDTO findedVoca = wordMapper.findByVoca(vocabulary);
        String correct = findedVoca.getCorrect();
        //아이디 얻는코드
        String id = FindLoginedMemberIdUtil.findLoginedMember(request);

        //이부분 중요 시작
        Integer fixedDayTableValue = fixedDayMapper.findFixedDayByIdAndColumn(id, vocabulary);
        if (fixedDayTableValue.equals(0)) {
            fixedDayMapper.plusOneDay(vocabulary, id);
        }
        //이부분 중요 끝

        if (!userAnswer.equals(correct)) {
            trueOrFalseMap.put("trueOrFalseBox", "incorrect");

        } else {
            trueOrFalseMap.put("trueOrFalseBox", "correct");
            //fixedday 에 +1 해주는 코드가 필요해.
            fixedDayMapper.plusOneDay(vocabulary, id);

        }

        return trueOrFalseMap;
    }
}