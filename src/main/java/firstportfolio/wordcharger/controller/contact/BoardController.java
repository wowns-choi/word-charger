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

        Integer totalWriting = writingMapper.countAllWriting();
        Integer pageSize = 10;

        double totalPages = Math.ceil((double) totalWriting / pageSize);
        Integer totalPagesInteger = (int) totalPages;
        model.addAttribute("totalPagesInteger", totalPagesInteger);


        int startRow = page - 1;
        List<WritingDTOSelectVersion> currentPageWritings = writingMapper.findCurrentPageWritings(startRow);
        model.addAttribute("currentPageWritings", currentPageWritings);
        log.info("allWriting================{}", currentPageWritings);

        model.addAttribute("page", page);
        log.info("page====={}", page);

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



//
////        log.info("{}",writingNum); null
////        log.info("{}",title);  제목1111
////        log.info("{}",userId); wowns590
//        log.info("{}",secretWritingCheckBox); //체크하면 true || 체크안하면false
//        log.info("{}", writingPassword);  //체크해서 입력하면 1234 || 체크 안하면 ""(빈문자열) //근데 체크 안하는게 불가능하도록 위에서 코드 짬.
//        log.info("================={}=================", writingPassword.equals(""));
//        log.info("================={}=================", writingPassword==null);
////        log.info("{}", writingDate); null
////        log.info("{}", content); 본문내용1111
////        log.info("{}", viewNumber); null
////        log.info("{}", likeNumber); null

        Integer isPrivate = secretWritingCheckBox ? 1 : 0;
        writingMapper.insertWriting(title, userId, isPrivate, writingPassword, content);



        return "redirect:/board-home";
    }




}
