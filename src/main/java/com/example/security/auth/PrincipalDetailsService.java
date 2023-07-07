package com.example.security.auth;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 .loginProcessingUrl("/login"); 을 해서
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 Ioc 되어 있는
// loadUserByUsername 함수가 실행된다. (스프링 시큐리티 규칙임)
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // PrincipalDetails가 리턴되면서 : 시큐리티 session(Authentication(UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);

        if(username != null){
            System.out.println("아아아아아악");
            return new PrincipalDetails(userEntity);
        }
        System.out.println("kdkdksfkdslkfsdfsdfsdfsdf");

        return null;
    }
}
