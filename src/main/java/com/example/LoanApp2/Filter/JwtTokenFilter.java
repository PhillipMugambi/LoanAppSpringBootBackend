package com.example.LoanApp2.Filter;

import com.example.LoanApp2.JwtTokenUtils.JwtTokenUtil;
import com.example.LoanApp2.services.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
@Slf4j
public  class JwtTokenFilter extends OncePerRequestFilter  {
    private final UserServiceImpl userService;
    private final JwtTokenUtil jwtTokenUtil;
    public JwtTokenFilter( UserServiceImpl userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        } else {
           // UniversalResponse universalResponse;
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {

                    token = authorizationHeader.substring(7);
                    username = jwtTokenUtil.extractUsername(token);
                    UserDetails userDetails = userService.loadUserByUsername(username);

                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        filterChain.doFilter(request, response);
                    }
                } catch (Exception e) {
                    log.info("exceptions " + e.getMessage());
                    //universalResponse = UniversalResponse.builder()
                           // .status(401)
                            //.message("Token has expired to perform this request")
                           // .build();
                   // response.setContentType(APPLICATION_JSON_VALUE);
                   // new ObjectMapper().writeValue(response.getOutputStream(), universalResponse);
                }
            } else {
                filterChain.doFilter(request, response);
            }

        }

    }
}