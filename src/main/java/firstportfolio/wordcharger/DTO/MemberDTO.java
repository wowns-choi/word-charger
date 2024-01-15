package firstportfolio.wordcharger.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberDTO {
    public String id;

    public String password;

    public String userName;

    public String zipCode;

    public String phoneNumberStart;
    public String phoneNumberMiddle;
    public String phoneNumberEnd;


    public String streetAddress;
    public String address;
    public String detailAddress;
    public String referenceItem;
    public String myCheckbox1;
    public String myCheckbox2;
    public String myCheckbox3;

}


