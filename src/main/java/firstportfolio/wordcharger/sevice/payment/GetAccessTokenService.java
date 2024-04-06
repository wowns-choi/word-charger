package firstportfolio.wordcharger.sevice.payment;

import firstportfolio.wordcharger.DTO.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
// @Transactional 쓰지 않는다. 왜? db접근하는 코드 없는 서비스계층임.
@Slf4j
@RequiredArgsConstructor
public class GetAccessTokenService {
    private final RestTemplate restTemplate;

    /*restTemplate 을 통해서 포트원서버에 HTTP요청 하기.
    * */
    public TokenResponseDTO getAccessToken(){
        //누구에게 HTTP요청을 보낼건지를 정함.
        String url = "https://api.iamport.kr/users/getToken";

        //HTTP요청 본문이 application/x-www-form-urlencoded 형식의 데이터임을 나타냄.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("imp_key", "5737118685604387");
        map.add("imp_secret", "iny0Od4aEZ74VzpTeqrwXgidvVnrITMppgWVrKbhACXOvwqHRw2eS182qmEtORqP0xnD6DN3hXewCQAd");

        HttpEntity<MultiValueMap<String, String>> entity2 = new HttpEntity<>(map, headers);

        TokenResponseDTO responseJson = restTemplate.postForObject(url, entity2, TokenResponseDTO.class);


        return responseJson;
    }


}
