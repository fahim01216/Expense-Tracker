package com.codewithfahim.expensetracker.security;

import com.codewithfahim.expensetracker.entity.User;
import com.codewithfahim.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the email: " + email));
        //List<UserDetails> userDetails = new ArrayList<>();

        return new org.springframework.security.core.userdetails
                .User(existingUser.getEmail(), existingUser.getPassword(), new ArrayList<>());
    }
}
