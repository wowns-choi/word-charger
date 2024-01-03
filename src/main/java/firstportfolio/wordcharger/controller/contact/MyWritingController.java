package firstportfolio.wordcharger.controller.contact;

import firstportfolio.wordcharger.DTO.WritingDataUserCommentDTO;
import firstportfolio.wordcharger.repository.WritingDataUserCommentMapper;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyWritingController {
    private final WritingDataUserCommentMapper writingDataUserCommentMapper;

    @GetMapping("/my-writing")
    public String MyWritingHomeControllerMethod (@RequestParam(required = false,defaultValue = "1") Integer page,  HttpServletRequest request, Model model){

        String loginedMemberId = FindLoginedMemberIdUtil.findLoginedMember(request);
        //현재 페이지
        Integer currentPage = page;
        //페이지당 게시물 수
        Integer pageSize = 5;
        //총 글 수
        Integer totalWritings = writingDataUserCommentMapper.findTotalMyWritingSize(loginedMemberId);
        //총 페이지 수
        Integer totalPages = (int) Math.ceil((double) totalWritings / pageSize);

        //그룹당 페이지 수
        Integer pageGroupSize = 5;
        //현재 그룹
        Integer currentGroup = (int) Math.ceil((double) currentPage / pageGroupSize);
        //현재 그룹 첫페이지
        Integer currentGroupFirstPage = (currentGroup - 1) * pageGroupSize + 1;
        //현재 그룹 마지막페이지
        int currentGroupLastPage = Math.min(currentGroupFirstPage + pageGroupSize - 1, totalPages);

        model.addAttribute("currentGroupFirstPage", currentGroupFirstPage);
        model.addAttribute("currentGroupLastPage", currentGroupLastPage);
        model.addAttribute("pageGroupSize",pageGroupSize);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        Integer startRow = (currentPage - 1) * pageSize;

        List<WritingDataUserCommentDTO> myWritings = writingDataUserCommentMapper.findMyWriting(loginedMemberId,startRow,pageSize);

        model.addAttribute("myWritings", myWritings);

        return "/contact/myWriting";

    }
}
