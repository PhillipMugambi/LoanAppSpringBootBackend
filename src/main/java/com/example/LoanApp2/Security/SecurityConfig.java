package com.example.LoanApp2.Security;

import com.example.LoanApp2.Filter.CustomUersDetails;
import com.example.LoanApp2.Filter.JwtTokenFilter;
import com.example.LoanApp2.services.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private UserServiceImpl userService;
    private CustomUersDetails customUersDetails;
    private JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoderD());
    }

    @Bean //spring bean
    public PasswordEncoder passwordEncoderD() {
        return new BCryptPasswordEncoder(); //return if coverted to shaa


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            LOGGER.log(Level.SEVERE, "Unauthorized request - {}" + ex.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }
                )
                .and();


        // Set permissions on endpoints
        http.authorizeRequests()
                // Our public endpoints
                .antMatchers("/authenticate/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("swagger-ui/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                // .antMatchers("/configuration/ui").permitAll()

                .antMatchers("/configuration/security").permitAll()
                // .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()


                // Our private endpoints
                .anyRequest().authenticated();


        // Add JWT token filter
        http.addFilterBefore(jwtTokenFilter, customUersDetails);

    }


    // Expose authentication manager bean
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}