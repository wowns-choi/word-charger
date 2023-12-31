package firstportfolio.wordcharger.controller.charger;

import firstportfolio.wordcharger.repository.CountMapper;
import firstportfolio.wordcharger.repository.FixedDayMapper;
import firstportfolio.wordcharger.repository.IncludeMapper;
import firstportfolio.wordcharger.repository.WritingMapper;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DeletePulledVocaController {

    private final CountMapper countMapper;
    private final FixedDayMapper fixedDayMapper;
    private final IncludeMapper includeMapper;
    private final WritingMapper writingMapper;
    @GetMapping("/delete-pulled-voca")
    public String deletePulledVocaControllerMethod(@RequestParam String vocabulary, HttpServletRequest request) {
        String id = FindLoginedMemberIdUtil.findLoginedMember(request);
        countMapper.deletePulledVoca(vocabulary, id);
        return "redirect:/zero-to-hundred";
    }

    @PostConstruct
    public void init(){
        countMapper.init();
        fixedDayMapper.init();
        includeMapper.init();
        writingMapper.deleteAll();

        for(int i=0; i<300; i++ ){
            writingMapper.init(i);
        }



    }
}
