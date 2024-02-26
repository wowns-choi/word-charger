package firstportfolio.wordcharger.controller.home;


import firstportfolio.wordcharger.DTO.EmailDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.EmailMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.NaverMemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberMapper memberMapper;
    private final NaverMemberMapper naverMemberMapper;
    private final EmailMapper emailMapper;
    @RequestMapping("/")
    public String homeControllerMethod(HttpServletRequest request, Model model,
                   @RequestParam(value = "name", required = false) String name,
                   @RequestParam(value = "nickname", required = false) String nickname,
                   @RequestParam(value="email", required = false) String email,
                   @RequestParam(value="id", required = false) String id
                                       ) {
        log.info("네이버 이름=================={}", name);
        log.info("네이버 별명=================={}", nickname);
        log.info("네이버 이메일=================={}", email);
        log.info("네이버 아이디=================={}", id);


        if (id != null) {
            //사용자가 naver로 연동 로그인을 시도한 경우

            // 이메일에서 @ 이전꺼와 @이후를 분리
            int atIndex = email.indexOf("@");
            String emailFragment = email.substring(0, atIndex);
            String domain = email.substring(atIndex + 1); // @ 포함 뒤의 부분을 얻기 위해 atIndex에 1을 더함
            String beId = "nv_" + emailFragment;


            // 가장 먼저 체크해야 할 건, 만약, 우리 사이트에 회원가입했던 사람이 네이버로 로그인하는 경우,
            // 최대한 우리 사이트에서 회원가입한 아이디로 로그인하라고 안내해줘야 함.
            // 그렇지 않는다면, 우리 사이트에서 회원가입하고 결제를 했는데, 네이버 연동로그인 시 결제한 적이 없게 되므로
            // 일단, 로그인 페이지에서 이를 안내문구로 안내해주고,
            // 우리사이트에서 회원가입시 입력한 이메일과 동일하다면, 이미 회원가입했다. 그러니 그 아이디로 로그인해달라고 해야함.
            // 그리고 로그인페이지로 돌려보내줘야함.

            boolean flag = false;

            EmailDTO emailDTO = emailMapper.emailCountByEmail(emailFragment);

            if (emailDTO != null) {
                if(emailDTO.getDomain().equals(domain)){
                    flag = true;
                }
            }
            if (flag == true) {
                // 즉, 동일한 이메일이 있던 경우, login 페이지로 이동.
                return "redirect:/login-form-alert-version";
            }


            // ** NAVER_MEMBER 테이블에서 id 와 동일한 행이 있는지 확인 **

            // 1. 최초에는 없겠지, 없을 경우
            // 1-1. 새로운 행을 MEMBER테이블에 삽입해야해.
            // 1-2. 그리고, 그 행의 id 컬럼 값을 얻어서, NAVER_MEMBER 테이블의 행을 집어넣겠어.


            Integer rowCount = naverMemberMapper.rowCount(id);
            if(rowCount == 0){
                Integer sequence = memberMapper.selectNextSequenceValue();

                // 비밀번호는 naver 서버에서 받아온 id 로 하자.
                // 이름은 naver 서버에서 받아온 name 으로 하자.
                memberMapper.insertMemberNaverVersion(sequence, beId, id, name);

                //세션 설정
                MemberJoinDTO findedMember = memberMapper.findMemberById(beId);
                HttpSession session = request.getSession(true);
                session.setAttribute("loginedMember", findedMember);

                // loginedHome2 로 제어권 이동
                return "/home/loginedHome2";

            } else{
            // 2. 있을 경우
                // 아이디로 찾아와야지. 아이디는 이메일의 @ 이전까지의 String 값으로 해놨으니, beId 로 찾자.
                MemberJoinDTO findedMember = memberMapper.findMemberById(beId);
                HttpSession session = request.getSession(true);
                session.setAttribute("loginedMember", findedMember);

                // loginedHome2 로 제어권 이동
                return "/home/loginedHome2";

            }

        }



        //세션 자체가 없다.
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "/home/home";
        }

        //세션은 있는데, loginedMember 라는 키의 세션은 없다.
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        if (loginedMember == null) {
            return "/home/home";
        }

        //loginedMember 라는 키의 세션이 있다.
        return "/home/loginedHome2";
    }


}
