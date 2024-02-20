package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.CommentDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.CommentsMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.PostViewMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.ShowPostService;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ShowPostController {
    private final ShowPostService showPostService;
    @GetMapping("show-writing")
    public String showWritingControllerMethod(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam Integer postId,
                                              Model model,
                                              HttpServletRequest request) {

        showPostService.showPost(page, postId, model, request);
        return "/contact/showPost";
    }
}
