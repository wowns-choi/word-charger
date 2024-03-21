package firstportfolio.wordcharger.controller.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberWithdrawalController {
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;


    @GetMapping("/withdrawal")
    public String withdrawalGet(HttpServletRequest request) {

        return "/login/withdrawal";
    }

    @PostMapping("/withdrawal")
    @ResponseBody
    public String withdrawalPost(@RequestBody MemberAllDataFindDTO member, HttpServletRequest request){
        //여기서 검증해주는거야. 비밀번호가 맞는지에 대해
        String inputPassword = member.getPassword();

        HttpSession session = request.getSession(false);
        MemberJoinDTO findMemberInSession = (MemberJoinDTO)session.getAttribute("loginedMember");

        boolean matches = passwordEncoder.matches(inputPassword, findMemberInSession.getPassword());

        if (matches) {
            // 비밀번호가 일치한다면,
            // 해당 멤버에 해당하는 Member 테이블 행의 delete_member_fl 컬럼의 값을 'Y' 로 바꿔주고,
            // success 를 리턴.
            Integer id = findMemberInSession.getId();
            log.info("aaa-={}", findMemberInSession.getId());
            memberMapper.updateDeleteMemberFl(findMemberInSession.getId());
            //세션도 지워주어야 할듯?
            session.invalidate();
            return "success";

        } else{
            // 비밀번호가 일치하지 않는다면
            // fail 을 리턴.
            return "fail";
        }
    }
}


