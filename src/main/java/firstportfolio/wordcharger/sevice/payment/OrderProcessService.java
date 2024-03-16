package firstportfolio.wordcharger.sevice.payment;

import firstportfolio.wordcharger.DTO.OrderFormDTO;
import firstportfolio.wordcharger.repository.OrdersMapper;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional 은 쓰지 않는다. 왜? '하나의' insert 문만 있는 서비스계층이기 때문이다.
public class OrderProcessService {
    private final OrdersMapper ordersMapper;
    public Map<String, String> orderProcess(OrderFormDTO orderFormDTO, HttpServletRequest request){
        //orders 테이블에 행을 삽입하기 위한 재료 모음.
        // 1. memberId 가져오기
        Integer memberId = FindLoginedMemberIdUtil.findLoginedMember(request);

        // 2. productId
        String productId = orderFormDTO.getProductId();

        // 3. merchantUid(주문번호, 이 주문에 부여된 고유한 키로서, 다른 주문과의 구별을 하게 해주는 식별키) 생성
        long nano = System.currentTimeMillis();
        String merchantUid = "pid-" + nano;

        // 4. amount : 해당 주문을 하는 사용자가 지불해야할 총 가격
        Integer amount = Integer.parseInt(orderFormDTO.getTotalAmount());

        // 5. phoneNum : member 테이블에서 해당 사용자의 핸드폰번호를 가져오지 않았다. 왜? 해당 주문을 한 사용자가 어떤 주문에서는 연락받을 핸드폰번호를 바꾸고 싶어하는 경우도 있을 수 있기 때문에.
        String phoneNumStart = orderFormDTO.getPhoneNumStart();
        String phoneNumMiddle = orderFormDTO.getPhoneNumMiddle();
        String phoneNumEnd = orderFormDTO.getPhoneNumEnd();

        // 6. address : member 테이블에서 해당 사용자의 주소를 가져오지 않았다. 왜? 해당 주문을 한 사용자가 어떤 주문은 다른 곳에서 상품을 받고 싶어할 수 있기 때문이다.
        String zipCode = orderFormDTO.getZipCode();
        String streetAddress = orderFormDTO.getStreetAddress();
        String address = orderFormDTO.getAddress();
        String detailAddress = orderFormDTO.getDetailAddress();
        String referenceItem = orderFormDTO.getReferenceItem();

        //orders 테이블에 행을 삽입해야해.
        // Pending : 주문이 접수되었지만, 아직 재고확인 & 결제확인 이 되지 않은 상태. 라고 통용되는 단어라고 함.
        // 참고로, 현재, 재고확인은 showOrderSheet 라는 메서드에서 하고 있지만, preparePayment 메서드 에서 사전검증에 들어가기 전에 한번 더 할 것이다.
        ordersMapper.insertOrder(memberId,
                productId,
                merchantUid,
                amount,
                "Pending",
                "내용없음",
                phoneNumStart,
                phoneNumMiddle,
                phoneNumEnd,
                zipCode,
                streetAddress,
                address,
                detailAddress,
                referenceItem);

        Map<String, String> returnMap = new ConcurrentHashMap<>();
        returnMap.put("result", "success");
        // orderSheet.jsp 에 생성된 merchantUid 를 넘겨주는 이유는, orderSheet.jsp 에서 다시 preparePayment메서드에 넘겨주기 위함이다.
        returnMap.put("merchantUid", merchantUid);

        return returnMap;
    }
}
