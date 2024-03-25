package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.PostPasswordDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.PostIdMemberIdForNotDuplicateLikeMapper;
import firstportfolio.wordcharger.repository.PostLikeMapper;
import firstportfolio.wordcharger.repository.PostPasswordMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.ShowPostService;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ShowPostController {
    private final ShowPostService showPostService;
    private final PostLikeMapper postLikeMapper;
    private final PostIdMemberIdForNotDuplicateLikeMapper postIdMemberIdForNotDuplicateLikeMapper;
    private final PostPasswordMapper postPasswordMapper;
    private final PostsMapper postsMapper;

    @GetMapping("/is-this-you")
    @ResponseBody
    public String isThisYou(@RequestParam String postId, HttpServletRequest request){
        //게시글을 클릭한 사람이 본인인지 확인.
        PostsDTO post = postsMapper.findPostById(Integer.parseInt(postId));
        int memberId = post.getMemberId();
        log.info("memberId=={}", memberId);

        int loginedMember = FindLoginedMemberIdUtil.findLoginedMember(request);
        log.info("logiendMember=={}", loginedMember);


        if (memberId == loginedMember) {
            return "yes";
        } else{
            return "no";
        }
    }

    @GetMapping("/is-this-secret")
    @ResponseBody
    public Map<String,String> isThisSecret(@RequestParam String postId){
        //비밀글인지 여부를 조사.
        PostPasswordDTO row = postPasswordMapper.findRow(Integer.parseInt(postId));
        Map<String, String> map = new ConcurrentHashMap<>();

        // 만약 처음에 비밀글로 생성하지 않았다면, post_password 테이블에 행이 아예 존재하지 않을 수 있거든.
        // 그래서, row 가 null 이 아니라면 => 비밀글로 생성했다는 뜻.
        // row 가 null 이면 => 비밀글로 생성하지 않았다.
        if (row == null) {
            // 공개글이다.
            map.put("isThisSecret", "no");
        } else{
            // 비밀글이다.
            String postPassword = row.getPostPassword();
            map.put("isThisSecret", "yes");
            map.put("writingPassword", postPassword);
        }

        return map; //비밀글 이다.
    }





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
    public Map<String, Integer> updateAndFindLikeNum(@RequestBody Map<String, String> map, HttpServletRequest request) {
        String postId = map.get("postId");
        int pi = Integer.parseInt(postId); // 현재 pi 에는 추천을 누른 해당 게시글의 posts 테이블의 id 컬럼값이 들어있다.

        Integer id = FindLoginedMemberIdUtil.findLoginedMember(request); // 현재 id 에는 추천을 누른 사용자의 member 테이블 id 컬럼값이 들어있다.

        // 지금 추천을 누른 사용자가 해당 게시글에 추천을 누른적이 있는지를 확인하기 위해서,
        // POST_ID_MEMBER_ID_FOR_NOT_DUPLICATE_LIKE 라는 테이블에서 pi 와 id 로 조회를 하고 있다.
        Integer rowCount = postIdMemberIdForNotDuplicateLikeMapper.findRowCountByPostIdAndMemberId(pi, id);
        Map<String, Integer> response = new HashMap<>();

        if(rowCount == 0){
            // POST_ID_MEMBER_ID_FOR_NOT_DUPLICATE_LIKE 테이블에서 조회된 행이 없다
            // == 지금 추천 누른 사람이 이 글에 좋아요를 처음 누른 경우.

            // 뭘 해줄거냐면, POST_LIKE 테이블에 행을 삽입해줘야 함.
            // 게시글을 만드는 과정에서도, 그리고 사용자가 게시판 홈에서 제목을 클릭해서 게시글의 상세조회를 보여주는 과정에서도,
            // POST_LIKE 테이블에 행을 넣은 적이 없음.
            // 1번. pi 로 POST_LIKE 테이블에서 like_number 컬럼값 을 조회한다.
            // 2번. 만약, 조회된 값이 null이라면(= 조회된 행이 없다면), 해당 글에 대해 최초로 추천(좋아요)가 눌린 상황이다. 따라서, POST_LIKE 테이블에 행을 삽입해주고, like_number 컬럼값을 + 1 해줘야함.
            // 3번. 만약, 조회된 값이 null이 아니라면(= 조회된 행이 있다면), 이미 위의 과정을 거쳤다고 판단하고, like_number 컬럼값만 + 1 해주면 됨.
            Integer likeNumber = postLikeMapper.findPostLikeCountByPostId(pi);

            if (likeNumber == null) {
                //조회된 컬럼값이 비어있다면, 해당 글에 대해 최초로 추천(좋아요)이 눌린 상황.
                Integer sequence = postLikeMapper.selectNextSequenceValue(); //시퀀스 미리 가져오고,
                postLikeMapper.initPostLike(pi); // 행을 삽입.
            }
            // 2번의 상황이든, 3번의 상황이든, 공통적으로 like_num 을 +1 해줘야함.
            postLikeMapper.updateByPostId(pi);
            // 2번의 상황이든, 3번의 상황이든, 공통적으로 post_id_member_id_for_not_duplicate_like 테이블에 행을 넣어줘야 함.
            postIdMemberIdForNotDuplicateLikeMapper.insertRow(pi, id);

            // 다시 조회. 왜? likeNumber 에 들어있는 값은, like_number 컬럼을 +1 해주기 이전의 값이기 때문.
            Integer updatedNumber = postLikeMapper.findPostLikeCountByPostId(pi);

            response.put("updatedLikeNumber", updatedNumber);
            return response; // JSON 형식의 맵 반환
        } else{
            // 조회된 행이 1 이상인 경우
            // == 지금 추천을 누른 사람이 이 글에 추천(좋아요)을 이전에 누른 경우.
            // == 이미 POST_LIKE 테이블에 행이 있다는 것도 의미하는 것이다.
            response.put("updatedLikeNumber", -1); //jsp 파일에서 1이면 alert 창으로 "좋아요 는 1번만 누르실 수 있습니다" 라는 문구 띄워줘.
            return response;
        }

    }



}
