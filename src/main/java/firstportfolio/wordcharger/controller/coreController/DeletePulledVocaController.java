package firstportfolio.wordcharger.controller.coreController;

import firstportfolio.wordcharger.DTO.MemberDTO;
import firstportfolio.wordcharger.repository.CountMapper;
import firstportfolio.wordcharger.repository.FixedDayMapper;
import firstportfolio.wordcharger.repository.IncludeMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    @GetMapping("/deletePulledVoca")
    public String deletePulledVocaControllerMethod(@RequestParam String vocabulary, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        MemberDTO loginedMember = (MemberDTO)session.getAttribute("loginedMember");
        countMapper.deletePulledVoca(vocabulary, loginedMember.getId());
        return "redirect:/zeroToHundred";
    }

    @PostConstruct
    public void init(){
        countMapper.init();
        fixedDayMapper.init();
        includeMapper.init();
    }
}
