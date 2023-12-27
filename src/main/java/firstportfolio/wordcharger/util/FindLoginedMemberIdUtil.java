package firstportfolio.wordcharger.util;

import firstportfolio.wordcharger.DTO.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class FindLoginedMemberIdUtil {

    public static String findLoginedMember(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        MemberDTO loginedMember = (MemberDTO) session.getAttribute("loginedMember");
        String id = loginedMember.getId();
        return id;
    }
}
