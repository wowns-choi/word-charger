package firstportfolio.wordcharger.sevice.join;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class JoinService {
    public MemberJoinDTO onAndNullChange (MemberJoinDTO memberJoinDTO){

        if(memberJoinDTO.getMyCheckbox1().equals("on")){
            memberJoinDTO.setMyCheckbox1("1");
        } else{
            memberJoinDTO.setMyCheckbox1("0");
        }

        if(memberJoinDTO.getMyCheckbox2().equals("on")){
            memberJoinDTO.setMyCheckbox2("1");
        } else{
            memberJoinDTO.setMyCheckbox2("0");
        }


        if(memberJoinDTO.getMyCheckbox3().equals("on")){
            memberJoinDTO.setMyCheckbox3("1");
        } else{
            memberJoinDTO.setMyCheckbox3("0");
        }
        return memberJoinDTO;
    }

}
