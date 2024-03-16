package firstportfolio.wordcharger.sevice.board.common;

import firstportfolio.wordcharger.DTO.PostsDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
// @Transactional 안쓴다. db관련 코드 없음.
public class PaginationService {

    public void pagination (Integer currentPage, Integer pageSize, Integer totalPosts, Integer pageGroupSize, List<?> currentPagePosts, Model model){
        Integer totalPages = (int) Math.ceil((double) totalPosts / pageSize);

        Integer currentGroup = (int) Math.ceil((double) currentPage / pageGroupSize);
        Integer currentGroupFirstPage = (currentGroup - 1) * pageGroupSize + 1;
        Integer currentGroupLastPage = Math.min(currentGroupFirstPage + pageGroupSize - 1, totalPages);

        model.addAttribute("currentPagePosts", currentPagePosts);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentGroupFirstPage", currentGroupFirstPage);
        model.addAttribute("currentGroupLastPage", currentGroupLastPage);
        model.addAttribute("pageGroupSize", pageGroupSize);

    }

}
