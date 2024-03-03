package firstportfolio.wordcharger.sevice.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordUpdateService {
    private final MemberMapper memberMapper;
    public String passwordUpdate(HttpServletRequest request, MemberAllDataFindDTO memberAllDataFindDTO){
        try{
            HttpSession session = request.getSession(false);
            MemberJoinDTO memberJoinDTO = (MemberJoinDTO) session.getAttribute("loginedMember");
            memberMapper.updatePassword(memberJoinDTO.getId(), memberAllDataFindDTO.getPassword());
            return "1";
        }catch(Exception e){
            return "0";
        }
    }
}
