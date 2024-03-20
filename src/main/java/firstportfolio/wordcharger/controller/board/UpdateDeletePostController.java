package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostGenerateDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.PostPasswordMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UpdateDeletePostController {

    private final PostsMapper postsMapper;
    private final PostPasswordMapper postPasswordMapper;

    @GetMapping("/update-post")
    public String updatePostGet(@RequestParam String postId, Model model, HttpServletRequest request) {

        PostsDTO post = postsMapper.findPostAndPostPasswordById(Integer.parseInt(postId));
        // 지금 post 라는 변수에 들어있는 PostsDTO 타입 객체에는
        // post_password 까지 들어있다.
        // 그리고, isPrivate 라는 필드에는 비밀번호가 있는 경우 1 이 , 없는 경우에는 0이 들어있다.
        // 이제, 시작해보자고.
        // 생각의 흐름은 이러하다.
        // 1. isPrivate 안에 들어있는 값이 1인 경우 => 체크박스 체크
        // 2. 0인 경우 => 체크박스 미체크
        // 3. post_password 테이블의 post_password 컬럼값으로는 null 이 들어갈 수 있도록 해놨거든.
        //      따라서, 그냥 그 값을 넣어두면, 만약 비밀번호 설정을 안했던 글 같은 경우에는 그대로 null 인채로 남게 된다.
        // 4. 그리고, isPrivate 필드 값이 1인 경우에는 show 라는 걸 model에 담아줘야 해.


        if (post.getIsPrivate() == 1) {
            model.addAttribute("show", true);
        }

        model.addAttribute("post", post);

        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        int id = loginedMember.getId();
        model.addAttribute("id", id);
        model.addAttribute("userId", loginedMember.getUserId());

        // 보안 강화
        // 지금 http://localhost:8080/update-post?postId=127 이렇게 오면, 무조건 수정이 가능하도록 되어있음.
        // 이렇게 왔을 때 이 요청을 보낸 사용자가 이 글을 쓴 사용자와 일치하는지 검사한 다음에 수정할 수 있는 페이지를 보여주도록 해야해.
        int memberId = post.getMemberId();


        if (memberId != id) {
            String message = "글을 작성한 사람만 수정할 수 있습니다";
            String encodedMessage = "";
            try {
                encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                log.error("updatePostGet 에서 url 인코딩 중 UnsupportedEncodingException 예외 발생", e);
                throw new RuntimeException(e);
            }
            return "redirect:/board-home?hecker=" + encodedMessage;
        }

        return "/contact/updatePost";
    }

    @PostMapping("/update-post")
    @ResponseBody
    public String updatePostPost(@RequestBody PostGenerateDTO postGenerateDTO) {
        // 어떤 데이터가 넘어왔나?
        // 1. id : posts 테이블의 id(기본키) 컬럼값
        // 2. member_id : member테이블의 id컬럼값을 참조하는 외래키로 설정되어 있음.
        // 3. title : 게시물의 제목
        // 4. is_private_string : on 인 경우 체크되어 있음을 나타냄. posts 테이블에서 비밀글인지 여부를 나타낼때 사용되는 컬럼
        // 5. content : 게시물의 내용
        // 6. postPassword : 게시물의 비밀번호
        // 7. userId : 게시물 작성자의 member테이블 user_id 컬럼값.


        // 이 중 어떤 걸 update 해줘야 하나?
        // title, is_private_string, content, postPassword
        Integer id = postGenerateDTO.getId();
        String title = postGenerateDTO.getTitle();
        String isPrivateString = postGenerateDTO.getIs_private_string();
        Integer isPrivate = 0;
        if (isPrivateString != null && isPrivateString.equals("on")) { // 만약, 체크가 되있다면, 1로 바꾸기.
            isPrivate = 1;
        }

        log.info("isPrivate=={}", isPrivate);


        String content = postGenerateDTO.getContent();
        String postPassword = postGenerateDTO.getPostPassword();
        postsMapper.updatePost(id, title, isPrivate, content);
        postPasswordMapper.updatePostPassword(id, postPassword);

        return "success";
    }

    @GetMapping("/delete-post")
    @ResponseBody
    public String deletePostGet(@RequestParam String postId, Model model, HttpServletRequest request) {

        log.info("postId={}", postId);
        //1. 일단, 게시글의 작성자와 지금 요청을 보낸 사용자가 동일인물인지 확인
        PostsDTO post = postsMapper.findPostAndPostPasswordById(Integer.parseInt(postId));
        int memberId = post.getMemberId();

        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        int id = loginedMember.getId();

        if (memberId != id) { //동일인물이 아니라면,
            return "fail";
        }
        return "success";
    }

    @GetMapping("/delete-post-real")
    @ResponseBody
    public String deletePostGet2(@RequestParam String postId) {
        // 저 postId 의 flag 컬럼을 Y 로 바꿔주면 됨.
        postsMapper.updateDeletePostFL(Integer.parseInt(postId));
        return "success";

    }
}
