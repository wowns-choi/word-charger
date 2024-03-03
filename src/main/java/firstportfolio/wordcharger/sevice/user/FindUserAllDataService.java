package firstportfolio.wordcharger.sevice.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FindUserAllDataService {
    private final MemberMapper memberMapper;
    public void findAllUserInfo(Model model, String id){
        MemberJoinDTO memberJoinDTO = new MemberJoinDTO();
        MemberAllDataFindDTO memberAllDataFindDTO = memberMapper.findMemberTotalData(id);

        log.info("id 로 회원에 대한 정보 중 member, phone, address, email 테이블에 있는 정보 가져옴 ={}", memberAllDataFindDTO);
        model.addAttribute("memberAllData", memberAllDataFindDTO);
    }
}
