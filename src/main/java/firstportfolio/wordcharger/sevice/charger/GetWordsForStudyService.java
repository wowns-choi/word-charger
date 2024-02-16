package firstportfolio.wordcharger.sevice.charger;

import firstportfolio.wordcharger.DTO.UserWordDTO;
import firstportfolio.wordcharger.repository.*;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GetWordsForStudyService {

    private final WordMapper wordMapper;
    private final UserWordMapper userWordMapper;
    private final MeaningMapper meaningMapper;
    private final WrongMeaningMapper wrongMeaningMapper;
    public String getWordsForStudy(HttpServletRequest request, Model model, String startWordId, String endWordId){
        //로그인한 유저의 MEMBER 테이블 내 ID컬럼 값을 가져옴.
        Integer id = FindLoginedMemberIdUtil.findLoginedMember(request);

        //초기화 작업은 JoinController 에서 회원가입을 하면 즉시 해주도록 했기 때문에,
        //여기서는 userWord 테이블에서 1-100 사이 중 nextReviewDate 가 오늘 이거나 오늘 이전인 행만을 조회해서
        //그걸 테스트로 내주면 됨.

        Integer startWordIdInteger = Integer.valueOf(startWordId);
        Integer endWordIdInteger = Integer.valueOf(endWordId);

        List<UserWordDTO> findUserWordDTO = userWordMapper.findRowByIdAndWordId(id, startWordIdInteger);
        //만약에, user_wrod 테이블에 행이 아예 존재하지 않을 경우, init(초기화) 해줌.
        if (findUserWordDTO.isEmpty()) {
            for(int i=startWordIdInteger; i<=endWordIdInteger; i++){
                userWordMapper.initUserWord(id, i);
            }
        }

        List<Integer> wordIdList = userWordMapper.findWordId(startWordIdInteger, endWordIdInteger, id);

        if (wordIdList.isEmpty()) {
            // 오늘 외울 단어가 더 이상 존재하지 않을 경우.
            return "/charger/todayFinish";
        }

        Integer testWordId = wordIdList.get(0); //첫번째로 나온 wordId. 이걸로 뭘 할 수 있는데? 의미와 예문 찾아야지
        //단어 찾기
        String findWord = wordMapper.findByWordId(testWordId);
        //의미 찾기
        List<String> meaning = meaningMapper.findMeaning(testWordId);
        //오답 찾기
        List<String> wrongMeaning = wrongMeaningMapper.findWrongMeaning(testWordId);

        List<String> answer = new ArrayList<>();
        for(int i=0; i<meaning.size();i++){
            answer.add(meaning.get(i));
        }
        for(int i=0; i<wrongMeaning.size();i++){
            answer.add(wrongMeaning.get(i));
        }

        model.addAttribute("voca", findWord);
        model.addAttribute("answer", answer);
        return "/charger/zeroToHundredQuestion";

    }
}
