package firstportfolio.wordcharger.controller.join;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.*;
import firstportfolio.wordcharger.sevice.join.InsertMemberService;
import firstportfolio.wordcharger.sevice.join.JoinService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JoinController {
    private final JoinService joinService;
    private final InsertMemberService insertMemberService;
    //해싱을 위해서.
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/Join-form")
    public String getJoinFormControllerMethod(HttpServletRequest request,
                                              Model model,
                                              @RequestParam String myCheckbox1,
                                              @RequestParam String myCheckbox2,
                                              @RequestParam String myCheckbox3
    ){

        MemberJoinDTO memberJoinDTO = new MemberJoinDTO();
        memberJoinDTO.setMyCheckbox1(myCheckbox1);
        memberJoinDTO.setMyCheckbox2(myCheckbox2);
        memberJoinDTO.setMyCheckbox3(myCheckbox3);

        model.addAttribute("memberJoinDTO", memberJoinDTO);
        return "/login/joinForm";
    }

    @PostMapping("/Join-form")
    public String postJoinFormControllerMethod (@Valid @ModelAttribute MemberJoinDTO memberJoinDTO, BindingResult bindingResult){
        //유효성 검사
        if (memberJoinDTO.getUserId().equals("") ) {
            bindingResult.rejectValue("userId", null, "아이디를 입력 해주세요");
        }
        if (memberJoinDTO.getPassword().equals("")) {
            bindingResult.rejectValue("password", null, "비밀번호를 입력해주세요");
        }
        if (memberJoinDTO.getUserName().equals("")) {
            bindingResult.rejectValue("userName", null, "이름을 입력해주세요");
        }

        if (memberJoinDTO.getZipCode().equals("")|| memberJoinDTO.getStreetAddress().equals("")|| memberJoinDTO.getAddress().equals("")) {
            bindingResult.rejectValue("zipCode", null, "우편번호를 찾기를 통해 주소를 찾아주세요.");
        }
        if (memberJoinDTO.getPhoneNumberStart().equals("") || memberJoinDTO.getPhoneNumberMiddle().equals("") || memberJoinDTO.getPhoneNumberEnd().equals("")) {
            bindingResult.rejectValue("phoneNumberStart", null, "전화번호를 입력해주세요");
        }

        if (bindingResult.hasErrors()) {
            return "/login/joinForm";
        }

        //checkbox value : on => 1 , null => 0  변환
        MemberJoinDTO changedMemberJoinDTO = joinService.onAndNullChange(memberJoinDTO);

        // 비밀번호 해싱
        String rawPassword = changedMemberJoinDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        changedMemberJoinDTO.setPassword(encodedPassword);

        //insert 진행 서비스
        insertMemberService.insertMember(changedMemberJoinDTO);
        return "/home/home";
    }




}
