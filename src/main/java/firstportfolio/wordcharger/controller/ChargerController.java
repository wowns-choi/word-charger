package firstportfolio.wordcharger.controller;

import firstportfolio.wordcharger.DTO.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChargerController {

    @GetMapping("/chargerHome")
    public String chargerHomeController(HttpServletRequest request){
        return "/charger/chargerHome";
    }
}
