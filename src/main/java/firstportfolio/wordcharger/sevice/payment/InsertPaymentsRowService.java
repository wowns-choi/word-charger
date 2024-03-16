package firstportfolio.wordcharger.sevice.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import firstportfolio.wordcharger.DTO.PaymentCancelAnnotation;
import firstportfolio.wordcharger.exception.payment.JsonParsingException;
import firstportfolio.wordcharger.exception.payment.PaymentVerificationException;
import firstportfolio.wordcharger.repository.OrdersMapper;
import firstportfolio.wordcharger.repository.PaymentsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

// @Transactional 쓰지 않는다. insert 쿼리 하나만 있기 때문이다. 하나의 데이터베이스 작업은 자체적으로 하나의 트랜잭션으로 처리된다.
@Slf4j
@RequiredArgsConstructor
@Service
public class InsertPaymentsRowService {
    private final OrdersMapper ordersMapper;
    private final PaymentsMapper paymentsMapper;

    public void insertRow(Integer memberId, String responseBody ){

        //member_id 는 memberId 를 그대로 넣으면 됨.

        //responseBody 에 보면, merchant_uid 라는 게 있는데, 그걸 이용해서 order테이블에서 행을 조회해서, 그 행의 id컬럼값을 빼서 payment테이블의 order_id 컬럼의 값으로 하면 됨.

        //responseBody에서 merchant_uid 꺼내기
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode responseNode = rootNode.path("response");

            //결제취소시 필요한 정보 3개 : amount, impUid, merchantUid + 혹시 몰라서, currency 까지 포함.
                String impUid = responseNode.path("imp_uid").asText(); //포트원 거래 고유번호 : imp_088022300265
                String merchantUid = responseNode.path("merchant_uid").asText(); //가맹점 주문번호. 내 프로젝트에서 결제건마다 부여한 고유번호로서 랜덤한 숫자로 구성함 : 57008833-33010
                int amount = responseNode.path("amount").asInt(); //결제건의 결제금액 : 1
                String currency = responseNode.path("currency").asText(); //결제통화 구분코드 (KRW..등) : KRW
            //관리자 페이지에서 status 가 fail 이거나 cancelled 인 것만 보기 뭐 이런거 할 때 필요할 거 같아서.
                String status = responseNode.path("status").asText(); //결제건의 결제상태 : paid(결제완료), ready(브라우저 창 이탈, 가상계좌 발급완료 미결제 상태), failed(신용카드 한도초과, 체크카드 잔액부족, 브라우저 창 종료, 취소버튼 클릭 등 결제실패상태)
                    //결제실패 (결제 자체가 성공된 적이 없음. 카드한도초과, 유효하지 않은 카드번호, 은행 거절 등)
                    String failReason = responseNode.path("fail_reason").asText(); // 결제실패 사유로, 결제상태가 결제실패(failed)가 아닐 경우 null로 표시됨 : null
                    Long failedAtLong = responseNode.path("failed_at").asLong(); //결제실패시각. 결제상태가 결제실패(failed)가 아닌 경우 0으로 표시됨. : 0
                    LocalDateTime failedAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(failedAtLong), ZoneId.systemDefault());

            String payMethod = responseNode.path("pay_method").asText(); //결제수단 구분코드 :
                    // card 외
                    // trans(실시간 계좌이체),
                    // vbank(가상계좌),
                    // phone(휴대폰소액결제),
                    // cultureland(컬쳐랜드상품권  (구)문화상품권),
                    // smatculture(스마트문상(게임 문화 상품권)),
                    // happymoney(해피머니),
                    // booknlif(도서문화상품권),
                    // culturegift(문화상품권)
                    // samsung(삼성페이),
                    // kakaopay(카카오페이)
                    // naverpay(네이버페이)
                    // payco(페이코)
                    // lpay(LPAY),
                    // ssgpay(SSG페이),
                    // tosspay(토스페이),
                    // applepay(애플페이),
                    // pinpay (핀페이)
                    // skpay(11pay(구.SKPay))
                    // wechat(위쳇페이),
                    // alipay(알리페이),
                    // unionpay(유니온페이),
                    // tenpay(텐페이)
                    // paysbuy(페이스바이)
                    // econtext(편의점 결제)
                    // molpay(MOL페이)
                    // point(베네피아 포인트 등 포인트 결제),
                    // paypal(페이팔)
                    // toss_brandpay(토스페이먼츠 브랜드페이)
                    // naverpay_card(네이버페이 - 카드)
                    // naverpay_point(네이버페이 - 포인트)


            // 결제와 관련된 정보
            String name = responseNode.path("name").asText(); // 결제건의 제품명 : \ub2f9\uadfc 10kg
            Long paidAtLong = responseNode.path("paid_at").asLong(); //결제된 시각. 결제상태가 결제완료(paid)가 아닌 경우 0으로 표시됨. : 1709722069
            LocalDateTime paidAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(paidAtLong), ZoneId.systemDefault());



            String receiptUrl = responseNode.path("receipt_url").asText(); //결제건의 매출전표 URL로 PG사 또는 결제수단에 따라 매출전표가 없을 수 있다. : https:\/\/iniweb.inicis.com\/receipt\/iniReceipt.jsp?noTid=StdpayCARDINIBillTst20240306194748374762

            //startedAtLong : 1709722022L
            //startedAt : 2024-03-06 10:47:02
            Long startedAtLong = responseNode.path("started_at").asLong(); //결제건의 결제요청 시각 UNIX timestamp : 1709722022
            LocalDateTime startedAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(startedAtLong), ZoneId.systemDefault());

            String userAgent = responseNode.path("user_agent").asText(); //구매자가 결제시 사용한 단말기의 UserAgent 문자열 : Mozilla\/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit\/605.1.15 (KHTML, like Gecko) Version\/16.6 Safari\/605.1.15

            //구매자에 대한 정보- buyer 테이블 (확)
            String buyerName = responseNode.path("buyer_name").asText(); //주문자 이름 : \ud3ec\ud2b8\uc6d0 \uae30\uc220\uc9c0\uc6d0\ud300
            String buyerTel = responseNode.path("buyer_tel").asText(); //주문자 전화번호 : 010-1234-5678
            String buyerAddr = responseNode.path("buyer_addr").asText(); //주문자 주소 : \uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uac15\ub0a8\uad6c \uc0bc\uc131\ub3d9
            String buyerPostcode = responseNode.path("buyer_postcode").asText(); //주문자 우편번호 : 123-456
            String buyerEmail = responseNode.path("buyer_email").asText(); //주문자 이메일 : wowns590@naver.com

            //카드 결제
            String applyNum = responseNode.path("apply_num").asText(); //결제건의 신용카드 승인번호 : 31491375
            String cardCode = responseNode.path("card_code").asText(); //결제건의 카드사 코드번호(금융결제원 표준코드번호) - 카드 결제건의 경우 : 366
            String cardName = responseNode.path("card_name").asText(); //결제건의 카드사명 - 카드 결제건의 경우 : \uc2e0\ud55c\uce74\ub4dc
            String cardNumber = responseNode.path("card_number").asText(); //카드번호 : 559410*********4
            Integer cardQuota = responseNode.path("card-quota").asInt(); //할부개월 수 : 0
            Integer cardType = responseNode.path("card-type").asInt(); //0이면 신용카드, 1이면 체크카드. 해당 정보를 제공하지 않는 일부 PG사의 경우 null로 응답됨.(ex : JTNet, 이니시스-빌링) : 1 내가 체크카드로 결제해서 그래

            //실시간 계좌이체
            String bankCode = responseNode.path("bank_code").asText(); //결제건의 은행 표준코드(금융결제원기준) - 실시간계좌이체 결제건의 경우 : null
            String bankName = responseNode.path("bank_name").asText(); //결제건의 은행명 - 실시간계좌이체 결제건의 경우 : null

            //가상계좌(virtual bank)
            String vbankCode = responseNode.path("vbank_code").asText(); //결제건의 가상계좌 은행 표준코드(금융결제원기준) - 가상계좌 결제건의 경우 : null
            Integer vbankDate = responseNode.path("vbank_date").asInt(); //결제건의 가상계좌 입금기한 - 가상계좌 결제 건의 경우 : 0


            String vbankHolder = responseNode.path("vbank_holder").asText(); //결제건의 입금받을 가상계좌 입금주 - 가상계좌 결제건의 경우 : null
            Long vbankIssuedAtLong = responseNode.path("vbank_issued_at").asLong(); //결제건의 가상계좌 생성시각 UNIX timestamp - 가상계좌 결제건의 경우 : 0
            LocalDateTime vbankIssuedAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(vbankIssuedAtLong), ZoneId.systemDefault());



            String vbankNum = responseNode.path("vbank_num").asText(); //결제건의 입금받을 가상계좌 계좌번호 - 가상계좌 결제건의 경우 : null
            String vbankName = responseNode.path("vbank_name").asText(); //결제건의 입금받을 가상계좌 은행명 - 가상계좌 결제건의 경우 : null

            //쩌리
            String customData = responseNode.path("custom_data").asText(); //결제 요청시 가맹점에서 전달한 추가정보(JSON string으로 전달) : null
            String customerUid = responseNode.path("customer_uid").asText(); //결제건에 사용된 빌링키와 매핑되며 가맹점에서 채번하는 구매자의 결제수단 식별 고유번호 : null
            String customerUidUsage = responseNode.path("customer_uid_usage").asText(); //결제처리에 사용된 구매자의 결제수단 식별 고유번호의 사용 구분코드 : null
            String channel = responseNode.path("channel").asText(); //결제환경 구분코드 : pc
            Boolean cashReceiptIssuedBoolean = responseNode.path("cash_receipt_issued").asBoolean(); //결제건의 현금영수증 발급 여부 : false
            String cashReceiptIssued = String.valueOf(cashReceiptIssuedBoolean);
            boolean escrowBoolean = responseNode.path("escrow").asBoolean(); //에스크로결제 여부 : false
            String escrow = String.valueOf(escrowBoolean);

            //PG사
            String pgId = responseNode.path("pg_id").asText(); //PG사 상점아이디 : INIBillTst
            String pgProvider = responseNode.path("pg_provider").asText(); //PG사 구분코드 : html5_inicis
            String embPgProvider = responseNode.path("emb_pg_provider").asText(); //허브형 결제 PG사 구분코드 : null
            String pgTid = responseNode.path("pg_tid").asText(); //PG사 거래번호 : StdpayCARDINIBillTst20240306194748374762

            //결제취소와 관련 정보 (결제 성공했다가 취소되는 경우. 구매자의 요청에 의한 취소, 상품의 결함으로 인한 취소 등.)
            int cancelAmount = responseNode.path("cancel_amount").asInt(); //결제건의 누적 취소금액 : 0
            String cancelReason = responseNode.path("cancel_reason").asText(); //결제취소된 사유. 결제상태가 결제취소(cancelled)가 아닐경우 null 로 표시됨. : null
            String cancelledAt = responseNode.path("cancelled_at").asText(); //결제취소된 시각. 결제상태가 결제취소(cancelled)가 아닐 경우 null 로 표시됨. : 0 ? 이거 왜 null 로 표시 안되어있지? 뭐가 들어올지 모르므로 String 으로 받아오자.
            // "cancel_history":[], -- 결제건의 취소/부분취소 내역 : PaymentCancelAnnotation[]
            // "cancel_receipt_urls":[], -- 더이상 사용하지 않는다고 한다. cancel_history 를 쓰도록 권장하고 있음.

            JsonNode cancelHistoryNode = responseNode.path("cancel_history");
            PaymentCancelAnnotation[] paymentCancelArr = objectMapper.treeToValue(cancelHistoryNode, PaymentCancelAnnotation[].class);
            for (PaymentCancelAnnotation anno : paymentCancelArr) {
                String pgTid2 = anno.getPgTid();
                Integer amount1 = anno.getAmount();
                Integer cancelledAt1 = anno.getCancelledAt();
                String reason = anno.getReason();
                String cancellationId = anno.getCancellationId();
                String receiptUrl1 = anno.getReceiptUrl();
                //cancel_history 테이블에 insert
            }

            //merchantUid 로 orders 테이블에서 행을 찾아서, 그 행의 id 컬럼값을 가져온다.
            Integer orderId = ordersMapper.findOrderIdByMerchantUid(merchantUid);

            paymentsMapper.insertPayment(memberId, orderId, impUid, merchantUid, amount, currency, status, failReason, failedAt, payMethod, name, paidAt, receiptUrl, startedAt, userAgent, buyerName, buyerTel, buyerAddr, buyerPostcode, buyerEmail, applyNum, cardCode, cardName,cardNumber ,cardQuota ,cardType , bankCode, bankName, vbankCode, vbankDate, vbankHolder, vbankIssuedAt, vbankNum, vbankName, customData, customerUid, customerUidUsage, channel, cashReceiptIssued, escrow, pgId, pgProvider, embPgProvider, pgTid, cancelAmount, cancelReason, cancelledAt);


            //paymentsMapper.insertPayment(memberId);

        } catch (JsonProcessingException e) {
            log.error("SinglePaymentQueryService : JSON 파싱 중 오류 발생", e);
            throw new JsonParsingException("InsertPaymentRowService : JSON 파싱 중 오류 발생");
        }




    }


}
