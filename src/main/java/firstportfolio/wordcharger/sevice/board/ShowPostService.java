package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.CommentDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.*;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import firstportfolio.wordcharger.sevice.board.common.WritingDateChangeService;
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

    public void showPost(Integer page, Integer postId, Model model, HttpServletRequest request){

        //post테이블의 아이디컬럼 값으로 게시글을 찾아와야함.
        PostsDTO findPost = postsMapper.findPostById(postId);

        //findPost 에 들어있는 memberId 를 가지고 member테이블에서 userId 받아오기.
        String userId = memberMapper.findUserIdById(findPost.getMemberId());
        findPost.setUserId(userId);

        model.addAttribute("findPost", findPost);

        //post_view +1 update : 조회수 1 올리는 작업
        postViewMapper.updateByPostId(findPost.getId());


        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        model.addAttribute("loginedMemberId", loginedMember);

        //----------자식 댓글(리플) 을 부모 객체에 끼워넣는 작업 시작----------
        List<CommentDTO> findComments = commentsMapper.findCommentsByPostId(postId);//찾아옴
        Map<Integer, CommentDTO> commentMap = new HashMap<>();

        for (CommentDTO comment : findComments) {
            commentMap.put(comment.getId(), comment);
        }

        for (CommentDTO comment : findComments) {
            if (comment.getParentCommentId() != null) {
                CommentDTO parent = commentMap.get(comment.getParentCommentId());
                if (parent != null) {
                    parent.getReplies().add(comment);
                }
            }
        }

        List<CommentDTO> parentCommentList = new ArrayList<>();

        for (CommentDTO comment : findComments) {
            if (comment.getParentCommentId() == null) {
                parentCommentList.add(comment);
            }
        }



        //--------페이징 시작----------
        Integer currentPage = page; // 현재 페이지

        //한 페이지당 댓글 개수
        Integer pageSize = 5;
        //전체 코멘트 개수
        Integer totalPosts = parentCommentList.size(); //전체 comment 의 개수

        //그룹당 페이지수
        Integer pageGroupSize = 5;

        // 게시물 5개 주는 거는 아래 코드로 됬음.
        Integer startRow = (currentPage - 1) * pageSize;

        List<CommentDTO> listFinded = new ArrayList<>();

        for (int i = startRow; i < Math.min(parentCommentList.size(), startRow + 5); i++) {
            CommentDTO commentDTO = parentCommentList.get(i);
            listFinded.add(commentDTO);
        }

        for (int i = 0; i < listFinded.size(); i++) {
            CommentDTO commentDTO = listFinded.get(i);
            Integer id = commentDTO.getMemberId();
            String findUserId = memberMapper.findUserIdById(id);
            listFinded.get(i).setUserId(findUserId);
        }

        for (int i = 0; i < listFinded.size(); i++) {
            if (listFinded.get(i).getReplies() != null) {
                for (CommentDTO child : listFinded.get(i).getReplies()) {
                    Integer id = child.getMemberId();
                    String findUserId = memberMapper.findUserIdById(id);
                    child.setUserId(findUserId);
                }
            }
        }

        //댓글 시간처리
        for (CommentDTO comment : listFinded) {
            // 현재 시각을 구하기 (시스템의 기본 시간대 사용)
            LocalDateTime now = LocalDateTime.now();

            // CommentDTO에서 Date 객체를 가져와 LocalDateTime으로 변환
            Date date = comment.getCreateDate();
            LocalDateTime localDateTime = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            // 시간 차이 계산
            long years = ChronoUnit.YEARS.between(localDateTime, now);
            long months = ChronoUnit.MONTHS.between(localDateTime, now);
            long days = ChronoUnit.DAYS.between(localDateTime, now);
            long hours = ChronoUnit.HOURS.between(localDateTime, now);
            long minutes = ChronoUnit.MINUTES.between(localDateTime, now);

            // "얼마나 전" 문자열 생성
            String timeAgoStr;
            if (years > 0) {
                timeAgoStr = years + "년 전";
            } else if (months > 0) {
                timeAgoStr = months + "개월 전";
            } else if (days > 0) {
                timeAgoStr = days + "일 전";
            } else if (hours > 0) {
                timeAgoStr = hours + "시간 전";
            } else if (minutes > 0) {
                timeAgoStr = minutes + "분 전";
            } else {
                timeAgoStr = "방금 전";
            }

            // CommentDTO 객체에 문자열 설정
            comment.setStringCreateDate(timeAgoStr);
        }

        //대댓글 시간 처리
        for (CommentDTO comment : listFinded) {
            // 현재 시각을 구하기 (시스템의 기본 시간대 사용)
            LocalDateTime now2 = LocalDateTime.now();

            List<CommentDTO> replies = comment.getReplies();

            for (CommentDTO reply : replies) {
                Date date = reply.getCreateDate();
                LocalDateTime localDateTime = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                // 시간 차이 계산
                long years = ChronoUnit.YEARS.between(localDateTime, now2);
                long months = ChronoUnit.MONTHS.between(localDateTime, now2);
                long days = ChronoUnit.DAYS.between(localDateTime, now2);
                long hours = ChronoUnit.HOURS.between(localDateTime, now2);
                long minutes = ChronoUnit.MINUTES.between(localDateTime, now2);

                // "얼마나 전" 문자열 생성
                String timeAgoStr;
                if (years > 0) {
                    timeAgoStr = years + "년 전";
                } else if (months > 0) {
                    timeAgoStr = months + "개월 전";
                } else if (days > 0) {
                    timeAgoStr = days + "일 전";
                } else if (hours > 0) {
                    timeAgoStr = hours + "시간 전";
                } else if (minutes > 0) {
                    timeAgoStr = minutes + "분 전";
                } else {
                    timeAgoStr = "방금 전";
                }
                reply.setStringCreateDate(timeAgoStr);
            }

        }


        //게시글의 좋아요 수 model 에 박아주기. Comment 와는 관련 없음.
        Integer findPostLikeNumber = postLikeMapper.findPostLikeCountByPostId(postId);

        model.addAttribute("likeNumber", findPostLikeNumber);

        paginationService.pagination(currentPage,pageSize,totalPosts, pageGroupSize, listFinded, model);
    }
}
