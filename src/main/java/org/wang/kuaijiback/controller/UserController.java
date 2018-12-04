package org.wang.kuaijiback.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wang.kuaijiback.bean.Company;
import org.wang.kuaijiback.bean.Page;
import org.wang.kuaijiback.bean.Result;
import org.wang.kuaijiback.bean.User;
import org.wang.kuaijiback.service.CompanyService;
import org.wang.kuaijiback.service.UserService;
import org.wang.kuaijiback.util.FileUtil;
import org.wang.kuaijiback.util.MailUtil;
import org.wang.kuaijiback.util.PoiUtil;
import org.wang.kuaijiback.util.RedisUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(value="USER")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/demo")
    public String demo(){
        return"demo";
    }

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public Result login(User user){
        Result result=new Result();
        try{
            String token=userService.login(user);

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/checkemail",method = RequestMethod.POST)
    public Result checkEmail(@RequestParam String email){
        Result result=new Result();
        try{
            userService.checkEmail(email);
            result.setSuccess(true);
            result.setMsg("验证码发送成功s");
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/adduser",method = RequestMethod.POST)
    public Result addUser(@RequestBody User user){
        Result result=new Result();
        try {
            String msg = userService.addUser(user);
            if(msg!=null){
                result.setMsg(msg);
                return result;
            }
            result.setMsg("注册成功");
            result.setSuccess(true);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result.setMsg("系统出错");
        }
        return result;
    }

    @RequestMapping(value="/getby",method = RequestMethod.POST)
    public Result getBy(@RequestBody User user){
        Result result=new Result();
        try {
            User u = userService.getBy(user);
            result.setSuccess(true);
            result.setMsg("系统已存在");
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result.setMsg("系统出错");
        }
        return result;
    }

//    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws AuthenticationException {
//        final String token = userService.login(user);
//        // Return the token
//        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
//    }
//
//    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
//    public ResponseEntity<?> refreshAndGetAuthenticationToken(
//            HttpServletRequest request) throws AuthenticationException{
//        String token = request.getHeader(tokenHeader);
//        String refreshedToken = userService.refresh(token);
//        if(refreshedToken == null) {
//            return ResponseEntity.badRequest().body(null);
//        } else {
//            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
//        }
//    }
//
//    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
//    public User register(@RequestBody User addedUser) throws AuthenticationException{
//        return authService.register(addedUser);
//    }








}
