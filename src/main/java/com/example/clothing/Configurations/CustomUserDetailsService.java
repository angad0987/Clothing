package com.example.clothing.Configurations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.clothing.DAO.UserRepository;
import com.example.clothing.Entities.User;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("User id :--------------------> " + username);
        User user = userRepository.findByUserId(username);
        System.out.println("User id is ---------------------------------------------->" + user.getUserId());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // User user = opt.get();
        return user;
    }

}
