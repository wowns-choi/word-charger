package firstportfolio.wordcharger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WordchargerApplication {

	//스프링 2.0 이후 버전에서는 RestTemplate 을 자동으로 빈으로 등록해주지 않음. -> 수동 빈 설정 필요.
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(WordchargerApplication.class, args);
	}

}
