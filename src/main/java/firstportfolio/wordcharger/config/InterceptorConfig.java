package firstportfolio.wordcharger.config;


import firstportfolio.wordcharger.logininterceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(loginInterceptor)
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**","/**/*.jpeg","/**/*.png","/error","/","/login-form","/memberJoin","/find-voca","/faq","/contact","/wayToCome","/wowns","/board-home","/writing-page","/findWriting");
//    }


}
