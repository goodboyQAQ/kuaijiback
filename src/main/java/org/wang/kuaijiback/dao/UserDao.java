package org.wang.kuaijiback.dao;

import org.apache.ibatis.annotations.Mapper;
import org.wang.kuaijiback.bean.User;

@Mapper
public interface UserDao {
    void addUser(User user);
    User login(User user);
    User getBy(User user);
}
