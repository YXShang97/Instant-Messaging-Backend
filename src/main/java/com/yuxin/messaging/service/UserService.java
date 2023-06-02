package com.yuxin.messaging.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import com.yuxin.messaging.dao.UserDAO;
import com.yuxin.messaging.dao.UserValidationCodeDAO;
import com.yuxin.messaging.enums.Gender;
import com.yuxin.messaging.enums.Status;
import com.yuxin.messaging.exception.MessagingServiceException;
import com.yuxin.messaging.model.User;
import com.yuxin.messaging.model.UserValidationCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserValidationCodeDAO userValidationCodeDAO;
    @Autowired
    private EmailService emailService;

    public void register(String username,
                         String nickname,
                         String email,
                         String password,
                         String repeatPassword,
                         String address,
                         Gender gender) throws MessagingServiceException {
        // validation
        // passwords are matched
        if (!password.equals(repeatPassword)) {
            throw new MessagingServiceException(Status.PASSWORD_NOT_MATCH);
        }
        if (username == null || username.isEmpty()) {
            throw new MessagingServiceException(Status.EMPTY_USERNAME);
        }

        // check whether email already exists
        List<User> selectedUsers = this.userDAO.selectByEmail(email);
        if (selectedUsers != null && !selectedUsers.isEmpty()) {
            System.out.println(selectedUsers);
            throw new MessagingServiceException(Status.EMAIL_EXISTS_ALREADY);
        }

        // check whether username already exists
        List<User> selectedUsersByName = this.userDAO.selectByUserName(username);
        if (selectedUsersByName != null && !selectedUsersByName.isEmpty()) {
            System.out.println(selectedUsersByName);
            throw new MessagingServiceException(Status.USERNAME_EXISTS_ALREADY);
        }

        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setAddress(address);
        user.setGender(gender);
        user.setValid(false);
        user.setRegisterTime(new Date());

        // secure password with MD5 hashing and salting
        String salt = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
        user.setPassword(md5(password + salt));

        // insert into user table
        this.userDAO.insert(user);

        // generate validation code
        var validationCode = RandomStringUtils.randomNumeric(6);
        UserValidationCode userValidationCode = new UserValidationCode();
        userValidationCode.setUserId(user.getId());
        userValidationCode.setValidationCode(validationCode);
        this.userValidationCodeDAO.insert(userValidationCode);

        // send validation code to user via email
//        emailService.sendEmail(user.getEmail(), "Registration Validation", String.format("Validation code is: %s", validationCode));
    }

    public void activate(String identification, String validationCode) throws MessagingServiceException {
        List<User> selectedUsers = this.userDAO.selectByEmail(identification);
        if (selectedUsers.isEmpty()) {
            selectedUsers = this.userDAO.selectByUserName(identification);
            if (selectedUsers.isEmpty()) {
                throw new MessagingServiceException(Status.USER_NOT_EXISTS);
            }
        }

        var selectedUser = selectedUsers.get(0);
        // select userValidationCode by selectedUser.id
        String code = this.userValidationCodeDAO.selectByUserId(selectedUser.getId());
        // compare -> N: throw exception
        if (!code.equals(validationCode)) {
            throw new MessagingServiceException(Status.VALIDATION_CODE_NOT_MATCH);
        }
        //         -> Y: 1. update selectedUser (set valid = 1)
        this.userDAO.updateValid(selectedUser.getId());
        //               2. delete userValidationCode
        this.userValidationCodeDAO.deleteByUserId(selectedUser.getId());

    }

    public static String md5(String input) throws MessagingServiceException {

        String md5 = null;

        if(input == null) return null;

        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new MessagingServiceException(Status.UNKNOWN_EXCEPTION);
        }
        return md5;
    }
}
