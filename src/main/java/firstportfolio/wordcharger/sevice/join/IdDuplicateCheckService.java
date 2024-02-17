package firstportfolio.wordcharger.sevice.join;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class IdDuplicateCheckService {

    private final MemberMapper memberMapper;
    public Map<String, Boolean> idCheck(Map<String, String> request) {

        String userId = request.get("userId");
        MemberJoinDTO memberById = memberMapper.findMemberById(userId);
        if (memberById == null) {
            return Collections.singletonMap("isAvailable", true);
        }else{
            return Collections.singletonMap("isAvailable", false);
        }
    }
}
