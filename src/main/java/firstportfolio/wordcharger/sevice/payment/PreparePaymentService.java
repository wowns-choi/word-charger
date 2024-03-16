package firstportfolio.wordcharger.sevice.payment;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.TokenResponseDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.ProductsMapper;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional // @Transactional 왜 씀?
// 포트원으로부터 access 토큰을 얻어오거나, 사전검증요청을 하다가 실패할 경우, 재고를 감소시키는
// productsMapper.stockCountPlusByProductId(productId, -1); 이 부분이 롤백되도록 하기 위함.
public class PreparePaymentService {
    private final CheckStockService checkStockService;
    private final ProductsMapper productsMapper;
    private final GetAccessTokenService accessToken;
    private final PreValidationRequestService preValidationRequestService;
    private final MemberMapper memberMapper;

    public Map<String, Object> preparePayment(String productId, @RequestParam String merchantUid , HttpServletRequest request){
        Map<String, Object> returnMap = new ConcurrentHashMap<>();

        // 1. 본격적인 사전검증에 들어가기 전에 재고량을 파악하도록 한다.
            // 1-1) 재고량을 파악
            Integer returnValue = checkStockService.checkStock(productId);

            // 1-2) 재고량이 없는 경우
            if (returnValue == 0) {
                returnMap.put("outOfStock", true);
            }

            // 1-3). 재고량이 있는 경우, 미리 재고량을 하나 감소시킨다. 만약, 결제가 중간에 어떠한 사유로 취소되는 경우, 거기서 다시 재고량을 늘리도록 한다.
            // 왜? 사전검증 다음 단계는 이니시스 결제창 띄워지고 결제가 진행되게 된다.
            // 결제는 됬는데, 재고가 없는 경우가 발생할 수 있기 때문에, 손해를 보더라도 이게 맞지 않나? 라는 생각에 이렇게 설계했다.
            productsMapper.stockCountPlusByProductId(productId, -1); // -> 재고량 감소시키기.

        // 2. 사전검증 시작
            // 2-1) 사전검증 요청 전, access_token 을 받아와야 함. 왜? 포트원과 통신하기 위해서는 포트원에게 보내는 HTTP 요청 헤더에 accessToken 을 담아줘야 한다.
            TokenResponseDTO tokenDTO = accessToken.getAccessToken();
            // 2-2) 포트원에게 사전검증 요청
                //a) product 의 가격(amount)을 db에서 가져온다.
                Integer amount = productsMapper.findAmount(productId);

                //b) merchantId(주문번호) 는 파라미터로 받음.

                //c) 사전검증 요청
                preValidationRequestService.preValidationRequest(tokenDTO, amount, merchantUid);

        // 3. jsp 에 넘겨줄 데이터 담기
            // 3-1) 구매자 정보 (이메일, 이름, 전화번호, 주소, 우편번호)
            Integer memberId = FindLoginedMemberIdUtil.findLoginedMember(request);
            MemberAllDataFindDTO memberTotalData = memberMapper.findMemberTotalData(String.valueOf(memberId));
            returnMap.put("memberTotalData", memberTotalData);

            // 3-2) merchantUid : 각 주문에 부여된 식별키
            returnMap.put("merchantUid", merchantUid);

            // 3-3) 상품의 이름과 가격
            String productName = productsMapper.findProductNameByProductId(productId);
            returnMap.put("productName", productName);
            returnMap.put("amount", amount);

            return returnMap;
    }
}
