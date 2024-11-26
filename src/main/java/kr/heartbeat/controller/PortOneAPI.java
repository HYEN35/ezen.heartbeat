package kr.heartbeat.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PortOneAPI {

    @GetMapping("/getAccessToken")
    public String getAccessToken() throws Exception {
        String url = "https://api.iamport.kr/users/getToken"; // 포트원 API의 엑세스 토큰 발급 URL

        // 포트원 API Key와 Secret
        String clientId = "8222383208550316"; // 포트원에서 제공한 API Key
        String clientSecret = "Ef2oRQvlageeXJiHoZ7IgmMWhJQZ9NaVazj9VBxBoPWpkspP7YCKhNy87dena5UzEOuVM5mmlxPwouxl"; // 포트원에서 제공한 API Secret

        String params = "imp_key=" + clientId + "&imp_secret=" + clientSecret + "&grant_type=client_credentials";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = params.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        BufferedReader in;
        StringBuilder response = new StringBuilder();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            throw new Exception("Failed to get access token. Error response: " + response.toString());
        }

        // 응답을 JSON으로 변환
        JSONObject jsonResponse = new JSONObject(response.toString());
        
        // response 객체 내부의 access_token을 가져옴
        if (jsonResponse.has("response")) {
            JSONObject responseObject = jsonResponse.getJSONObject("response");
            if (responseObject.has("access_token")) {
                String accessToken = responseObject.getString("access_token");
                return accessToken;
            } else {
                throw new Exception("Failed to get access token. Missing access_token in response.");
            }
        } else {
            throw new Exception("Failed to get access token. Missing response in response.");
        }
    }
}