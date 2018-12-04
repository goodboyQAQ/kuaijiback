package org.wang.kuaijiback.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
public class JWTUser implements UserDetails {
    private final String id;
    private final String userName;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Date lastPasswordResetDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {//账户未过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//账户未锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//密码未过期
        return true;
    }

    @Override
    public boolean isEnabled() {//账户未禁用
        return true;
    }
}
