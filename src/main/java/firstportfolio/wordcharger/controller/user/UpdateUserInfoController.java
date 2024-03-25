package firstportfolio.wordcharger.controller.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.AddressMapper;
import firstportfolio.wordcharger.repository.EmailMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.PhoneMapper;
import firstportfolio.wordcharger.sevice.user.FindUserAllDataService;
import firstportfolio.wordcharger.sevice.user.UpdateUserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UpdateUserInfoController {


    private final FindUserAllDataService findUserAllDataService;
    private final UpdateUserInfoService updateUserInfoService;

    @GetMapping("/update-user-info")
    public String updateUserInfoControllerMethod(@RequestParam String id, Model model) {
        findUserAllDataService.findAllUserInfo(model, id);
        return "/user/userInfoUpdate";
    }

    @PostMapping("/update-user-info")
    public String updateUserInfoPostControllerMethod(@ModelAttribute MemberAllDataFindDTO memberAllDataFindDTO, Model model) {

        log.info("userInfo 수정폼에 유저가 입력한 걸 바인딩 한 객체 ={}", memberAllDataFindDTO);
        updateUserInfoService.updateUserInfo(memberAllDataFindDTO, model);
        return "/home/loginedHome2";
    }



}
