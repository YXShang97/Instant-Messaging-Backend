package com.yuxin.messaging.service;

import java.util.Date;
import java.util.List;

import com.yuxin.messaging.dao.UserDAO;
import com.yuxin.messaging.enums.Gender;
import com.yuxin.messaging.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public void register(String username,
                         String nickname,
                         String email,
                         String password,
                         String repeatPassword,
                         String address,
                         Gender gender) throws Exception {
        // validation
        // passwords are matched
        if (!password.equals(repeatPassword)) {
            throw new Exception();
        }
        if (username == null || username.isEmpty()) {
            throw new Exception();
        }

        List<User> selectedUsers = this.userDAO.selectByEmail(email);
        if (selectedUsers != null && !selectedUsers.isEmpty()) {
            System.out.println(selectedUsers);
            throw new IllegalArgumentException();
        }

        List<User> selectedUsersByName = this.userDAO.selectByUserName(username);
        if (selectedUsersByName != null && !selectedUsersByName.isEmpty()) {
            System.out.println(selectedUsersByName);
            throw new IllegalArgumentException();
        }

        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setGender(gender);
        user.setValid(false);
        user.setRegisterTime(new Date());

        this.userDAO.insert(user);
    }
}
