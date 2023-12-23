package firstportfolio.wordcharger.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberDTO {
    @NotBlank
    public String id;
    @NotBlank
    public String password;
    @NotBlank
    public String userName;
}


