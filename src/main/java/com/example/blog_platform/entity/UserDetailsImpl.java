package com.example.blog_platform.entity;

import com.example.blog_platform.entity.enums.AppRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private String userName;
    private String password;
    private String email;
    private List<Role> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // map the role to a GrantedAuthority → ROLE_USER, ROLE_ADMIN
        return role.stream()
                .map(r -> (GrantedAuthority) () -> r.getRoleName().name())
                .toList();
    }

    @Override
    public String getPassword() {
        return password;   // ✅ return actual encoded password
    }

    @Override
    public String getUsername() {
        return userName;   // ✅ return username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // you can add real checks later
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetails build(User user) {
        UserDetailsImpl details = new UserDetailsImpl();
        details.email = user.getEmail();
        details.password = user.getPassword();
        details.userName = user.getUserName();
        details.role = user.getRoles();
        return details;
    }
}
