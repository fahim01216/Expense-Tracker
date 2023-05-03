package com.codewithfahim.expensetracker.service;

import com.codewithfahim.expensetracker.entity.User;
import com.codewithfahim.expensetracker.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);
    User getUser();

    User updateUser(UserModel user);

    void deleteUser();

    User getLoggedInUser();
}
