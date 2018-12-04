package org.wang.kuaijiback.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class User{
    private String id;
    private String username;
    private String password;
    private String email;
    private Date createTime;
    private Date lastPasswordResetDate;
    private String captcha;

    private List<String> roles;

}
