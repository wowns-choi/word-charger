package firstportfolio.wordcharger.controller.coreController;

import firstportfolio.wordcharger.DTO.CountDTO;
import firstportfolio.wordcharger.DTO.IncludeDTO;
import firstportfolio.wordcharger.DTO.MemberDTO;
import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.CountMapper;
import firstportfolio.wordcharger.repository.FixedDayMapper;
import firstportfolio.wordcharger.repository.IncludeMapper;
import firstportfolio.wordcharger.repository.WordMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class zeroToHunderedController {

    private final CountMapper countMapper;
    private final FixedDayMapper fixedDayMapper;
    private final IncludeMapper includeMapper;
    private final WordMapper wordMapper;
    @GetMapping("/zeroToHundred")
    public String zeroToHundredController(HttpServletRequest request){

        //id 꺼내기
        HttpSession session = request.getSession(false);
        MemberDTO loginedMember = (MemberDTO) session.getAttribute("loginedMember");
        String id = loginedMember.getId();

        //count테이블에서 위에서 꺼낸 아이디로 행 조회하기
        CountDTO idMatchingCountTableRow = countMapper.findRowById(id);

        //만약, 아이디에 매칭되는 행이 없는 경우(=최초로 단어 시험 보는 경우),
        //count 테이블에는 id 컬럼을 제외하고 모두 null 을 삽입하는 행을 추가.
        //fixedday 테이블에는 id 컬럼을 제외하고 모든 컬럼을 1 로 하는 행을 추가.
        //include 테이블에는 id 컬럼을 제외하고 모든 컬럼을 false 로 하는 행을 추가. => false는 count테이블에 안올라와있음을 의미.
        if (idMatchingCountTableRow == null) {
            countMapper.CountInit(id);
            includeMapper.includeInit(id);
            fixedDayMapper.fixedDayInit(id);
        }

        //아이디에 매칭되는 행이 있는 경우
        //include 에 false 인 값을 지닌 컬럼명을 가져와서 fixedday테이블의 그 컬럼명이 지닌 값을 찾아서, 그 값대로 count테이블의 컬럼에 들어가야 함.
        //일단, include 테이블에서 id 와 매핑되는 행을 가져와야겠지.
        IncludeDTO idMappingRowInInclude = includeMapper.findRowById(id);
        Class<? extends IncludeDTO> reflection = idMappingRowInInclude.getClass();
        List<String> falseFields = new ArrayList<>();


        //여기부터 진행해.

        for (Field field : reflection.getDeclaredFields()) {
            try {
                Object value = field.get(idMappingRowInInclude);
                if (value.equals("false")) {
                    falseFields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        for (String falseFieldName : falseFields) {
            //a = falseFieldName

            //a= include 테이블에 있던 false 라는 값을 가진 컬럼의 이름
            String findedFixedDay = fixedDayMapper.findFixedDayByIdAndColumn(id, falseFieldName);
            log.info("여기야여기={}", findedFixedDay);


            if (findedFixedDay.equals("1")) {
                countMapper.uploadFixedDayToCount("one", id, falseFieldName);
            } else if (findedFixedDay.equals("2")) {
                countMapper.uploadFixedDayToCount("two", id, falseFieldName);
            } else if (findedFixedDay.equals("3")) {
                countMapper.uploadFixedDayToCount("three", id, falseFieldName);
            } else if (findedFixedDay.equals("4")) {
                countMapper.uploadFixedDayToCount("four", id, falseFieldName);
            } else if (findedFixedDay.equals("5")) {
                countMapper.uploadFixedDayToCount("five", id, falseFieldName);
            } else if (findedFixedDay.equals("6")) {
                countMapper.uploadFixedDayToCount("six", id, falseFieldName);
            } else if (findedFixedDay.equals("7")) {
                countMapper.uploadFixedDayToCount("seven", id, falseFieldName);
            } else if (findedFixedDay.equals("8")) {
                countMapper.uploadFixedDayToCount("eight", id, falseFieldName);
            } else if (findedFixedDay.equals("9")) {
                countMapper.uploadFixedDayToCount("nine", id, falseFieldName);
            } else if (findedFixedDay.equals("10")) {
                countMapper.uploadFixedDayToCount("ten", id, falseFieldName);
            }

            includeMapper.updateToTrue(falseFieldName, id);
        }

        String pulledOneColumn = countMapper.findOneColumnById(id);
        String[] splitedVoca = pulledOneColumn.split(",");
        String firstVoca = splitedVoca[0];
        if(firstVoca.equals("")){
            firstVoca = splitedVoca[1];
        }

        countMapper.deletePulledVoca(firstVoca, id);



        //firstVoca 의 correct 와 wrong을 꺼내와야지
        WordDTO findRowFromWord = wordMapper.findByVoca(firstVoca);

        String voca = findRowFromWord.getVoca();
        String correct = findRowFromWord.getCorrect();
        String wrong1 = findRowFromWord.getWrong1();
        String wrong2 = findRowFromWord.getWrong2();

        List<String> answer = new ArrayList<>();
        answer.add(correct);
        answer.add(wrong1);
        answer.add(wrong2);

        Collections.shuffle(answer);

        request.setAttribute("voca", voca);
        request.setAttribute("answer", answer);

        return "/charger/zeroToHundredQuestion";

    }
}
