package firstportfolio.wordcharger.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {

    private String imp_uid;
    private String merchant_uid;
}
