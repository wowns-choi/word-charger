package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.repository.PostIdMemberIdForNotDuplicateLikeMapper;
import firstportfolio.wordcharger.repository.PostLikeMapper;
import firstportfolio.wordcharger.sevice.board.ShowPostService;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ShowPostController {
    private final ShowPostService showPostService;
    private final PostLikeMapper postLikeMapper;
    private final PostIdMemberIdForNotDuplicateLikeMapper postIdMemberIdForNotDuplicateLikeMapper;
    @GetMapping("show-writing")
    public String showWritingControllerMethod(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam Integer postId,
                                              Model model,
                                              HttpServletRequest request) {

        showPostService.showPost(page, postId, model, request);
        return "/contact/showPost";
    }

    @PostMapping("update-and-find-like-num")
    @ResponseBody // 이 어노테이션 추가
    public Map<String, Integer> updateAndFindLikeNum(@RequestParam String postId, HttpServletRequest request) {
        int pi = Integer.parseInt(postId);

        Integer id = FindLoginedMemberIdUtil.findLoginedMember(request);

        Integer rowCount = postIdMemberIdForNotDuplicateLikeMapper.findRowCountByPostIdAndMemberId(pi, id);
        Map<String, Integer> response = new HashMap<>();

        if(rowCount == 0){
            //조회된 행이 없는 경우 == 좋아요를 처음 누른 경우

            postLikeMapper.updateByPostId(pi);
            Integer updatedNumber = postLikeMapper.findPostLikeCountByPostId(pi);

            // 여기서, post_id_member_id_for_not_duplicate_like 테이블에 행을 넣어줘야 함.
            postIdMemberIdForNotDuplicateLikeMapper.insertRow(pi, id);

            response.put("updatedLikeNumber", updatedNumber);
            return response; // JSON 형식의 맵 반환

        } else{
            // 조회된 행이 1 이상인 경우
            response.put("updatedLikeNumber", -1); //jsp 파일에서 1이면 alert 창으로 "좋아요 는 1번만 누르실 수 있습니다" 라는 문구 띄워줘.
            return response;

        }


    }



}
