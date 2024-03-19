package firstportfolio.wordcharger.controller.login;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PWFindTokenDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.TokenMapper;
import firstportfolio.wordcharger.util.TokenGenerator;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FindPWController {

    private final MemberMapper memberMapper;
    private final TokenGenerator tokenGenerator;
    private final TokenMapper tokenMapper;

    //email 보내기 위해서.
    private final JavaMailSender javaMailSender;

    //해싱을 위해서.
    private final PasswordEncoder passwordEncoder;

    //@Value 는 application.properties 에 있는 값을 가져오는 방법임.
    @Value("${spring.mail.username}")
    private String from;


    @GetMapping("/find-password")
    public String findPw() {
        return "/login/findPassword";
    }

    @PostMapping("/user-info-validation")
    @ResponseBody
    public String userInfoValidation(@RequestBody MemberAllDataFindDTO memberAllDataFindDTO) {

        // memberAllDataFindDTO 객체는 다음 3개 빼고 나머지 null 임.
        // userId=wowns590, userName=최재준, email=wowns590@naver.com
        // 이 중 userId 로 MEMBER테이블의 행을 조회한다. 그리고, 그 행의 userName 컬럼값이 최재준인지, email 이 wowns590@naver.com 인지 확인한다.
        // 만약 wowns590 이라는 user_id 컬럼값을 지닌 행이 없거나,
        // 있더라도 user_name 컬럼값이 최재준이 아니거나, email 이 wowns590@naver.com 이 아니라면,
        // "fail" 을 리턴.
        // wowns590이라는 user_id 컬럼값을 지닌 행도 있고,
        // 그 행의 user_name 컬럼값도 최재준이고,
        // email 도 wowns590@naver.com 인 경우에는 "true" 를 리턴.

        String userId = memberAllDataFindDTO.getUserId();
        String userName = memberAllDataFindDTO.getUserName();

        //email+domain 이 memberAllDataFindDTO 타입 객체에 바인딩되도록 해놔서 분리해줘야함.
        String[] split = memberAllDataFindDTO.getEmail().split("@");
        String email = split[0];
        String domain = split[1];

        MemberAllDataFindDTO findResult = memberMapper.findUserNameAndEmailAndDomainByUserId(userId);

        if (findResult == null) {
            return "not_found";
        }

        String findUserName = findResult.getUserName();
        String findEmail = findResult.getEmail();
        String findDomain = findResult.getDomain();

        if (userName.equals(findUserName) && email.equals(findEmail) && domain.equals(findDomain)) {
            // 1. TOKEN 테이블 (id(기본키), member_id(member테이블의 id컬럼값을 참조하는 외래키로 설정), token, expired_at(토큰만료시간))
            // 에 행을 삽입.
            // 2. token 을 포함한 url 전송.
            // 3. 토큰을 받는 컨트롤러 내 메서드에서 파라미터로 온 토큰을 가지고 해당 member 를 찾음.
            // 3-1) 일단, 토큰 만료시간이 지났는지 확인. 안지났을 경우에만 4번으로
            // 4. 그 member의 비밀번호를 변경할 수 있는 페이지로 이동.

            // 1. Token 테이블에 행 삽입.
            // TOKEN 테이블 : id(기본키), member_id(member테이블의 id컬럼값을 참조하는 외래키로 설정), token, expired_at(토큰만료시간)
            // 1-1) member_id 컬럼에 넣을 값 구하기.
            MemberJoinDTO memberJoinDTO = memberMapper.findMemberById(userId);
            Integer id = memberJoinDTO.getId();
            // 1-2) token 생성.
            String token = tokenGenerator.generateToken();
            // 1-3) 만료 시간 구하기
            // ㄱ) 현재시간 구하기
            LocalDateTime now = LocalDateTime.now();
            // ㄴ) +2 시간
            LocalDateTime expiredAt = now.plusHours(2);
            // 1-4) TOKEN 테이블에 행 삽입
            tokenMapper.tokenInsertRow(id, token, expiredAt);

            // 2. 이메일 보내기.
            String url = "http://localhost:8080/reset-password?token=" + token;

            // 보내는 로직 시작.
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setTo("wowns590@naver.com"); //누구한테 보낼건지. 문자열로 정해줘야함.

                mimeMessageHelper.setSubject("[단어충전소] 비밀번호 변경 링크");

                //여기서 cid : Content ID 를 의미. 이걸 addInline 메서드의 첫번째 파라미터의 값으로 써주면 됨.
                String html = "<html><body>" +
                        "<div><img src='cid:imageId' width='500' height='350'></div> " +
                        "<a href='" + url + "'  style='width=30px; height:30px; text-decoration:none; color: black; font-size: 30px;'> Click : 비밀번호 변경하기</a>" +
                        "</body></html>";

                mimeMessageHelper.setText(html, true); // 두번째 파라미터 : true(첫번째 파라미터가 html 형식이다), false(첫번째 파라미터가 String타입 문자열이다)
                mimeMessageHelper.addInline("imageId", new FileDataSource("/Users/choejaejun/study/wordcharger/src/main/webapp/resources/freedom.jpg"));
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                // 이 코드가 실행될 때 발생할 수 있는 예외를 잡아줘야 함. -> MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            }


            return "success";
        } else {
            if (!userName.equals(findUserName)) {
                return "not_name";
            } else if (!email.equals(findEmail)) {
                return "not_email";
            } else { /*!domain.equals(findDomain) */
                return "not_domain";
            }
        }
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam String token, Model model) {
        // 1. token 테이블에서 토큰의 유효기간이 지났는지 확인.


        PWFindTokenDTO tokenRow = tokenMapper.findRowByToken(token);
        LocalDateTime expiredAt = tokenRow.getExpiredAt();
        LocalDateTime now = LocalDateTime.now();

        log.info("expiredAt ==={}", expiredAt);
        log.info("now ==={}", now);

        boolean isBefore = now.isBefore(expiredAt);
        // 현재시각이 토큰만료시각보다 이전인지 여부를 따짐.

        if (isBefore) {
            //현재시각이 (토큰만료시각보다) 이전이라면 => 유효한 토큰
            //유효한 토큰이라면 뭘 해줘야 할까?
            //토큰에 의해 찾을 수 있는 member테이블의 id컬럼값을 담아주면 되지 않나?
            model.addAttribute("id", tokenRow.getMemberId());
            return "/login/pwUpdateForm";
        } else {
            //현재시각과 토큰만료시각이 같거나, 토큰만료시간이 (현재시각보다) 이전이라면 => 무효인 토큰
            // --> model 에 "tokenOutOfDate" 라는 키를 담아서 Home으로 보내줄거임.
            model.addAttribute("tokenOutOfDate", "이 링크로 비밀번호를 변경할 수 있는 기한이 이미 지났습니다. 새롭게 링크를 받아주세요.");
            return "/home/home";
        }
    }


    @PostMapping("/update-pw")
    public String updatePW(@RequestParam String password, @RequestParam String id, Model model){

        log.info("password=={}", password);
        log.info("id=={}", id);

        // 여기서, 비밀번호 업데이트 해야해.
        // 해싱하기
        String encodedPassword = passwordEncoder.encode(password);

        //db(member테이블)에 저장하기.
        memberMapper.updatePassword(Integer.parseInt(id), encodedPassword);
        model.addAttribute("PWSccessUpdate", "비밀번호가 성공적으로 변경되었습니다");
        return "/home/home";
    }
}
