package firstportfolio.wordcharger.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberDTO {
    public Integer id;
    public String userId;
    public String password;
    public String userName;
}


