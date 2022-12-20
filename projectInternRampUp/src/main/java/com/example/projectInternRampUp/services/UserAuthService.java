package com.example.projectInternRampUp.services;

import com.example.projectInternRampUp.entities.Role;
import com.example.projectInternRampUp.entities.User;
import com.example.projectInternRampUp.enumerations.Authorities;
import com.example.projectInternRampUp.repositories.UserRepository;
import com.example.projectInternRampUp.services.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//This class gets the username and load user details.
//Authenticationmanager in turn will set up the password encoder to compare
//the password recovered from the oracle database

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Login not found"));

        Integer admin = 0;
        Integer operator = 0;

        //iterates through the user roles and if he has one one of them the value of their variable receives a 1
        for (Role roles : user.getRoles()) {
            if (roles.getAuthority() == Authorities.ADMIN) {
                admin = 1;
            } else if (roles.getAuthority() == Authorities.OPERATOR) {
                operator = 1;
            }
        }

        //using the value set to the variable admin or operator from the foreach, build the user with it's correct role.
        if (admin == 1 && operator == 0) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles("ADMIN")
                    .build();
        } else if (admin == 0 && operator == 1) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles("OPERATOR")
                    .build();
        } else if (admin == 1 && operator == 1) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles("ADMIN", "OPERATOR")
                    .build();
        } else {
            throw new DatabaseException("No roles");
        }

    }
}