package com.codewithfahim.expensetracker.controller;

import com.codewithfahim.expensetracker.entity.JwtResponse;
import com.codewithfahim.expensetracker.entity.LoginModel;
import com.codewithfahim.expensetracker.entity.User;
import com.codewithfahim.expensetracker.entity.UserModel;
import com.codewithfahim.expensetracker.security.CustomUserDetailsService;
import com.codewithfahim.expensetracker.service.UserService;
import com.codewithfahim.expensetracker.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginModel loginModel) throws Exception {
        authenticate(loginModel.getEmail(), loginModel.getPassword());

        // create jwt token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginModel.getEmail());
        final String token = jwtTokenUtil.generate(userDetails);

        return new ResponseEntity<>(new JwtResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch(DisabledException e) {
            throw new Exception("User Disabled !!");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials !!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
}
