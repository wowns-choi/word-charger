package firstportfolio.wordcharger.sevice.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PasswordValidationService {
    private final MemberMapper memberMapper;
    //해싱을 위해서.
    private final PasswordEncoder passwordEncoder;

    public String pwValidation(Model model, HttpServletRequest request, MemberAllDataFindDTO memberAllDataFindDTO) {

        HttpSession session = request.getSession(false);
        MemberJoinDTO memberJoinDTO = (MemberJoinDTO) session.getAttribute("loginedMember");
        String previousPw = memberMapper.findPwById(memberJoinDTO.getId());
        String password = memberAllDataFindDTO.getPassword();
        // DB 해시값 : previousPw
        // 사용자가 입력한 raw 한(해시되지 않은) 비밀번호 : password

        if (passwordEncoder.matches(password, previousPw)) {
            return "/user/pw-update";
        } else {
            model.addAttribute("wrong", "비밀번호가 일치하지 않습니다.");
            return "/user/pw-update-validation";
        }
    }

}
