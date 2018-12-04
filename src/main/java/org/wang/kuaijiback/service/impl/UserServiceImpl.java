package org.wang.kuaijiback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.wang.kuaijiback.bean.JWTUser;
import org.wang.kuaijiback.bean.Result;
import org.wang.kuaijiback.bean.User;
import org.wang.kuaijiback.dao.UserDao;
import org.wang.kuaijiback.service.UserService;
import org.wang.kuaijiback.util.JWTUtil;
import org.wang.kuaijiback.util.MailUtil;
import org.wang.kuaijiback.util.RedisUtil;
import org.wang.kuaijiback.util.StringUtil;

import java.util.Date;

import static java.util.Arrays.asList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public String addUser(User user) {
        String msg=null;
        //1.验证用户名
        UserDetails u=userDetailsService.loadUserByUsername(user.getUsername());
        if(u!=null){
          msg="用户名重复";
          return msg;
        }
        //2.验证邮箱
        String key="captcha"+user.getEmail();
        String value=redisUtil.get(key);
        if(user.getCaptcha()!=null && !user.getCaptcha().equals(value)){
            msg="验证码错误";
            return msg;
        }

        String _password=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(_password);
        user.setCreateTime(new Date());
        user.setRoles(asList("ROLE_USER"));
        userDao.addUser(user);
        return msg;
    }

    @Override
    public String login(User user) {
        String username=user.getUsername();
        String password=user.getPassword();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtUtil.generateToken(userDetails);
        return token;

    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());

        return jwtUtil.refreshToken(token);

    }

    @Override
    public void checkEmail(String email){
        int captcha=(int)(Math.random()*10000);
        String value=captcha+"";
        String key="captcha"+email;
        redisUtil.del(key);
        //验证码存入redis 120秒
        redisUtil.set(key,value,1200);
        mailUtil.sendSimpleMail(email,"验证码","您的验证码为"+captcha);
    }

    @Override
    public User getBy(User user) {
        return userDao.getBy(user);
    }



}
