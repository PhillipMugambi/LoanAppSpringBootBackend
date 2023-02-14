package com.example.LoanApp2;

import com.example.LoanApp2.model.Role;
import com.example.LoanApp2.model.User;
import com.example.LoanApp2.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class LoanApp2Application {

    public static void main(String[] args) {
        SpringApplication.run(LoanApp2Application.class, args);
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }
    @Bean
CommandLineRunner runner(UserService userService){
        return args -> {

        };
}

}
