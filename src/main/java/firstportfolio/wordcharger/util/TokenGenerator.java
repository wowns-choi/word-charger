package firstportfolio.wordcharger.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
@Component
public class TokenGenerator {
    public String generateToken() {
        SecureRandom random = new SecureRandom(); // 랜덤 숫자 생성 기계
        byte[] bytes = new byte[24]; // 24바이트 = 192비트 크기의 바이트 배열 생성
        random.nextBytes(bytes); // 랜덤숫자생성기계인 SecureRandom 타입 객체에 대고
                                // nextBytes 메서드를 호출하면서, 매개변수 bytes 배열을 넘겨주면
                                // SecureRandom 타입객체가 바이트배열을 랜덤한 숫자로 가득 채움.

        // 아래 코드는 바이트배열을 String(문자열)으로 인코딩(다른 데이터 형식으로 변환) 하는 과정
        // Base64 : 바이너리 데이터(이진수)를 ASC2 문자열로 변환하는 인코딩 방법
        // getUrlEncoder() : getUrlEncoder() 라고 하면, 인코더(인코딩하는 기계) 가 반환되는데,
        //                   이때, urlEncoder 라는 건, URL에서 사용하기 안전하도록 인코딩해주는 인코더.
        // withoutPadding() : 패딩 없이.
        // encodeToString(바이트배열) : 바이트배열을 String으로 인코딩하라는 뜻.
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);


        return token;
    }
}
