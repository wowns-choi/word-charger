package firstportfolio.wordcharger.controller.charger;


import firstportfolio.wordcharger.repository.MeaningMapper;
import firstportfolio.wordcharger.repository.UserWordMapper;
import firstportfolio.wordcharger.repository.WordMapper;
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


    //여기부터
    private final WordMapper wordMapper;
    private final MeaningMapper meaningMapper;
    private final UserWordMapper userWordMapper;

    @PostMapping("/submit-answer-sheet")
    @ResponseBody
    public Map<String, String> submitAnswerSheetControllerMethod(@RequestParam String vocabulary, @RequestParam String userAnswer, HttpServletRequest request){

        Integer memberTableId = FindLoginedMemberIdUtil.findLoginedMember(request);


        Map<String, String> trueOrFalseMap = new ConcurrentHashMap<>();

        //지금 테스트 낸 단어의 word 테이블 내 wordId 가 무엇이냐?
        Integer findWordId = wordMapper.findByWord(vocabulary);

        List<String> meaning = meaningMapper.findMeaning(findWordId);

        Integer ox = 0;

        for(String mean : meaning){
            log.info("mean={}", mean);
            log.info("userAnswer={}", userAnswer);
            log.info("trueOrFlase ============{}", mean.equals(userAnswer));
            log.info("trueOrFlase ============2{}", mean == userAnswer);

            if (mean.equals(userAnswer)) {
                ox += 1;
            }
        }

        log.info("ox =============={}", ox);

        if(ox==0){
            //틀린 경우
            trueOrFalseMap.put("trueOrFalseBox", "incorrect");

        } else if(ox>0){
            //맞춘 경우
            trueOrFalseMap.put("trueOrFalseBox", "correct");
            //rememberedday 컬럼의 값을 +1 증가시키는 로직이 필요해
            userWordMapper.updateDaysRemembered(memberTableId, findWordId);
        }

        Integer findDaysRemembered = userWordMapper.findDaysRemembered(memberTableId, findWordId);
        userWordMapper.updateNextReviewDate(findDaysRemembered, memberTableId, findWordId);

        String startWordId = null;
        String endWordId = null;

        if(findWordId <= 100){
            startWordId = "1";
            endWordId = "100";
        } else if (findWordId >100 && findWordId <=200) {
            startWordId = "101";
            endWordId = "200";
        } else if (findWordId >200 && findWordId <=300){
            startWordId = "201";
            endWordId = "300";
        } else if (findWordId >300 && findWordId <=400) {
            startWordId = "301";
            endWordId = "400";
        } else if (findWordId >400 && findWordId <= 500){
            startWordId  = "401";
            endWordId = "500";
        } else if (findWordId >500 && findWordId <=600){
            startWordId = "501";
            endWordId = "600";
        } else if (findWordId > 600 && findWordId <= 700) {
            startWordId = "601";
            endWordId = "700";
        } else if (findWordId > 700 && findWordId <= 800) {
            startWordId = "701";
            endWordId = "800";
        } else if (findWordId > 800 && findWordId <= 900) {
            startWordId = "801";
            endWordId = "900";
        } else if (findWordId > 900 && findWordId <= 1000) {
            startWordId = "901";
            endWordId = "1000";
        }
        trueOrFalseMap.put("startWordId", startWordId);
        trueOrFalseMap.put("endWordId", endWordId);

        return trueOrFalseMap;
    }
}
