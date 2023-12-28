package firstportfolio.wordcharger.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;

@Data
public class WritingDTO {
    public String writingNum;
    @NotBlank
    public String title;
    public String userId;
    public Boolean secretWritingCheckBox;
    public String writingPassword;
    public Date writingDate;
    public String content;
    public Integer viewNumber;
    public Integer likeNumber;
}
