package firstportfolio.wordcharger.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class UserWordDTO {

    public Integer memberId;
    public Integer wordId;
    public Integer daysRemembered;
    public Date nextReviewDate;

}
