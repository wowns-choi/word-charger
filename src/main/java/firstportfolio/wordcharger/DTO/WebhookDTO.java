package firstportfolio.wordcharger.DTO;

import lombok.Data;

@Data
public class WebhookDTO {

    private String imp_uid; //포트원 쪽 고유번호 (결제번호 라고 표현하더라)
    private String merchant_uid; //내가 랜덤으로 생성한 고유번호 (주문번호라고 표현하더라)
    private String status; // 결제 결과
    private String cancellation_id; //취소내역 아이디

}
