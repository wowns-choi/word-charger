package firstportfolio.wordcharger.sevice.join;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
@Slf4j
//@Transactional 안쓴다. select 쿼리밖에 없음.
public class IdDuplicateCheckService {

    private final MemberMapper memberMapper;
    public Map<String, Boolean> idCheck(Map<String, String> request) {

        String userId = request.get("userId");
        MemberJoinDTO memberById = memberMapper.findMemberById(userId);
        Map<String, Boolean> map = new ConcurrentHashMap<>();
        if (memberById == null) {
            map.put("isAvailable", true);
        }else{
            map.put("isAvailable", false);
        }
        return map;
    }
}
