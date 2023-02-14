package com.example.LoanApp2.services;

import com.example.LoanApp2.model.Role;
import com.example.LoanApp2.model.User;
import com.example.LoanApp2.repositories.RoleRepository;
import com.example.LoanApp2.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if (user==null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        else{
            log.error("User found{}",user);
        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));});
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
    @Override
    public User saveUser(User user) {
        log.info("saving new user to the database");
        return userRepository.save(user);


    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new Role to the database");
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String name) {
        User user=userRepository.findByUsername(username);
        Role role=roleRepository.findRoleByName(name);
        log.info("saving new user to the database");
        user.getRoles().add(role);
    }


    @Override
    public User getUser(String username) {
        log.info("fetching user{}",username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getusers() {
        log.info("fetching new users");
        return userRepository.findAll();
    }
}
