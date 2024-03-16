package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostGenerateDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Slf4j
@Service
//@Transactional 안쓴다. db관련 코드 없음.
@RequiredArgsConstructor
public class ShowWritingFormService {

    public void showWritingForm (Model model, HttpServletRequest request){
        model.addAttribute("postGenerateDTO", new PostGenerateDTO());

        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        model.addAttribute("id", loginedMember.getId());
        model.addAttribute("userId", loginedMember.getUserId());
    }


}
