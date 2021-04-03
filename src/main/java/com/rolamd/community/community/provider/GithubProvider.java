package com.rolamd.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.rolamd.community.community.dto.AccessTokenDTO;
import com.rolamd.community.community.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        String url = "https://github.com/login/oauth/access_token";
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute())
        {
            String bodyStr = response.body().string();
            System.out.println(bodyStr);

            String access_token = bodyStr.split("&")[0].split("=")[1];
            System.out.println(access_token);
            return access_token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDTO getUser(String accessToken){
        System.out.println("in getUser "+accessToken);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("Authorization","token "+accessToken)
                .url("https://api.github.com/user")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String bodyStr = response.body().string();
            GithubUserDTO githubUserDTO = JSON.parseObject(bodyStr,GithubUserDTO.class);
            return githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
