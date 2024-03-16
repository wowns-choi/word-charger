package firstportfolio.wordcharger.controller.payment;
import firstportfolio.wordcharger.DTO.*;
import firstportfolio.wordcharger.exception.payment.JsonParsingException;
import firstportfolio.wordcharger.exception.payment.PaymentVerificationException;
import firstportfolio.wordcharger.repository.OrdersMapper;
import firstportfolio.wordcharger.repository.ProductsMapper;
import firstportfolio.wordcharger.sevice.payment.*;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderV2Controller {

    private final ProductsMapper productsMapper;
    private final GetAccessTokenService accessToken;
    private final SinglePaymentQueryService singlePaymentQueryService;
    private final OrdersMapper ordersMapper;
    private final PostValidationService postValidationService;
    private final InsertPaymentsRowService insertPaymentsRowService;
    private final ShowOrderSheetService showOrderSheetService;
    private final OrderProcessService orderProcessService;
    private final PreparePaymentService preparePaymentService;

    @GetMapping("/payment-home")
    public String paymentHomeControllerMethod() {
        return "/payment/paymentHome";
    }

    @GetMapping("/show-order-sheet")
    public String showOrderSheet(@RequestParam String productId, HttpServletRequest request, Model model) {
        String returnString = showOrderSheetService.showOrderSheet(productId, request, model);
        if (returnString.equals("outOfStock")) {
            return "/payment/paymentHome";
        } else{
            return "/payment/orderSheet";
        }
    }

    @PostMapping("/order-process")
    @ResponseBody
    public Map<String, String> orderProcess(@ModelAttribute OrderFormDTO orderFormDTO, HttpServletRequest request) {
        Map<String, String> returnMap = orderProcessService.orderProcess(orderFormDTO, request);
        return returnMap;
    }

    //결제 정보 사전 검증하기
    @GetMapping("/pre-validation")
    @ResponseBody
    public Map<String, Object> preparePayment(@RequestParam String productId,@RequestParam String merchantUid ,HttpServletRequest request) {

        Map<String, Object> returnMap = preparePaymentService.preparePayment(productId, merchantUid, request);
        return returnMap;
    }

    @PostMapping("/payment-response")
    @ResponseBody
    public String paymentResponeControllerMethod(@RequestBody PaymentDTO paymentDTO, HttpServletRequest request) {

    // 1. orders 테이블에 결제건에 해당하는 행의 status 컬럼을 Processing 이라고 바꿔줘야함.
        // 여기서 Processing 이란, 주문에 대한 결제를 처리 중이다. 라는 뜻이다. 아직 사후검증(결제됬어야 할 금액과 실제로 결제한 금액이 같은지 여부를 검증)을 하기 전이니, 결제를 처리 중인게 맞지.
        ordersMapper.updateStatusByMerchantUid(paymentDTO.getMerchant_uid(),"Processing");

    // 2. 사후검증 시작
        try {
            // 2-1) Imp_uid 와 merchant_uid 를 꺼내오기.
            String impUid = paymentDTO.getImp_uid();
            String merchantUid = paymentDTO.getMerchant_uid();

            // 2-2) access 토큰 얻어오기
            TokenResponseDTO tokenDTO = accessToken.getAccessToken();

            // 2-3) 얻은 토큰으로 결제단건조회를 함.
            Integer memberId = FindLoginedMemberIdUtil.findLoginedMember(request);
            ResponseEntity<String> response = singlePaymentQueryService.singlePaymentQuery(impUid, tokenDTO);

            // 2-4) 일단, payments 테이블에 행을 삽입한다. 왜? 사후검증(결제됬어야 할 금액과 실제로 결제한 금액이 같은지 여부를 검증)여부와는 무관하게, 일단은 결제가 됬으니까.
                // ㄱ. 포트원으로부터 온 HTTP응답의 body 가져오기
                String responseBody = response.getBody();
                // ㄴ. INSERT INTO PAYMENTS
                insertPaymentsRowService.insertRow(memberId, responseBody);

            // 2-5) 사후검증
            postValidationService.postValidation(response);


            //판매자들을 인도하는 페이지에서, 판매자들이 택배회사와 송장번호를 입력하면 그걸 받는 컨트롤러에서 orders 테이블의 status 컬럼값을 Shipped 로 변경하도록 해주면 됨.
            //그리고 고객들이 보는 페이지에서는 payments 테이블의 status 컬럼이 아니라, orders 테이블의 status 컬럼값에 따라서, 표시가 되도록 하면 됨.
            return "success";

        } catch (PaymentVerificationException e) {
        //사후검증에 실패한 경우 :
            // orders 테이블의 status, fail_reason 컬럼을 실패했다고 업데이트 해주는 것은 SinglePaymentQueryService 에서 해줬기 때문에 그 과정 생략.

            Integer portOneAmount = e.getPortOneAmount();
            Integer myDbAmount = e.getMyDbAmount();
            Integer priceDifference = e.getPriceDifference();

            if (priceDifference > 0) {
                //덜 결제된 경우
                return "결제했어야 할 금액 : " + myDbAmount + ", 결제된 금액 : " + portOneAmount + ", " + priceDifference + "가 더 결제되어야 합니다. 최대한 빠른 시일 내에 당사 직원이 연락드리겠습니다. " ;
            } else{
                //더 결제된 경우 => 정상처리로 보고 정상처리를 해줘야지.
                return "결제했어야 할 금액 : " + myDbAmount + ", 결제된 금액 : " + portOneAmount + ", " + priceDifference + "가 더 결제되었습니다. 최대한 빠른 시일 내에 당사 직원이 연락드리겠습니다." ;
            }
        } catch (JsonParsingException e) {
            //JSON 파싱에 실패한 경우
            return "결제 중 오류가 발생되었습니다. JsonParsingException";
        } catch(Exception e){
            return "결제 중 오류가 발생되었습니다. Exception";
        }
    }

    @RequestMapping("/payment-fail")
    public String paymentFail(@RequestParam String merchantUid, HttpServletRequest request, @RequestParam String impUid){
        // 1. 결제자체가 실패한 경우이므로, 사전검증에서 감소시켰던 재고를 +1 해줘야 한다.
            // 1-1) orders 테이블에서 해당 행의 productId 를 찾아라.
            Integer productId = ordersMapper.findProductIdByMerchantUid(merchantUid);
            // 1-2) 그 productId 를 가진 상품의 stock 컬럼을 1 증가시키자.
            productsMapper.stockCountPlusByProductId(String.valueOf(productId), 1);

        // 2. orders 테이블의 status 를 PAYMENT_FAILED 로 처리한다.
            // orders 테이블에 해당 merchantUid 를 가진 행의 status컬럼값이 PAYMENT_FAILED 이면 결제자체가 실패 됬다는걸로 알면 됨.
            ordersMapper.updateStatusByMerchantUid(merchantUid, "PAYMENT_FAILED");

        // 3. payments 테이블의 행을 넣어준다.
            // 결제가 성공했든, 결제가 (잔액부족, 카드정보오류등으로) 실패했든, 결제단건조회를 해서, 포트원으로부터 받은 정보 그대로를 payments 테이블에 저장해두도록 한다.
            // 즉, payments 테이블 = 포트원 결제단건조회 결과

            // 3-1) access 토큰 얻어오기
            TokenResponseDTO tokenDTO = accessToken.getAccessToken();

            // 3-2) 얻은 토큰으로 결제단건조회를 함.
            Integer memberId = FindLoginedMemberIdUtil.findLoginedMember(request);
            ResponseEntity<String> response = singlePaymentQueryService.singlePaymentQuery(impUid, tokenDTO);

            // 3-3) payments 테이블에 행을 삽입한다.
                // ㄱ. 포트원으로부터 온 HTTP응답의 body 가져오기
                String responseBody = response.getBody();
                // ㄴ. INSERT INTO PAYMENTS
                insertPaymentsRowService.insertRow(memberId, responseBody);

        return "/payment/paymentHome";
    }

    @RequestMapping("/webhook")
    public ResponseEntity<String> method1(@RequestBody WebhookDTO webhookDTO) {

        // 웹훅이란?
        // 내가 파악하기로는 가상계좌발급이 되거나 발급된 가상계좌로 구매자가 돈을 입금하는 경우, 포트원이 자동으로 이곳으로 HTTP 메세지를 보내주는 것을 말한다.
        // 이외에도 결제가 승인되거나, 예약결제가 시도되었을 때, 관리자 콘솔에서 결제취소가 되었을 때 웹훅을 보내준다고 한다.
        // 원래는 폴링(polling) 이라고 해서, 주기적으로 내가 포트원한테 물어봤었어야 됬는데,
        // 웹훅을 사용함으로써 네트워크가 불안정해서 내가 보낸 HTTP요청이 포트원에게 전달되지 못하는 것도 예방할 수 있고,
        // 주기적으로 포트원에게 HTTP요청 안보내도 되니까, 내 프로젝트 입장에서는 리소스도 아낄 수 있겠지.

        // 그런데, 문제는 가상계좌 관련한 건, 이니시스 같은 PG사에게 사업자등록증을 제출하고 상점관리자가 되어야한다.
        // 그래서 당장에 내가 할 수 있는 것은, 결제가 승인되었다는 웹훅을 처리하는 것 밖에 없다.

        // 포트원이 준 웹훅을 파싱해봤더니 아래와 같은 데이터가 들어있었다.
        log.info("webhookDTO============================{}", webhookDTO);
        // WebhookDTO(imp_uid=imp_1234567890, merchant_uid=merchant_1234567890, status=paid, cancellation_id=null)

        // 이걸 가지고 어떤 걸 할 수 있을까?
        // 내 payments 테이블에 이 merchant_uid 를 가진 행의 status 가 이 웹훅의 status 와 동일한지 여부?
        //paid(결제완료), ready(브라우저 창 이탈, 가상계좌 발급완료 미결제 상태), failed(신용카드 한도초과, 체크카드 잔액부족, 브라우저 창 종료, 취소버튼 클릭 등 결제실패상태)
        // 일치한다면, 더 이상 해줄 건 없어.
        // 그러나, 일치하지 않는다면?
        // 다시 한번 결제단건조회를 진행. 이번에 한 결제단건조회에서의 status 컬럼값을 확인.
        // 웹훅이 옳은지, 내 db에 저장된 status 컬럼값이 옳은지 검증.
        // 내 db에 저장된 status 컬럼값이 옳은 경우, 더 이상 해줄 거 없음.
        // 웹훅이 옳은 경우, 내 db에 저장된 행을 지우고, 방금 조회한 결제단건조회의 결과를 다시 삽입해준다.

        // + 해줘야 할 게 있음.
        // 내 db / 웹훅
        // paid / ready : 포트원이 아닌 다른 곳(해커)에서 받은 결제단건조회결과를 바탕으로 사후검증을 진행했다고 판단하고 새롭게 사후검증을 진행.
        // paid / failed : 포트원이 아닌 다른 곳(해커)에서 받은 결제단건조회결과를 바탕으로 사후검증을 진행했다고 판단하고 새롭게 사후검증을 진행.
        // ready / failed
        // ready / paid
        // failed / paid,
        // failed / ready
        // 이렇게 6가지의 경우가 있을 수 있다.


        //포트원 서버에게 웹훅을 잘 받았다고 보냄.
        return ResponseEntity.ok().body("Webhook received successfully");
    }



}
