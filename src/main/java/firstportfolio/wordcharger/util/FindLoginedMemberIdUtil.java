package firstportfolio.wordcharger.util;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class FindLoginedMemberIdUtil {

    public static Integer findLoginedMember(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        Integer id = loginedMember.getId();
        return id;
    }
}
