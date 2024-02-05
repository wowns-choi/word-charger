package firstportfolio.wordcharger.controller.charger;


import firstportfolio.wordcharger.repository.*;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ZeroToHundredController {

    private final WordMapper wordMapper;
    private final UserWordMapper userWordMapper;
    private final MeaningMapper meaningMapper;
    private final ExampleSentenceMapper exampleSentenceMapper;
    private final WrongMeaningMapper wrongMeaningMapper;

    @GetMapping("/zero-to-hundred")
    public String zeroToHundred(HttpServletRequest request, @RequestParam String startWordId, @RequestParam String endWordId){
        //로그인한 유저의 MEMBER 테이블 내 ID컬럼 값을 가져옴.
        Integer id = FindLoginedMemberIdUtil.findLoginedMember(request);

        //초기화 작업은 JoinController 에서 회원가입을 하면 즉시 해주도록 했기 때문에,
        //여기서는 userWord 테이블에서 1-100 사이 중 nextReviewDate 가 오늘 이거나 오늘 이전인 행만을 조회해서
        //그걸 테스트로 내주면 됨.

        Integer startWordIdInteger = Integer.valueOf(startWordId);
        Integer endWordIdInteger = Integer.valueOf(endWordId);




        List<Integer> wordIdList = userWordMapper.findWordId(startWordIdInteger, endWordIdInteger, id);

        //여기서 만약에, user_word 테이블에 초기화가 안되어 있다면? 초기화를 해줘야함.
        if (wordIdList.isEmpty()) {
            for(int i=0; i<endWordIdInteger-startWordIdInteger; i++){
                userWordMapper.initUserWord(id, i);
            }
        }

        List<Integer> wordIdList2 = userWordMapper.findWordId(startWordIdInteger, endWordIdInteger, id);


        Integer testWordId = wordIdList2.get(0); //첫번째로 나온 wordId. 이걸로 뭘 할 수 있는데? 의미와 예문 찾아야지
        log.info("testWordId = {}", testWordId);
        //단어 찾기
        String findWord = wordMapper.findByWordId(testWordId);
        log.info("findWord = {}", findWord);
        //의미 찾기
        List<String> meaning = meaningMapper.findMeaning(testWordId);
        log.info("meaning ={}", meaning);
        //오답 찾기
        List<String> wrongMeaning = wrongMeaningMapper.findWrongMeaning(testWordId);
        log.info("wrongMeaning={}", wrongMeaning);

        List<String> answer = new ArrayList<>();
        for(int i=0; i<meaning.size();i++){
            answer.add(meaning.get(i));
        }
        for(int i=0; i<wrongMeaning.size();i++){
            answer.add(wrongMeaning.get(i));
        }



        request.setAttribute("voca", findWord);
        request.setAttribute("answer", answer);




        return "/charger/zeroToHundredQuestion";
    }


}
