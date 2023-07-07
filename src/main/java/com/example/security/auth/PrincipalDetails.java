package com.example.security.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 session 을 만들어준다. Security ContextHolder 키 값에다가 세션 정보를 저장한다.
// 시큐리티가 가지고 있는 세션에 들어갈 수 있는 객체가 정해져 있다. Authentication 타입 객체이다.
// Authentication 안에는 User 정보가 있어야 됨.
// user 오브젝트 타입은 userDetails 타입 객체?

import com.example.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Security Session 영역이있는데, 여기에 세션 정보를 저장해주어야하는데
// 여기들어갈 수 있는 객체가 Authentication 로 정해져 있는 것
// 그리고 Authentication 객체 안에 user 정보를 저장할 때 타입이 UserDetails 타입이여야 한다.
// 뭔소리야ㅡㅡ
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user){
        System.out.println("Ridkjfslkfjlsdkqnlknwlqnwlknqlnkqnflkdsnlvksndlfkndsf");
        this.user = user;
    }

    // 해당 user의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리 사이트에서 1년동안 회원이 로그인을 안하면
        // 휴면계정으로 하기로 했다고 하면 return false 하고 그런 기능
        return true;
    }
}
