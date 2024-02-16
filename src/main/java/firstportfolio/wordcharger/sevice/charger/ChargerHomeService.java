package firstportfolio.wordcharger.sevice.charger;

import firstportfolio.wordcharger.repository.UserWordMapper;
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
@RequiredArgsConstructor
@Transactional
public class ChargerHomeService {

    private final UserWordMapper userWordMapper;

    public void getTodaysWordsCount(Model model, HttpServletRequest request){

        //count 테이블의 today 컬럼에 들어있는 거 꺼내와.
        Integer id = FindLoginedMemberIdUtil.findLoginedMember(request);

        List<Integer> todayCountList = new ArrayList<>();

        for(int i=1; i<11; i++){
            List<Integer> wordId = userWordMapper.findWordId(i*100 -99, i*100, id);
            int size = wordId.size();
            todayCountList.add(size);
        }

        model.addAttribute("a", todayCountList.get(0));
        model.addAttribute("b", todayCountList.get(1));
        model.addAttribute("c", todayCountList.get(2));
        model.addAttribute("d", todayCountList.get(3));
        model.addAttribute("e", todayCountList.get(4));

        model.addAttribute("f", todayCountList.get(5));
        model.addAttribute("g", todayCountList.get(6));
        model.addAttribute("h", todayCountList.get(7));
        model.addAttribute("i", todayCountList.get(8));
        model.addAttribute("j", todayCountList.get(9));
    }

}
