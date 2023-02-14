package com.example.LoanApp2.services;

import com.example.LoanApp2.model.Role;
import com.example.LoanApp2.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String rolename);
    User getUser(String username);
    List<User>getusers();
}
