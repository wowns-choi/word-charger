package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.repository.PostLikeMapper;
import firstportfolio.wordcharger.repository.PostPasswordMapper;
import firstportfolio.wordcharger.repository.PostViewMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class InsertPostService {
    private final PostsMapper postsMapper;
    private final PostPasswordMapper postPasswordMapper;
    //private final PostViewMapper postViewMapper;
    //private final PostLikeMapper postLikeMapper;
    public void insertPost(Boolean isPrivate, String postPassword, String title, String content,Integer memberId ){
        // isPrivate 에는 현재 true 또는 false 가 들어와있다.
        // true 인 경우 1 이, false 인 경우 0 이 isPrivate2 라는 변수에 들어가도로고 하였다.
        Integer isPrivate2 = isPrivate ? 1 : 0;

        // 일단, POSTS 테이블에는 행을 넣어야 겠지.
        Integer findSequenceValue = postsMapper.selectNextSequenceValue(); // 시퀀스 미리 얻어옴.
        postsMapper.insertPost(findSequenceValue, title, memberId, isPrivate2, content);

        // 그리고 이제 행을 삽입할지 말지 고민해야 하는 건,
        // POST_PASSWORD, POST_VIEW, POST_LIKE 테이블이거든?

        // POST_PASSWORD 테이블은 게시글 작성자가 게시글에 비밀번호를 걸고 싶다고 했다면, 당연히 넣어줘야지.
        if(isPrivate2 == 1){
            // 만약 사용자가 게시글의 비밀번호를 설정한 경우.
            // 이때에만, POST_PASSWORD 라는 테이블에 행을 삽입하도록 한다.
            postPasswordMapper.insertPostPassword(findSequenceValue, postPassword);
        }

        // 이외에도 POST_VIEW 테이블과 POST_LIKE 테이블에 행을 삽입해둘까? 생각해봤는데,
        // 만약 누구도 조회하지 않는 경우, 그리고 누구도 좋아요를 누르지 않은 경우,
        // db 에 저장되지 않아도 될 데이터를 굳이 넣는 결과가 되기 때문에,
        // 실제로 조회가 발생한 그 순간 POST_VIEW 테이블에 행을 넣고,
        // 실제로 누군가 좋아요를 누른 그 순간 POST_LIKE 테이블에 행을 넣기로 하였다.
        //postViewMapper.initPostView(findSequenceValue); <- insert 안함.
        //postLikeMapper.initPostLike(findSequenceValue); <- insert 안함.
    }
}
