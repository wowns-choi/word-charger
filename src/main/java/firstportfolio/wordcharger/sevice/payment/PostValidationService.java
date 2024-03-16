package firstportfolio.wordcharger.sevice.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import firstportfolio.wordcharger.exception.payment.JsonParsingException;
import firstportfolio.wordcharger.exception.payment.PaymentVerificationException;
import firstportfolio.wordcharger.repository.OrdersMapper;
import firstportfolio.wordcharger.repository.PaymentsMapper;
import firstportfolio.wordcharger.repository.ProductsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
// @Transactional 쓰지 않는다. update 문 여러 개 있는 거 아님? 왜 안씀? 아니지. 지금 if else 조건문으로 쓰여져 있잖아. 즉, 어떠한 경우라도 단 하나의 update 쿼리만 발생하게 된다.
// 다시말해서, 하나의 update 쿼리이기 때문에, @Transactional 을 쓰지 않는다.
public class PostValidationService {
    private final OrdersMapper ordersMapper;
    private final InsertPaymentsRowService insertPaymentsRowService;
    private final ProductsMapper productsMapper;

    public void postValidation(ResponseEntity<String> response){
        log.info("response={}", response); // 아래와 같은 것들이 포트원으로부터 왔다.
        //<200 OK OK,
        // { -- HTTP응답의 body 시작
        // "code":0,
        // "message":null,
        // "response":
        // {
        // "amount":1,
        // "apply_num":"31491375",
        // "bank_code":null,
        // "bank_name":null,
        // "buyer_addr":"\uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uac15\ub0a8\uad6c \uc0bc\uc131\ub3d9",
        // "buyer_email":"wowns590@naver.com",
        // "buyer_name":"\ud3ec\ud2b8\uc6d0 \uae30\uc220\uc9c0\uc6d0\ud300",
        // "buyer_postcode":"123-456",
        // "buyer_tel":"010-1234-5678",
        // "cancel_amount":0,
        // "cancel_history":[],
        // "cancel_reason":null,
        // "cancel_receipt_urls":[],
        // "cancelled_at":0,
        // "card_code":"366",
        // "card_name":"\uc2e0\ud55c\uce74\ub4dc",
        // "card_number":"559410*********4",
        // "card_quota":0,
        // "card_type":1,
        // "cash_receipt_issued":false,
        // "channel":"pc",
        // "currency":"KRW",
        // "custom_data":null,
        // "customer_uid":null,
        // "customer_uid_usage":null,
        // "emb_pg_provider":null,
        // "escrow":false,
        // "fail_reason":null,
        // "failed_at":0,
        // "imp_uid":"imp_088022300265",
        // "merchant_uid":"57008833-33010",
        // "name":"\ub2f9\uadfc 10kg",
        // "paid_at":1709722069,
        // "pay_method":"card",
        // "pg_id":"INIBillTst",
        // "pg_provider":"html5_inicis",
        // "pg_tid":"StdpayCARDINIBillTst20240306194748374762",
        // "receipt_url":"https:\/\/iniweb.inicis.com\/receipt\/iniReceipt.jsp?noTid=StdpayCARDINIBillTst20240306194748374762",
        // "started_at":1709722022,
        // "status":"paid",
        // "user_agent":"Mozilla\/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit\/605.1.15 (KHTML, like Gecko) Version\/16.6 Safari\/605.1.15",
        // "vbank_code":null,
        // "vbank_date":0,
        // "vbank_holder":null,
        // "vbank_issued_at":0,
        // "vbank_name":null,
        // "vbank_num":null
        // }
        // }-- HTTP응답의 body 끝,
        // [
        // Date:"Wed, 06 Mar 2024 10:47:49 GMT",
        // Content-Type:"application/json;
        // charset=UTF-8",
        // Content-Length:"1393",
        // Connection:"keep-alive",
        // Server:"Apache",
        // X-Content-Type-Options:"nosniff",
        // X-XSS-Protection:"1; mode=block",
        // X-Frame-Options:"SAMEORIGIN"
        // ]
        // >

        // 사후검증 계획은 아래와 같다.
            // 1. 포트원으로부터 온 데이터 중, "merchant_uid":"57008833-33010" 이거를 꺼내서,
            // 내 DB의 orders 테이블에 있는 merchant_uid 컬럼의 값이 57008833-33010 인 행을 조회한다.
            // 2. 조회된 행의 amount 컬럼의 값은 해당 주문이 결제했어야 하는 금액을 넣어두었다.
            // 3. 따라서, 포트원으로부터 온 데이터 중 amount 와 방금 조회된 행의 amount 컬럼값이 일치하는지 검사한다.
            // 4. 같다면, 사후검증 통과.

        // 사후검증 시작.
        // 1. 포트원으로부터 온 HTTP응답 메세지 바디 가져오기
        String responseBody = response.getBody();

        int amount = 0;
        Integer findAmount = 0;
        String merchantUid = "";

        try {
            // 2.포트원이 보내준 데이터 중 amount 와 merchant_uid 를 파싱.
            //   그러기 위해서, 포트원으로부터 온 HTTP응답을 보면, 크게 code, message, response 이렇게 3가지로 구성되어 있는데, 이 중 response 를 파헤친다.

                // 2-1) jackson 라이브러리의 ObjectMapper 인스턴스 생성.
                // 왜? ObjectMapper 는 유연해서, 'JSON 형식'의 '문자열'을 파싱해서 JsonNode 타입 객체로 변환해줄 수 있다.
                // JsonNode 타입 객체를 알면, .path 메서드를 통해 쉽게 JSON 데이터에 접근할 수 있게된다.
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode responseNode = rootNode.path("response");
                // 2-2) 포트원이 나에게 보내준 HTTP응답메세지 중 얻고 싶었던 데이터인 amount 와 merchantUid 를 얻었다. 얻어오는 과정을 보니, JsonNode 타입 객체는 JSON 데이터를 계층적으로 구분해 놓는다고 예상됨.
                amount = responseNode.path("amount").asInt();
                merchantUid = responseNode.path("merchant_uid").asText();

            // 3. 내 데이터베이스에서 파싱해서 얻은 정보 중 merchantUid 와 동일한 merchant_uid 컬럼값을 지닌 행의 amount 를 조회
            // SELECT amount FROM orders
            // WHERE merchant_uid = #{merchantUid}
            findAmount = ordersMapper.findAmountByMerchantUid(merchantUid);

            // 4. 사후검증 판단
            if (amount == findAmount) {
                // 4-1) 일치하는 경우
                //  ORDERS 테이블의 status 컬럼값을 Paid 로 바꾸어주면 된다. 이때, Paid 의 의미는 이니시스에서의 결제는 성공적으로 처리되었으며, 사후검증도 통과했다는 의미로 정의한다.
                ordersMapper.updateStatusByMerchantUid(merchantUid,"Paid");
                //  PAYMENTS 테이블의 status 컬럼값을 바꾸어주어야 하는지에 대하여 생각해봤다. 결론 : 건들지 않는다.
                // 왜? payments 테이블의 status 컬럼값과 fail_reason 컬럼값을 포함한 모든 컬럼값은 결제단건조회한 결과 그대로를 저장해두는 것이 옳다고 생각한다. 즉, payments 테이블 = 포트원 결제단건조회 결과

            } else {
                // 4-2) 일치하지 않는 경우
                // 예외를 터뜨린다. 내가 만든 RuntimeException 인 PaymentVerificationException 을 터뜨려 줄 것이다.
                throw new PaymentVerificationException("SinglePaymentQueryService : 결제 검증 실패 - 결제금액 불일치");
            }
        } catch (PaymentVerificationException e) {
            // (사후검증에 통과하지 못한 경우) == (결제됬어야 하는 금액과 실제 결제된 금액이 불일치 하는 경우)
            log.error("SinglePaymentQueryService : 사후검증 중 결제금액 불일치", e);

            Integer priceDifference = findAmount - amount;

            if(priceDifference >0){
                // 사용자가 덜 지불한 경우
                ordersMapper.updateStatusByMerchantUid(merchantUid, "POST_VALIDATION_FAILED_MINUS");
                // payments 테이블에도 status 를 fail 로 해둬야할까?
                // payments 테이블의 status 컬럼값과 fail_reason 컬럼값을 포함한 모든 컬럼값은 결제단건조회한 결과 그대로를 저장해두는 것이 옳다고 생각한다. 즉, payments 테이블 = 포트원 결제단건조회 결과

                ordersMapper.updateFailReasonByMerchantUid(merchantUid, "고객님이 " + priceDifference + "원 더 결제하셔야 합니다.");
                throw new PaymentVerificationException(amount, findAmount, priceDifference);
            } else if (priceDifference < 0) {
                // 사용자가 더 지불한 경우
                ordersMapper.updateStatusByMerchantUid(merchantUid, "POST_VALIDATION_FAILED_PLUS");
                ordersMapper.updateFailReasonByMerchantUid(merchantUid, "고객님이" + priceDifference + "원 더 결제하셨습니다.");
                throw new PaymentVerificationException(amount, findAmount, priceDifference);
            }

        } catch (JsonProcessingException e) {
            // JsonNode rootNode = objectMapper.readTree(responseBody); 이거 하다가 발생할 수 있는 예외를 처리해줘야 함.
            // 컨트롤러로 다시 예외를 발생시키도록 했음. 왜? 이렇게 잡고 다시 던지면, 로그를 남길 수 있잖아. 그래서, throws 로 던질수도 있었는데, 그렇게 안함.
            log.error("SinglePaymentQueryService : JSON 파싱 중 오류 발생", e);
            throw new JsonParsingException("JSON 파싱 중 오류 발생");
        }

    }
}
