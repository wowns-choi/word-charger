package firstportfolio.wordcharger.sevice.board.common;

import firstportfolio.wordcharger.DTO.PostsDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class WritingDateChangeService {

    public List<PostsDTO> changeDate(List<PostsDTO> postsList){
        for (PostsDTO post : postsList) {
            Date date = post.getWritingDate();
            // Date -> Instant -> LocalDateTime 변환
            LocalDateTime localDateTime = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            // DateTimeFormatter를 사용하여 년-월-일 형식으로 포맷팅
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = localDateTime.format(formatter);
            post.setStringWritingDate(formattedDate);
        }
        return postsList;
    }
}
