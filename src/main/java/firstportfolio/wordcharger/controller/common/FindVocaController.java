package firstportfolio.wordcharger.controller.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FindVocaController {

    private static final String NOT_HANGLE_REG_EXP = "^[^가-힣]*$";
    private static final String NOT_ENGLISH_REG_EXP = "^[^a-zA-Z]*$";


    @PostMapping("/find-voca")
    @ResponseBody
    public Map<String,String> findVocaControllerMethod(@RequestParam("voca") String voca){
        String clientId = "yiy5cHLfvHqrLo18uims";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "2135NUielv";//애플리케이션 클라이언트 시크릿값";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try {
            text = URLEncoder.encode(voca, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = null;
        if(voca.matches((NOT_HANGLE_REG_EXP))){
            //영어 주면 한글 번역해주는 것
             responseBody = post1(apiURL, requestHeaders, text);
        }else if(voca.matches(NOT_ENGLISH_REG_EXP)){
             responseBody = post2(apiURL, requestHeaders, text);
        }


        System.out.println(responseBody);

        // Jackson ObjectMapper를 사용하여 JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode messageNode = rootNode.path("message");
            JsonNode resultNode = messageNode.path("result");
            String translatedText = resultNode.path("translatedText").asText();

            System.out.println("Translated Text: " + translatedText);
            // 필요하다면 translatedText를 반환하거나 다른 작업을 수행

            Map<String, String> response = new HashMap<>();
            response.put("voca", voca); // 요청 받은 단어
            response.put("translated", translatedText); // 번역된 텍스트


            return response;
        } catch (IOException e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }
    private static String post1(String apiUrl, Map<String, String> requestHeaders, String text){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=en&target=ko&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
    private static String post2(String apiUrl, Map<String, String> requestHeaders, String text){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=ko&target=en&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }


}
