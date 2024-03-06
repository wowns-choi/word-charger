package firstportfolio.wordcharger.DTO;

import lombok.Data;

// 이 DTO 로 받을 json 예시 :
//{"code":0,
// "message":null,
// "response":{"access_token":"14c6d0f2a5a84c9b4745b9cfbf9b59ce38582c3c","now":1709719486,"expired_at":1709721286}}
@Data
public class TokenResponseDTO {

    private int code;
    private String message;
    private TokenDetails response;

    @Data
    public static class TokenDetails{
        private String access_token;
        private long now;
        private long expired_at;
    }

}
