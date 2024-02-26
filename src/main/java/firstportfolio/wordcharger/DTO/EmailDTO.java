package firstportfolio.wordcharger.DTO;

import lombok.Data;

@Data
public class EmailDTO {


    private Integer id;
    private Integer memberId;
    private String email;
    private String domain;
}
