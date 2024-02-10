package firstportfolio.wordcharger.sevice.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class WritingService {
    private final WritingMapper writingMapper;

    public List<WritingDTOSelectVersion> findCurrentPageWritings (
            Integer startRow, Integer pageSize
    ){
        return writingMapper.findCurrentPageWritings(startRow, pageSize);
    }

    public Integer getTotalWritingsCount() {
        return writingMapper.countAllWriting();
    }

    public Map<String, Object> findTotalWritingByTitleWriterContent(String byWhatType, String hintToFind,Integer startRow, Integer pageSize){

        Integer findedWritingTotal = null;
        List<WritingDTOSelectVersion> currentPageWritings = null;


        if (byWhatType.equals("title")) {
            String searchKeyword = "%" + hintToFind + "%";
            findedWritingTotal = writingMapper.findWritingByTitle(searchKeyword);
            currentPageWritings = writingMapper.findCurrentPageWritingsByTitle(searchKeyword,startRow, pageSize);

        } else if (byWhatType.equals("writer")) {
            String searchKeyword = "%" + hintToFind + "%";
            findedWritingTotal = writingMapper.findWritingByWriter(searchKeyword);
            currentPageWritings = writingMapper.findCurrentPageWritingsByWriter(searchKeyword,startRow, pageSize);

        } else if (byWhatType.equals("content")) {
            String searchKeyword = "%" + hintToFind + "%";
            findedWritingTotal = writingMapper.findWritingByContent(searchKeyword);
            currentPageWritings = writingMapper.findCurrentPageWritingsByContent(searchKeyword,startRow, pageSize);
        }

        Map<String, Object> returnMap = new ConcurrentHashMap<>();
        returnMap.put("findedWritingTotal", findedWritingTotal);
        returnMap.put("currentPageWritings", currentPageWritings);

        return returnMap;
    }



}
