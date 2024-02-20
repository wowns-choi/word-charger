package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.FindPostsByTitleWriterService;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FindPostsByTitleWriterContentController {

    private final FindPostsByTitleWriterService findPostsByTitleWriterService;
    @RequestMapping("find-posts-by-title-writer-content")
    public String findWritingControllerMethod(@RequestParam String byWhatType, @RequestParam String hintToFind, @RequestParam(required = false, defaultValue = "1") Integer page, Model model) {
        findPostsByTitleWriterService.findPostsService(byWhatType, hintToFind, page, model);
        return "/contact/boardHomeFind2";
    }

}
