package firstportfolio.wordcharger.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PWFindTokenDTO {

    private Integer id;
    private Integer memberId;
    private String token;
    private LocalDateTime expiredAt;
}
