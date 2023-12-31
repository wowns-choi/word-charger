package firstportfolio.wordcharger.sevice.common;

import firstportfolio.wordcharger.DTO.WritingDTOSelectVersion;
import firstportfolio.wordcharger.repository.WritingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
