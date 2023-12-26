package firstportfolio.wordcharger.config;


import firstportfolio.wordcharger.repository.CountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulingConfig {

    private final CountMapper countMapper;

    //0 0 0 은 0초 0분 0시 를 의미한다. 따라서, 00시 00분 00초 를 의미한다.
    //그 다음, * 는 매 일 을 의미한다.
    //그 다음, * 는 매 월 을 의미한다.
    //그 다음, ? 는 요일이 들어가는 곳인데, 매일 매월 과 함께 사용시 "일주일내내"라는 뜻을 가짐.
    @Scheduled(cron = "0 54 20 * * ?")
    public void performTask(){
        log.info("asdklfasdjlfh");
        countMapper.todayColumnCharger();
        countMapper.columnShiftUpdate();
    }

}
