package com.example.LoanApp2.Filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class CustomUersDetails extends org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    public void UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager){
this.authenticationManager=authenticationManager;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
      String username=request.getParameter("username");
        String password=request.getParameter("password");
        log.info("Username is:{}",username);
        log.info("UserPassword is:{}",password);
        UsernamePasswordAuthenticationToken AuthenticationToken= new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(AuthenticationToken);
    }
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        User user= (User) authentication.getPrincipal();
//        Algorithm algorithm=Algorithm.HMAC256("sicret".getBytes());
//        String access_token= JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000))
//                .withIssuer(request.getRequestURL().toString())
//                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).sign(algorithm)
//                ;
//        String refresh_token= JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+ 30*60*1000))
//                .withIssuer(request.getRequestURL().toString()).sign(algorithm);
//        response.setHeader("accesstoken",access_token);
//        response.setHeader("refreshtoken",refresh_token);
//    }

}
