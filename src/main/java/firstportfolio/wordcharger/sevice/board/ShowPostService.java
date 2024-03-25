package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.CommentDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.*;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ShowPostService {

    private final PostsMapper postsMapper;
    private final PostViewMapper postViewMapper;
    private final CommentsMapper commentsMapper;
    private final MemberMapper memberMapper;
    private final PaginationService paginationService;
    private final PostLikeMapper postLikeMapper;

//    public void showPost(Integer page, Integer postId, Model model, HttpServletRequest request){
//
//        HttpSession session = request.getSession(false);
//        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
//        model.addAttribute("loginedMemberId", loginedMember);
//
//        //post테이블의 아이디컬럼 값으로 게시글을 찾아와야함.
//        PostsDTO findPost = postsMapper.findPostById(postId);
//
//
//        //findPost 에 들어있는 memberId 를 가지고 member테이블에서 userId 받아오기.
//        int memberId = findPost.getMemberId();
//        String userId = memberMapper.findUserIdById(memberId);
//        findPost.setUserId(userId);
//
//        model.addAttribute("findPost", findPost);
//
//        // post_view +1 update : 조회수 1 올리는 작업
//        // 게시글을 클릭한 사용자 != 게시글을 작성한 사용자 인 경우에만 조회수 1올리기.
//        if (memberId != loginedMember.getId()) {
//            postViewMapper.updateByPostId(findPost.getId());
//        }
//
//        //----------자식 댓글(리플) 을 부모 객체에 끼워넣는 작업 시작----------
//        List<CommentDTO> findComments = commentsMapper.findCommentsByPostId(postId);//찾아옴
//        Map<Integer, CommentDTO> commentMap = new HashMap<>();
//
//        for (CommentDTO comment : findComments) {
//            commentMap.put(comment.getId(), comment);
//        }
//
//        for (CommentDTO comment : findComments) {
//            if (comment.getParentCommentId() != null) {
//                CommentDTO parent = commentMap.get(comment.getParentCommentId());
//                if (parent != null) {
//                    parent.getReplies().add(comment);
//                }
//            }
//        }
//
//        List<CommentDTO> parentCommentList = new ArrayList<>();
//
//        for (CommentDTO comment : findComments) {
//            if (comment.getParentCommentId() == null) {
//                parentCommentList.add(comment);
//            }
//        }
//
//
//
//        //--------페이징 시작----------
//        Integer currentPage = page; // 현재 페이지
//
//        //한 페이지당 댓글 개수
//        Integer pageSize = 5;
//        //전체 코멘트 개수
//        Integer totalPosts = parentCommentList.size(); //전체 comment 의 개수
//
//        //그룹당 페이지수
//        Integer pageGroupSize = 5;
//
//        // 게시물 5개 주는 거는 아래 코드로 됬음.
//        Integer startRow = (currentPage - 1) * pageSize;
//
//        List<CommentDTO> listFinded = new ArrayList<>();
//
//        for (int i = startRow; i < Math.min(parentCommentList.size(), startRow + 5); i++) {
//            CommentDTO commentDTO = parentCommentList.get(i);
//            listFinded.add(commentDTO);
//        }
//
//        for (int i = 0; i < listFinded.size(); i++) {
//            CommentDTO commentDTO = listFinded.get(i);
//            Integer id = commentDTO.getMemberId();
//            String findUserId = memberMapper.findUserIdById(id);
//            listFinded.get(i).setUserId(findUserId);
//        }
//
//        for (int i = 0; i < listFinded.size(); i++) {
//            if (listFinded.get(i).getReplies() != null) {
//                for (CommentDTO child : listFinded.get(i).getReplies()) {
//                    Integer id = child.getMemberId();
//                    String findUserId = memberMapper.findUserIdById(id);
//                    child.setUserId(findUserId);
//                }
//            }
//        }
//
//        //댓글 시간처리
//        for (CommentDTO comment : listFinded) {
//            // 현재 시각을 구하기 (시스템의 기본 시간대 사용)
//            LocalDateTime now = LocalDateTime.now();
//
//            // CommentDTO에서 Date 객체를 가져와 LocalDateTime으로 변환
//            Date date = comment.getCreateDate();
//            LocalDateTime localDateTime = date.toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDateTime();
//
//            // 시간 차이 계산
//            long years = ChronoUnit.YEARS.between(localDateTime, now);
//            long months = ChronoUnit.MONTHS.between(localDateTime, now);
//            long days = ChronoUnit.DAYS.between(localDateTime, now);
//            long hours = ChronoUnit.HOURS.between(localDateTime, now);
//            long minutes = ChronoUnit.MINUTES.between(localDateTime, now);
//
//            // "얼마나 전" 문자열 생성
//            String timeAgoStr;
//            if (years > 0) {
//                timeAgoStr = years + "년 전";
//            } else if (months > 0) {
//                timeAgoStr = months + "개월 전";
//            } else if (days > 0) {
//                timeAgoStr = days + "일 전";
//            } else if (hours > 0) {
//                timeAgoStr = hours + "시간 전";
//            } else if (minutes > 0) {
//                timeAgoStr = minutes + "분 전";
//            } else {
//                timeAgoStr = "방금 전";
//            }
//
//            // CommentDTO 객체에 문자열 설정
//            comment.setStringCreateDate(timeAgoStr);
//        }
//
//        //대댓글 시간 처리
//        for (CommentDTO comment : listFinded) {
//            // 현재 시각을 구하기 (시스템의 기본 시간대 사용)
//            LocalDateTime now2 = LocalDateTime.now();
//
//            List<CommentDTO> replies = comment.getReplies();
//
//            for (CommentDTO reply : replies) {
//                Date date = reply.getCreateDate();
//                LocalDateTime localDateTime = date.toInstant()
//                        .atZone(ZoneId.systemDefault())
//                        .toLocalDateTime();
//
//                // 시간 차이 계산
//                long years = ChronoUnit.YEARS.between(localDateTime, now2);
//                long months = ChronoUnit.MONTHS.between(localDateTime, now2);
//                long days = ChronoUnit.DAYS.between(localDateTime, now2);
//                long hours = ChronoUnit.HOURS.between(localDateTime, now2);
//                long minutes = ChronoUnit.MINUTES.between(localDateTime, now2);
//
//                // "얼마나 전" 문자열 생성
//                String timeAgoStr;
//                if (years > 0) {
//                    timeAgoStr = years + "년 전";
//                } else if (months > 0) {
//                    timeAgoStr = months + "개월 전";
//                } else if (days > 0) {
//                    timeAgoStr = days + "일 전";
//                } else if (hours > 0) {
//                    timeAgoStr = hours + "시간 전";
//                } else if (minutes > 0) {
//                    timeAgoStr = minutes + "분 전";
//                } else {
//                    timeAgoStr = "방금 전";
//                }
//                reply.setStringCreateDate(timeAgoStr);
//            }
//
//        }
//
//
//        //게시글의 좋아요 수 model 에 박아주기. Comment 와는 관련 없음.
//        Integer findPostLikeNumber = postLikeMapper.findPostLikeCountByPostId(postId);
//
//        model.addAttribute("likeNumber", findPostLikeNumber);
//
//        paginationService.pagination(currentPage,pageSize,totalPosts, pageGroupSize, listFinded, model);
//    }
        public void showPost(Integer page, Integer postId, Model model, HttpServletRequest request){

            // 1. 이 글을 클릭한 사용자의 회원정보를 세션객체에서 얻어와서 model 에 담아주도록 한다.
                HttpSession session = request.getSession(false);
                MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
                model.addAttribute("loginedMemberId", loginedMember);

            // 2-1. 매개변수로 받은 postId(posts 테이블의 id 컬럼값) 으로 post테이블에서 행을 조회한다.
                PostsDTO findPost = postsMapper.findPostById(postId);

            // 2-2. 현재 findPost 에는 member_id 컬럼값이라고 해서 게시글을 작성한 사용자의 member테이블 id 컬럼값이 들어있다.
            // 그런데, 이 id 컬럼값은 시퀀스 값으로서 아무런 의미가 없는 값((ex: 237 이런 값이다))이기 때문에,
            // member테이블에서 이 id 컬럼값을 가진 행의 user_id 컬럼값(ex : wowns590 이런 값이다)을 가져와서, findPost의 userId 필드에 바인딩한다.
                int memberId = findPost.getMemberId();
                String userId = memberMapper.findUserIdById(memberId);
                findPost.setUserId(userId);
            // 2-3. model 에 담아준다.
                model.addAttribute("findPost", findPost);

            // 3. 조회수와 관련된 작업.
            // 처음 게시글이 작성되어 posts 테이블에 행을 insert 할 때, post_view 테이블에 행을 넣지 않았다.
            // 따라서, 현재 이 게시글을 클릭한 것이 처음인 경우, 이 게시글에 대한 post_view 테이블의 행은 존재하지 않는다.
            // 3-1. 매개변수로 넘어온 posts 테이블의 id 컬럼값을 이용해서 post_view 테이블에서 행을 조회한다.
                Integer viewNumber = postViewMapper.findPostViewCountByPostId(postId); // postId 로 post_view 테이블의 view_number 컬럼값을 조회.

            // 3-2.
            // 만약, null 이라면, 최초로 이 게시글이 조회된 것이므로, post_view 테이블에 행을 삽입하고, 조회수를 1 올린다.
            // 만약, null 이 아니라면, 이미 바로 위의 과정을 거친것이기 때문에, 조회수만 1 올려주면 된다.
                if (viewNumber == null) {
                    // 최초 조회.
                    Integer sequence = postViewMapper.selectNextSequenceValue();
                    postViewMapper.initPostView(sequence, postId);
                }

            // 3-3. 새로 post_view 테이블에 행을 삽입했든, 원래 있었든, 조회수 1을 올려줄 것이다. 근데, 조건이 있다.
            // "게시글을 클릭한 사용자 != 게시글을 작성한 사용자" 이어야 한다.
                if (memberId != loginedMember.getId()) {
                    postViewMapper.updateByPostId(postId);
                }

            // 4. 좋아요수와 관련된 작업
            // 좋아요 수도 조회수와 마찬가지로 처음 게시글이 작성되어 posts 테이블에 행을 insert 할 때, post_like 테이블에 행을 삽입하도록 하지 않았다.
            // 따라서, 아직 누구도 추천(좋아요)을 누르지 않은 경우라면, post_like 테이블에 행이 존재하지 않음.
            // 4-1. 매개변수로 넘어온 posts 테이블의 id 컬럼값을 이용해서 post_like 테이블에서 행을 조회한다.
                Integer findPostLikeNumber = postLikeMapper.findPostLikeCountByPostId(postId);

            // 4-2.
            // 만약, null 이라면 model 에 likeNumber 라는 키에 0을 넣어준다.
            // 만약, null 이 아니라면, model 에 likeNumber 라는 키에 조회된 post_like 테이블의 행에서 like_number 값을 넣어준다.
            if (findPostLikeNumber == null) {
                model.addAttribute("likeNumber", 0);
            } else{
                model.addAttribute("likeNumber", findPostLikeNumber);
            }
        }
}
