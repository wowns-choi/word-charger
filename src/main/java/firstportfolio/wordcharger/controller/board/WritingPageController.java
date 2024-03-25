package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostGenerateDTO;
import firstportfolio.wordcharger.DTO.PostPasswordDTO;
import firstportfolio.wordcharger.repository.PostPasswordMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.InsertPostService;
import firstportfolio.wordcharger.sevice.board.ShowWritingFormService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WritingPageController {
    private final ShowWritingFormService showWritingFormService;
    private final InsertPostService insertPostService;


    @GetMapping("/writing-page")
    public String boardWritingPageControllerMethod(Model model, HttpServletRequest request) {
        showWritingFormService.showWritingForm(model, request);
        return "/contact/writingPage";
    }

    @PostMapping("/writing-page")
    public String boardWritingPagePostMappingControllerMethod(@Valid @ModelAttribute PostGenerateDTO pageGenerateDTO, BindingResult bindingResult, Model model, HttpServletRequest request) {

        Boolean isPrivate = pageGenerateDTO.getIs_private(); //타입을 Boolean 으로 해 둔 경우, 체크박스를 클릭한 경우 on 이 아니라 true 가 오고, 체크박스를 클릭하지 않으면 false 가 온다.
        String postPassword = pageGenerateDTO.getPostPassword();
        String title = pageGenerateDTO.getTitle();
        String content = pageGenerateDTO.getContent();

        // 세션객체에서 꺼내올 데이터가 좀 있음.
        HttpSession session = request.getSession(false);
        MemberJoinDTO member = (MemberJoinDTO)session.getAttribute("loginedMember");
        Integer memberId = member.getId(); // 세션객체에서 꺼내온 member테이블의 pk 인 id 컬럼값. -> posts 테이블에 행 넣을 때, member_id 컬럼이라는 값이 있는데, 이 컬럼에 넣을 값으로 전달해주기 위해서 세션객체에서 꺼내온 값.
        String userId = member.getUserId(); // 세션객체에서 꺼내온 member테이블의 user_id 컬럼값. -> 애노테이션 기반 검증에 걸려서 다시 writingPage 로 돌아갈 때, 작성자를 표시해주기 위해서 세션객체에서 꺼내온 값.

        // 검증 - 1 : 애노테이션 기반 검증.
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", userId); // 돌아갈 writingPage 의 작성자를 표시해주기 위해서 넣어줌.
            return "/contact/writingPage";
        }

        // 검증 - 2 : isPrivate 값으로 true 가 들어왔는데, postPassword 값이 빈 문자열인 경우 == 비밀글로 하기 체크했으면서, 비밀번호를 안썻을 경우
        // 다시 writingPage.jsp 로 돌아가도록 하면서, model 에 '작성자 : ' 에 userId 가 표시되도록 하기 위해 userId 를 담아주었다.
        // 또한, show라는 키에는 true 라는 Boolean 값을 담아줬고,  notInsertPassword 라는 키에는 "비밀번호를 안쓰셨어요" 라는 텍스트를 담아줬다.
        if (isPrivate.equals(true)) {
            if (postPassword.equals("")) {
                model.addAttribute("userId", userId); // 돌아갈 writingPage 의 작성자를 표시해주기 위해서 넣어줌.
                model.addAttribute("show", true);
                model.addAttribute("notInsertPassword", "비밀번호를 안쓰셨어요");
                return "/contact/writingPage";
            }
        }

        // 검증에 모두 통과한 경우, POSTS 테이블에 insert 하도록 하였다.
        insertPostService.insertPost(isPrivate, postPassword, title, content, memberId);

        // POSTS 테이블에 행을 삽입하고 난 후에는 게시판 홈으로 돌아가도록 해주었다.
        return "redirect:/board-home";
    }


}
