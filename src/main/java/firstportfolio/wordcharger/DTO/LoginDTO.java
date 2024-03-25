package firstportfolio.wordcharger.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "아이디를 입력해주세요")
    private String id;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
