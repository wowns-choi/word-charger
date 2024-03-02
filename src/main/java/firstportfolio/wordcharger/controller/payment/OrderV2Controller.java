package firstportfolio.wordcharger.controller.payment;

import firstportfolio.wordcharger.DTO.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderV2Controller {


    @GetMapping("/payment-home")
    public String paymentHomeControllerMethod() {

        return "/payment/payment";
    }

    @PostMapping("/payment-response")
    @ResponseBody
    public Map<String, String> paymentResponeControllerMethod(@RequestBody PaymentDTO paymentDTO) {
        Map<String, String> map = new HashMap<>();
        map.put("imp_uid", paymentDTO.getImp_uid());
        map.put("merchant_uid", paymentDTO.getMerchant_uid());
        log.info("=======================Imp_uid{}=================", paymentDTO.getImp_uid());
        log.info("=======================merchant_uid{}=================", paymentDTO.getMerchant_uid());
        log.info("================================={}", paymentDTO);

        return map;

    }



}
