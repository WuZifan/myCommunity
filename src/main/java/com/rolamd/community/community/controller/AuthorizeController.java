package com.rolamd.community.community.controller;

import com.rolamd.community.community.dto.AccessTokenDTO;
import com.rolamd.community.community.dto.GithubUserDTO;
import com.rolamd.community.community.mapper.UserMapper;
import com.rolamd.community.community.model.User;
import com.rolamd.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;
    
    @GetMapping("/callback")
    public String loginByGit(@RequestParam(name = "code") String code,
                             @RequestParam(name = "state") String state,
                             HttpServletRequest request,
                             HttpServletResponse response){
        System.out.println("First login: "+request.getHeader("referer"));


        // step1: in the html
        // step2: get access token
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(this.clientId);
        accessTokenDTO.setClient_secret(this.clientSecret);
        accessTokenDTO.setRedict_uri(this.redirectUri);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        // step3: get user info
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);

        System.out.println(githubUserDTO);
        System.out.println("login");

        if(githubUserDTO != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            user.setBio(githubUserDTO.getBio());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUserDTO.getAvatar_url());

            response.addCookie(new Cookie("token",token));
            // 登录成功
            addUser(user);
//            userMapper.insert(user);
//            request.getSession().setAttribute("user",githubUserDTO);
            System.out.println("login: "+request.getHeader("referer"));
            System.out.println("scheme"+request.getScheme());
            System.out.println("servletPath"+request.getContextPath());
            return "redirect:"+request.getHeader("referer");
//            return "publish";
        }else{
            // 登录失败
            return "redirect:"+request.getHeader("referer");
        }
    }

    @GetMapping("/logout")
    public String doLogout(HttpServletRequest request,
                           HttpServletResponse response){
        

        System.out.println(request.getHeader("referer"));

        System.out.println("do logout");
        request.getSession().setAttribute("user",null);
        if(request.getCookies()!=null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    System.out.println("reset token");
                    cookie.setValue("");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return "redirect:"+request.getHeader("referer");
    }

    private void addUser(User user){
        User oldUser = userMapper.getUserByAcccountId(user.getAccountId());
        if(oldUser == null){
            userMapper.insert(user);
        }else{
            userMapper.updateUserTokenByAccountId(user);
        }
    }
}
