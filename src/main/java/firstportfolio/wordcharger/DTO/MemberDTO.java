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
    @NotBlank
    public String zipCode;
    @NotBlank
    public String phoneNumberStart;
    @NotBlank
    public String phoneNumberMiddle;
    @NotBlank
    public String phoneNumberEnd;
    @NotBlank
    public String streetAddress;
    @NotBlank
    public String address;
    @NotBlank
    public String detailAddress;
    @NotBlank
    public String referenceItem;
    public String myCheckbox1;
    public String myCheckbox2;
    public String myCheckbox3;

}


