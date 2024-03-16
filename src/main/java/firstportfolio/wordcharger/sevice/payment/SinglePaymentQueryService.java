package firstportfolio.wordcharger.sevice.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import firstportfolio.wordcharger.DTO.TokenResponseDTO;
import firstportfolio.wordcharger.exception.payment.JsonParsingException;
import firstportfolio.wordcharger.exception.payment.PaymentVerificationException;
import firstportfolio.wordcharger.repository.OrdersMapper;
import firstportfolio.wordcharger.repository.PaymentsMapper;
import firstportfolio.wordcharger.repository.ProductsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
// @Transactional 안쓴다. 왜? db 접근하는 코드 없음.
@Slf4j
@RequiredArgsConstructor
public class SinglePaymentQueryService {
    private final RestTemplate restTemplate;
    /*
    * 결제 단건 조회
    * */
    public ResponseEntity<String> singlePaymentQuery(String impUid, TokenResponseDTO tokenDTO) {

        String url = "https://api.iamport.kr/payments/" + impUid;

        //헤더 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", tokenDTO.getResponse().getAccess_token());
        //바디에 담을 거
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // RestTemplate 사용해서 GET 요청 전송.
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response;
    }




}
