package com.codewithfahim.expensetracker.service;

import com.codewithfahim.expensetracker.entity.User;
import com.codewithfahim.expensetracker.entity.UserModel;
import com.codewithfahim.expensetracker.exception.ItemAlreadyExistsException;
import com.codewithfahim.expensetracker.exception.ResourceNotFoundException;
import com.codewithfahim.expensetracker.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public User createUser(UserModel userModel) {
        if(userRepository.existsByEmail(userModel.getEmail())) {
            throw new ItemAlreadyExistsException("User already exists with email " + userModel.getEmail());
        }
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        user.setId(new Random().nextLong());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exists"));
    }
}
