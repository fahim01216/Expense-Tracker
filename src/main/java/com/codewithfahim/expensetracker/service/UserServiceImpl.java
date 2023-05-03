package com.codewithfahim.expensetracker.service;

import com.codewithfahim.expensetracker.entity.User;
import com.codewithfahim.expensetracker.entity.UserModel;
import com.codewithfahim.expensetracker.exception.ItemAlreadyExistsException;
import com.codewithfahim.expensetracker.exception.ResourceNotFoundException;
import com.codewithfahim.expensetracker.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserModel userModel) {
        if(userRepository.existsByEmail(userModel.getEmail())) {
            throw new ItemAlreadyExistsException("User already exists with email " + userModel.getEmail());
        }
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(new Random().nextLong());
        return userRepository.save(user);
    }

    @Override
    public User getUser() {
        Long userId = getLoggedInUser().getId();
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exists"));
    }

    @Override
    public User updateUser(UserModel user) {
        User existingUser = getUser();
        existingUser.setUsername(user.getUsername() != null ? user.getUsername() : existingUser.getUsername());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
        existingUser.setPassword(user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : existingUser.getPassword());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser() {
        User existingUser = getUser();
        userRepository.delete(existingUser);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found for the email " + email));
    }
}
