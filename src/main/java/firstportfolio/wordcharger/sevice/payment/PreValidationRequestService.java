package firstportfolio.wordcharger.sevice.payment;

import firstportfolio.wordcharger.DTO.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
//@Transactional 안쓴다. 왜? db 접근하는 코드 없음.
@Slf4j
@RequiredArgsConstructor
public class PreValidationRequestService {

    private final RestTemplate restTemplate;

    public void preValidationRequest(TokenResponseDTO tokenDTO, Integer amount, String merchantUid){
        String url = "https://api.iamport.kr/payments/prepare";

        HttpHeaders headers = new HttpHeaders(); //보낼 HTTP요청메세지의 헤더를 설정할 때 쓰일 객체.
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", tokenDTO.getResponse().getAccess_token());

        Map<String, Object> map = new HashMap<>();
        map.put("merchant_uid", merchantUid);
        map.put("amount", amount);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // restTemplate.postForObject(url, entity, String.class); 이 코드로 인해, 포트원 서버에 HTTP요청 감.
        // 갔다와서 받은 리턴값을 response 라는 String 타입 변수에 담음.
        String response = restTemplate.postForObject(url, entity, String.class);
        log.info("포트원이 뭘 줬을까? ===== {}", response);
    }

}
