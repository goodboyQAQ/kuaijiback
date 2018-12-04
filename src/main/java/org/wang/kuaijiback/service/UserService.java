package org.wang.kuaijiback.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.wang.kuaijiback.bean.Result;
import org.wang.kuaijiback.bean.User;

public interface UserService{
    String addUser(User user);
    String login(User user);
    String refresh(String oldToken);
    void checkEmail(String email);
    User getBy(User user);
}
