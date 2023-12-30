package firstportfolio.wordcharger.controller.contact;

import firstportfolio.wordcharger.DTO.WritingDTO;
import firstportfolio.wordcharger.DTO.WritingDTOSelectVersion;
import firstportfolio.wordcharger.repository.WritingMapper;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final WritingMapper writingMapper;

    @GetMapping("/board-home")
    public String boardHomeControllerMethod(@RequestParam(value="page", defaultValue = "1") Integer page, Model model) {

        int startRow = page - 1;
        List<WritingDTOSelectVersion> currentPageWritings = writingMapper.findCurrentPageWritings(startRow);
        model.addAttribute("currentPageWritings", currentPageWritings);
        log.info("allWriting================{}", currentPageWritings);


        Integer totalWriting = writingMapper.countAllWriting();
        Integer pageSize = 2;

        double totalPagesDouble = Math.ceil((double) totalWriting / pageSize);
        Integer totalPagesInteger = (int) totalPagesDouble;

        if (totalPagesInteger <= 5) {
            model.addAttribute("page", page);
            model.addAttribute("totalPagesInteger", totalPagesInteger);
            model.addAttribute("fivePageUnder", true);
            return "/contact/boardHome";
        }

        if(totalPagesInteger >5){
            if (page == 1 || page == 2) {
                model.addAttribute("page", page);
                model.addAttribute("totalPageInteger", totalPagesInteger);
                model.addAttribute("fivePageUp1", true);
                return "/contact/boardHome";
            }

            if(totalPagesInteger<page+2){
                model.addAttribute("page", page);
                model.addAttribute("totalPageInteger", totalPagesInteger);
                model.addAttribute("fivePageUp2", true);
                return "/contact/boardHome";
            }

            model.addAttribute("page", page);
            if (totalPagesInteger - page > 2) {
                String totalPagesIntegerToString = totalPagesInteger.toString();
                String totalPagesString = "..." + totalPagesIntegerToString;
                model.addAttribute("totalPagesString", totalPagesString);
                model.addAttribute("totalPageInteger", totalPagesInteger);

            }

            model.addAttribute("fivePageUp3", true);
            return "/contact/boardHome";
        }
        return "/contact/boardHome";
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
            if(secretWritingCheckBox.equals(true)){
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




}
