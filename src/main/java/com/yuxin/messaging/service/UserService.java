package com.yuxin.messaging.service;

import java.util.Date;
import java.util.List;

import com.yuxin.messaging.dao.UserDAO;
import com.yuxin.messaging.dao.UserValidationCodeDAO;
import com.yuxin.messaging.enums.Gender;
import com.yuxin.messaging.model.User;
import com.yuxin.messaging.model.UserValidationCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserValidationCodeDAO userValidationCodeDAO;

    @Autowired
    private JavaMailSender javaMailSender;

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

        // check whether email already exists
        List<User> selectedUsers = this.userDAO.selectByEmail(email);
        if (selectedUsers != null && !selectedUsers.isEmpty()) {
            System.out.println(selectedUsers);
            throw new IllegalArgumentException();
        }

        // check whether username already exists
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

        // insert into user table
        this.userDAO.insert(user);

        // generate validation code
        var validationCode = RandomStringUtils.randomNumeric(6);
        UserValidationCode userValidationCode = new UserValidationCode();
        userValidationCode.setUserId(user.getId());
        userValidationCode.setValidationCode(validationCode);
        this.userValidationCodeDAO.insert(userValidationCode);

        // send validation code to user via email
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("Registration Validation");
        msg.setText(String.format("Validation code is: %s", validationCode));
        javaMailSender.send(msg);
    }
}
