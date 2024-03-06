package firstportfolio.wordcharger.controller.payment;

import firstportfolio.wordcharger.DTO.PaymentDTO;
import firstportfolio.wordcharger.DTO.PortOneCommnicationDTO;
import firstportfolio.wordcharger.DTO.TokenResponseDTO;
import firstportfolio.wordcharger.DTO.WebhookDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
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

        return "/payment/payment";
    }


    //결제 정보 사전 검증하기
    @GetMapping("/pre-validation")
    @ResponseBody
    public String preparePayment(@RequestParam String productId) {


        ////////////////////////////productId 로 재고가 있는지 검증하는 코드 필요///////////////////
        //임의로 일단은, productId 가 1인 경우, amount 가 1원. 2인 경우 2원. 3인 경우 3원 으로 할게
        Integer amount = 0;
        if (productId.equals("1")) {
            amount = 1;
        } else if (productId.equals("2")) {
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
        TokenResponseDTO responseJson = restTemplate.postForObject(url2, entity2, TokenResponseDTO.class);

        log.info("responseJson========================+{}", responseJson);


        //////////////////////////포트원에게 사전검증 요청/////////////////////////////////
        String url = "https://api.iamport.kr/payments/prepare";

        HttpHeaders headers = new HttpHeaders(); //보낼 HTTP요청메세지의 헤더를 설정할 때 쓰일 객체.
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", responseJson.getResponse().getAccess_token());

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


    @PostMapping("/payment-response")
    @ResponseBody
    public ResponseEntity<?> paymentResponeControllerMethod(@RequestBody PaymentDTO paymentDTO) {


        log.info("===============아임피유아이디===============+{}", paymentDTO.getImp_uid());
        log.info("===============아임피유아이디===============+{}", paymentDTO.getImp_uid());
        log.info("===============아임피유아이디===============+{}", paymentDTO.getImp_uid());
        log.info("===============멀첸트유아이디===============+{}", paymentDTO.getMerchant_uid());
        log.info("===============멀첸트유아이디===============+{}", paymentDTO.getMerchant_uid());
        log.info("===============멀첸트유아이디===============+{}", paymentDTO.getMerchant_uid());


        // ============== 사후검증 로직 시작 ===============
        // 1. Imp_uid 와 merchant_uid 를 꺼내오기.
        try {
            String imp_uid = paymentDTO.getImp_uid();
            String merchant_uid = paymentDTO.getMerchant_uid();
            log.info("=======================Imp_uid{}=================", paymentDTO.getImp_uid());
            log.info("=======================merchant_uid{}=================", paymentDTO.getMerchant_uid());


            // 2. access 토큰 얻어오기
            String url2 = "https://api.iamport.kr/users/getToken";

            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map2 = new LinkedMultiValueMap<>();
            map2.add("imp_key", "5737118685604387");
            map2.add("imp_secret", "iny0Od4aEZ74VzpTeqrwXgidvVnrITMppgWVrKbhACXOvwqHRw2eS182qmEtORqP0xnD6DN3hXewCQAd");

            HttpEntity<MultiValueMap<String, String>> entity2 = new HttpEntity<>(map2, headers2);

            RestTemplate restTemplate = new RestTemplate();
            TokenResponseDTO responseJson = restTemplate.postForObject(url2, entity2, TokenResponseDTO.class);

            log.info("responseJson========================+{}", responseJson);

            //3. 얻은 토큰으로 결제단건조회를 함.
            //가지고 있는 정보 :
            // imp_uid 라는 변수에 imp_uid 들어있음.
            // access 토큰은 responseJson.getResponse().getAccess_token()

            //이 2개의 정보를 가지고 결제 단건조회 api 를 호출할거임.
            // + url
            String url = "https://api.iamport.kr/payments/" + imp_uid;

            //헤더 세팅
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", responseJson.getResponse().getAccess_token());

            //바디에 담을 거
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // RestTemplate 사용해서 GET 요청 전송
            RestTemplate restTemplate2 = new RestTemplate();

            ResponseEntity<String> response = restTemplate2.exchange(url, HttpMethod.GET, entity, String.class);

            log.info("response========!!!!!!!!!=========!!!!!!!!1==={}", response);
            log.info("response========!!!!!!!!!=========!!!!!!!!1==={}", response);
            log.info("response========!!!!!!!!!=========!!!!!!!!1==={}", response);

            //<200 OK OK,
            // {"code":0,
            // "message":null,
            // "response":{"amount":1,"apply_num":"31491375","bank_code":null,"bank_name":null,"buyer_addr":"\uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uac15\ub0a8\uad6c \uc0bc\uc131\ub3d9","buyer_email":"wowns590@naver.com","buyer_name":"\ud3ec\ud2b8\uc6d0 \uae30\uc220\uc9c0\uc6d0\ud300","buyer_postcode":"123-456","buyer_tel":"010-1234-5678","cancel_amount":0,"cancel_history":[],"cancel_reason":null,"cancel_receipt_urls":[],"cancelled_at":0,"card_code":"366","card_name":"\uc2e0\ud55c\uce74\ub4dc","card_number":"559410*********4","card_quota":0,"card_type":1,"cash_receipt_issued":false,"channel":"pc","currency":"KRW","custom_data":null,"customer_uid":null,"customer_uid_usage":null,"emb_pg_provider":null,"escrow":false,"fail_reason":null,"failed_at":0,
            // "imp_uid":"imp_088022300265","merchant_uid":"57008833-33010","name":"\ub2f9\uadfc 10kg","paid_at":1709722069,"pay_method":"card","pg_id":"INIBillTst","pg_provider":"html5_inicis","pg_tid":"StdpayCARDINIBillTst20240306194748374762","receipt_url":"https:\/\/iniweb.inicis.com\/receipt\/iniReceipt.jsp?noTid=StdpayCARDINIBillTst20240306194748374762","started_at":1709722022,"status":"paid","user_agent":"Mozilla\/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit\/605.1.15 (KHTML, like Gecko) Version\/16.6 Safari\/605.1.15","vbank_code":null,"vbank_date":0,"vbank_holder":null,"vbank_issued_at":0,"vbank_name":null,"vbank_num":null}},[Date:"Wed, 06 Mar 2024 10:47:49 GMT", Content-Type:"application/json; charset=UTF-8", Content-Length:"1393", Connection:"keep-alive", Server:"Apache", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", X-Frame-Options:"SAMEORIGIN"]>
            //여기서 알 수 있는거.
            // 포트원이 준 imp_uid 라는 건, 포트원 서버에서 요청한 결제에 부여한 고유한 아이디이다.
            // 그리고, merchant_uid 라는 건 내 프로그램쪽에서 생성한 내 결제에 대한 고유한 아이디이다.

            // 여기서 해야하는 거.
            // 내 데이터베이스에 있는 해당 merchant_uid 가 지불해야하는 값이 지금 들어온 값 중 amount 가 지불한 금액이거든?
            // 이거와 동일한지 따져줘
            // 만약 동일하다면, ok 를 jsp에 전달하고, 만약 동일하지 않다면, jsp 에는 에러가 났음을 알리고 db 에는 요청한 금액이 더 적다든지, 뭐 그러한 정보를 담아놔야해. 해킹 당한 경우 등도.


            return ResponseEntity.ok().body("결제 정보 수신 및 처리 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("요청 처리 중 오류 발생");


        }


    }

    @RequestMapping("/webhook")
    public ResponseEntity<String> method1(@RequestBody WebhookDTO webhookDTO) {

        log.info("webhookDTO============================{}", webhookDTO);
        log.info("webhookDTO============================{}", webhookDTO.getImp_uid());
        log.info("webhookDTO============================{}", webhookDTO.getMerchant_uid());
        log.info("webhookDTO============================{}", webhookDTO.getCancellation_id());
        log.info("webhookDTO============================{}", webhookDTO.getStatus());

        //테스트 결과
        // WebhookDTO(imp_uid=imp_1234567890, merchant_uid=merchant_1234567890, status=paid, cancellation_id=null)

        return ResponseEntity.ok().body("Webhook received successfully");
    }
}
