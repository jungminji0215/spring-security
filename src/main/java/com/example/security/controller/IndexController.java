package com.example.security.controller;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // localhost:8080/
    // localhost:8080
    @GetMapping({"","/"})
    public String index(){
        // 머스테치
        // 기본폴더 src/main/resources/
        // 뷰리졸버 설정
        // prefix : templates
        // suffix : .mustache

        // src/main/resources/templates/index.mustache를 찾는데 이거 index.html로 바꿔줄거임
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    // 스프링 시큐리티가 낚아챔
    // 그러나 SecurityConfig 파일 생성 후에 작동을 안 한다.
    // 로그인 하지 않는 사용자가 권한 없는 페이지를 요청하면
    // loginForm으로 이동하도록 설정
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        // 그냥 이렇게하면 패스워드 암호화가 안돼서 시큐리트 로그인할수없음.
        // SecurityConfig 여기서 설정해주기.
        // userRepository.save(user);

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);

        return "redirect:/loginForm";
    }
}
