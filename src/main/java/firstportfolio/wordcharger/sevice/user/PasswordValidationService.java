package firstportfolio.wordcharger.sevice.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PasswordValidationService {
    private final MemberMapper memberMapper;

    public String pwValidation(Model model, HttpServletRequest request, MemberAllDataFindDTO memberAllDataFindDTO) {

        HttpSession session = request.getSession(false);
        MemberJoinDTO memberJoinDTO = (MemberJoinDTO) session.getAttribute("loginedMember");
        String previousPw = memberMapper.findPwById(memberJoinDTO.getId());

        log.info("previousPw============={}", previousPw);
        log.info("================={}", memberAllDataFindDTO.getPassword());

        if (memberAllDataFindDTO.getPassword().equals(previousPw)) {
            return "/user/pw-update";
        } else {
            model.addAttribute("wrong", "비밀번호가 일치하지 않습니다.");
            return "/user/pw-update-validation";
        }
    }

}
