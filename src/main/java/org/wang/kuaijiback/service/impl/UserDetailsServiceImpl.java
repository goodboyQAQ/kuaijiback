package org.wang.kuaijiback.service.impl;//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.wang.kuaijiback.bean.User;
import org.wang.kuaijiback.dao.UserDao;
import org.wang.kuaijiback.util.JWTUtil;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =new User();
        user.setUsername(username);
        user=userDao.getBy(user);
        if(user!=null){
            return JWTUtil.createJWTUser(user);
        }
        return null;
    }
}
