package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.BoardHomeRecommendService;
import firstportfolio.wordcharger.sevice.board.BoardHomeService;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardHomeController {
    private final BoardHomeService boardHomeService;
    private final BoardHomeRecommendService boardHomeRecommendService;

    @GetMapping("/board-home")
    public String boardHomeControllerMethod(@RequestParam(required = false, defaultValue = "1") Integer page, Model model, @RequestParam(required = false) String hecker) {
        if(hecker != null){
            model.addAttribute("hecker", hecker);
        }
        boardHomeService.findAllPosts(page, model);
        return "/contact/boardHome2";
    }

    @GetMapping("/board-home-order-by-like-num")
    public String boardHomeOrderByLikeNum (@RequestParam(required = false, defaultValue = "1") Integer page, Model model){
        boardHomeService.findAllPosts2(page, model);
        return "/contact/boardHome2";
    }

    @GetMapping("/board-home-order-by-view-num")
    public String boardHomeOrderByViewNum (@RequestParam(required = false, defaultValue = "1") Integer page, Model model){
        boardHomeService.findAllPosts3(page, model);
        return "/contact/boardHome2";
    }


}
