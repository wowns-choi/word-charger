package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.repository.PostPasswordMapper;
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
    public void insertPost(String title, Integer memberId, Boolean secretWritingCheckBox, String writingPassword, String content){
        Integer isPrivate = secretWritingCheckBox ? 1 : 0;
        Integer findSequenceValue = postsMapper.selectNextSequenceValue();
        postsMapper.insertPost(findSequenceValue, title, memberId, isPrivate, content);
        postPasswordMapper.insertPostPassword(findSequenceValue, writingPassword);
        log.info("findSequenceValue = {}", findSequenceValue);
    }
}
