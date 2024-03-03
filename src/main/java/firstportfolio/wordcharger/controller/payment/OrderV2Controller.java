package firstportfolio.wordcharger.controller.payment;

import firstportfolio.wordcharger.DTO.PaymentDTO;
import firstportfolio.wordcharger.DTO.PortOneCommnicationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderV2Controller {

    private final RestTemplate restTemplate;

    @GetMapping("/payment-home")
    public String paymentHomeControllerMethod() {

        return "/payment/paymentHome";
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

    //결제 정보 사전 검증하기
    @GetMapping("/pre-validation")
    @ResponseBody
    public String preparePayment(@RequestParam String productId) {


        ////////////////////////////productId 로 재고가 있는지 검증하는 코드 필요///////////////////
        //임의로 일단은, productId 가 1인 경우, amount 가 1원. 2인 경우 2원. 3인 경우 3원 으로 할게
        Integer amount = 0;
        if(productId.equals("1")){
            amount = 1;
        } else if(productId.equals("2")){
            amount = 2;
        } else if (productId.equals("3")) {
            amount = 3;
        }




        /////////////////////////////paymentId(주문번호) 생성 //////////////////////////////////
        long nano = System.currentTimeMillis();
        String paymentId = "pid-" + nano;
        log.info("paymentId====================={}", paymentId);

        ////////////////////////////////사전검증 요청 전, access_token 을 받아와야 함. ///////////////////////////////////////////////////
        String url2 = "https://api.iamport.kr/users/getToken";

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map2 = new LinkedMultiValueMap<>();
        map2.add("imp_key", "5737118685604387");
        map2.add("imp_secret", "iny0Od4aEZ74VzpTeqrwXgidvVnrITMppgWVrKbhACXOvwqHRw2eS182qmEtORqP0xnD6DN3hXewCQAd");

        HttpEntity<MultiValueMap<String, String>> entity2 = new HttpEntity<>(map2, headers2);

        RestTemplate restTemplate = new RestTemplate();
        String responseJson = restTemplate.postForObject(url2, entity2, String.class);

        log.info("responseJson========================+{}", responseJson);


        //////////////////////////포트원에게 사전검증 요청/////////////////////////////////
        String url = "https://api.iamport.kr/payments/prepare";

        HttpHeaders headers = new HttpHeaders(); //보낼 HTTP요청메세지의 헤더를 설정할 때 쓰일 객체.
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer 1c0881f7f150054c8de86fc1d91f850b20c1216c");

        double amountDouble = amount;

        Map<String, Object> map = new HashMap<>();
        map.put("merchant_uid", paymentId);
        map.put("amount", amountDouble);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // restTemplate.postForObject(url, entity, String.class); 이 코드로 인해, 포트원 서버에 HTTP요청 감.
        // 갔다와서 받은 리턴값을 response 라는 String 타입 변수에 담음.
        String response = restTemplate.postForObject(url, entity, String.class);
        log.info("포트원이 뭘 줬을까? ===== {}", response);
        return "abc";
    }


}
