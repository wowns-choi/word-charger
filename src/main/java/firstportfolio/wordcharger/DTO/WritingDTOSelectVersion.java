package firstportfolio.wordcharger.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;

@Data
public class WritingDTOSelectVersion {
    public String writingNum;
    @NotBlank
    public String title;
    public String userId;
    public Integer isPrivate;
    public String writingPassword;
    public Date writingDate;
    public String content;
    public Integer viewNumber;
    public Integer likeNumber;
}
