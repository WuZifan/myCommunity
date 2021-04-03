package com.rolamd.community.community.controller;

import com.rolamd.community.community.dto.AccessTokenDTO;
import com.rolamd.community.community.dto.GithubUserDTO;
import com.rolamd.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value(("${github.redirect.uri}"))
    private String redirectUri;


    
    @GetMapping("/callback")
    public String loginByGit(@RequestParam(name = "code") String code,
                             @RequestParam(name = "state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(this.clientId);
        accessTokenDTO.setClient_secret(this.clientSecret);
        accessTokenDTO.setRedict_uri(this.redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);
        System.out.println(githubUserDTO);
        System.out.println("login");
        return "index";
    }
}
