package firstportfolio.wordcharger.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class PostGenerateDTO {
    //posts 테이블
    private Integer id;
    private Integer memberId;
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    private Boolean is_private; // 이 boolean 타입 때문에 새로운 dto 씀.
    private String is_private_string;
    private Date writing_date;
    @NotBlank(message = "본문을 입력해주세요")
    private String content;
    private String deletePostFl;


    //post_view 테이블
    private Integer viewNumber;

    //post_password 테이블
    private String postPassword;

    //member 테이블
    private String userId;


}
