package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // @Bean 을 적으면 : 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    // 그러면 어디서든지 쓸 수 있다(?) IndexController 에서 의존성 주입해주어서 사용함
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                // 해당 경로는 인증이 필요, 로그인 한 사람만 들어올 수 있다. 로그인 안하면 403 오류
                .antMatchers("/user/**").authenticated()
                // 로그인 했지만, 해당 권한이 있어야만 들어올 수 있다. 인증 안되면 403 오류가 뜬다.
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                // 나머지는 이제 스프링 시큐리티에서 로그인 안해도 들어갈 수 있다.
                .anyRequest().permitAll()
                // 로그인 없이 권한 있는 곳 들어가면 login 페이지로 리다이렉트 해준다.
                .and()
                .formLogin()
                .loginPage("/loginForm") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인 진행해준다.   // 그래서 컨트롤러에 /login을 만들지 않아도 된다.
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/"); // 로그인 완료되면 메인페이지로
    }
}
