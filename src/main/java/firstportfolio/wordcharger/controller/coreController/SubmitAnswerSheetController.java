package firstportfolio.wordcharger.controller.coreController;


import firstportfolio.wordcharger.DTO.MemberDTO;
import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.FixedDayMapper;
import firstportfolio.wordcharger.repository.WordMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/submitAnswerSheet")
    @ResponseBody
    public Map<String, String> submitAnswerSheetControllerMethod(@RequestParam String vocabulary, @RequestParam String userAnswer, HttpServletRequest request){

        log.info("voca==========={}", vocabulary);
        log.info("userAnswer======={}", userAnswer);
        Map<String, String> trueOrFalseMap = new ConcurrentHashMap<>();

        //voca = 영단어가 뭐였는지. userAnswer = 사용자가 뭘 선택했는지.
        WordDTO findedVoca = wordMapper.findByVoca(vocabulary);
        String correct = findedVoca.getCorrect();
        //아이디 얻는코드
        HttpSession session = request.getSession(false);
        MemberDTO loginedMember = (MemberDTO) session.getAttribute("loginedMember");
        String id = loginedMember.getId();
        if (!userAnswer.equals(correct)) {
            trueOrFalseMap.put("trueOrFalseBox", "incorrect");
            //틀렸을 경우, fixedday 에 0 인 경우에만 +1 해줘야해.
            //fixedday 테이블에서 vocabulary 의 값을 구해온다. 그게 0 이면, +1 해줘야 한다.
            //이거 왜 해주냐? 0인 경우에도 그대로 둔다면, ExplanationPage를 거쳐 다시 /zeroToHundred 를 호출했을 때, false이기 때문에 바로 count 에 올라가서, 최초 사용자에게 무한굴레에 빠지게 할 수 있음.
            Integer fixedDayTableValue = fixedDayMapper.findFixedDayByIdAndColumn(id, vocabulary);
            if (fixedDayTableValue.equals(0)) {
                fixedDayMapper.plusOneDay(vocabulary, id);
            }

        } else {
            trueOrFalseMap.put("trueOrFalseBox", "correct");
            //fixedday 에 +1 해주는 코드가 필요해.
            fixedDayMapper.plusOneDay(vocabulary, id);

        }

        return trueOrFalseMap;
    }
}
