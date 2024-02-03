package firstportfolio.wordcharger.util;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class FindLoginedMemberIdUtil {

    public static String findLoginedMember(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        String id = loginedMember.getUserId();
        return id;
    }
}
