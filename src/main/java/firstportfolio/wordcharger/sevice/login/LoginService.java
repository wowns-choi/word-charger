package firstportfolio.wordcharger.sevice.login;

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

@Slf4j
// @Transactional 안쓴다. select 쿼리 밖에 없음.
@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;


    public String loginCheck(String id, String password, HttpServletRequest request){

        //db Member 테이블에서 아이디를 찾아와서 여기있는 password랑 같은지 봐야겠지.
        MemberJoinDTO findedMember = memberMapper.findMemberById(id);
        String DBpassword = findedMember.getPassword();
        try {
            if (!passwordEncoder.matches(password, DBpassword)) { //사용자가 입력한 raw한 비밀번호와 DB에 저장되어 있는 해시값을 비교해줌.
                request.setAttribute("passwordIncorrectMessage", "비밀번호가 일치하지 않습니다.");
                return "/login/loginForm2";
            }

        } catch (NullPointerException e) {
            request.setAttribute("idIncorrectMessage", "존재하지 않는 아이디입니다.");
            return "/login/loginForm2";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("loginedMember", findedMember);

        //만약, 관리자라면, 다른 흐름을 만들어줘야 함.
        if(id.equals("manager1")){
            return "/manager/managerHome";
        }

        //만약, 판매자라면(merchant 로 시작한다면), 다른 흐름을 만들어줘야 함.
        if(id.startsWith("merchant")){
            return "/merchant/merchantHome";
        }

        //일반 사용자인 경우.
        return "redirect:/";
    }
}
