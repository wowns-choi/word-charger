package firstportfolio.wordcharger.controller.contact;

import firstportfolio.wordcharger.DTO.WritingDTO;
import firstportfolio.wordcharger.DTO.WritingDTOSelectVersion;
import firstportfolio.wordcharger.repository.WritingMapper;

import firstportfolio.wordcharger.sevice.common.WritingService;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final WritingMapper writingMapper;
    private final WritingService writingService;

    @GetMapping("/board-home")
    public String boardHomeControllerMethod(@RequestParam(required = false, defaultValue = "1") Integer page, Model model) {

        Integer currentPage = page;
        Integer pageSize = 20;

        Integer totalWritings = writingService.getTotalWritingsCount();
        Integer totalPages = (int) Math.ceil((double) totalWritings / pageSize);

        int startRow = (currentPage - 1) * pageSize;
        List<WritingDTOSelectVersion> currentPageWritings = writingService.findCurrentPageWritings(startRow, pageSize);

        Integer pageGroupSize = 9;
        Integer currentGroup = (int) Math.ceil((double) currentPage / pageGroupSize);
        Integer currentGroupFirstPage = (currentGroup - 1) * pageGroupSize + 1;
        Integer currentGroupLastPage = Math.min(currentGroupFirstPage + pageGroupSize - 1, totalPages);


        model.addAttribute("currentPageWritings", currentPageWritings);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentGroupFirstPage", currentGroupFirstPage);
        model.addAttribute("currentGroupLastPage", currentGroupLastPage);
        model.addAttribute("pageGroupSize", pageGroupSize);

        return "/contact/boardHome";

    }

    @RequestMapping("find-writings-by-title-writer-content")
    public String findWritingControllerMethod(@RequestParam String byWhatType, @RequestParam String hintToFind, @RequestParam(required = false, defaultValue = "1") Integer page, Model model){

        Integer currentPage = page;

        //pageSize 는 한페이지당 몇개의 글이 올라갈것인가? 의 문제이다.
        Integer pageSize = 3;

        Integer startRow = (currentPage - 1) * pageSize;


        Map<String, Object> returnMap = writingService.findTotalWritingByTitleWriterContent(byWhatType, hintToFind, startRow, pageSize);
        Integer findedWritingTotal = (Integer) returnMap.get("findedWritingTotal");
        List<WritingDTOSelectVersion> currentPageWritings = (List<WritingDTOSelectVersion>)returnMap.get("currentPageWritings");

        int totalPages = (int) Math.ceil((double) findedWritingTotal / pageSize);

        Integer pageGroupSize = 5;

        Integer currentGroup = (int) Math.ceil((double) currentPage / pageGroupSize);
        Integer currentGroupFirstPage = (currentGroup - 1) * pageGroupSize + 1;
        Integer currentGroupLastPage = Math.min(currentGroupFirstPage + pageGroupSize - 1, totalPages);



        model.addAttribute("currentPageWritings", currentPageWritings);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentGroupFirstPage", currentGroupFirstPage);
        model.addAttribute("currentGroupLastPage", currentGroupLastPage);
        model.addAttribute("pageGroupSize", pageGroupSize);

        model.addAttribute("byWhatType", byWhatType);
        model.addAttribute("hintToFind", hintToFind);

        return "/contact/boardHomeFind";
    }

    @GetMapping("/writing-page")
    public String boardWritingPageControllerMethod(Model model, HttpServletRequest request) {
        model.addAttribute("writingDTO", new WritingDTO());
        String id = FindLoginedMemberIdUtil.findLoginedMember(request);
        model.addAttribute("id", id);
        return "/contact/writingPage";
    }

    @PostMapping("/writing-page")
    public String boardWritingPagePostMappingControllerMethod(@Valid @ModelAttribute WritingDTO writingDTO, BindingResult bindingResult, Model model) {

        String title = writingDTO.getTitle();
        String userId = writingDTO.getUserId();
        Boolean secretWritingCheckBox = writingDTO.getSecretWritingCheckBox();
        String writingPassword = writingDTO.getWritingPassword();
        String content = writingDTO.getContent();

        if (bindingResult.hasErrors()) {
            if (secretWritingCheckBox.equals(true)) {
                model.addAttribute("show", true);
            }

            return "contact/writingPage";
        }


        if (secretWritingCheckBox.equals(true)) {
            if (writingPassword.equals("")) {
                model.addAttribute("show", true);
                model.addAttribute("notInsertPassword", "비밀번호를 안쓰셨어요");
                return "/contact/writingPage";
            }
        }

        Integer isPrivate = secretWritingCheckBox ? 1 : 0;
        writingMapper.insertWriting(title, userId, isPrivate, writingPassword, content);


        return "redirect:/board-home";
    }

    @GetMapping("show-writing")
    public String showWritingControllerMethod(@RequestParam String writingNum, Model model){

        WritingDTOSelectVersion findedWritingByWritingNum = writingMapper.findWritingByWritingNum(writingNum);
        model.addAttribute("findedWritingByWritingNum", findedWritingByWritingNum);


        return "/contact/";

    }


}
