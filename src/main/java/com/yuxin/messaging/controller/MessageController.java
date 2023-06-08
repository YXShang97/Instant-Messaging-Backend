package com.yuxin.messaging.controller;

import com.yuxin.messaging.exception.MessagingServiceException;
import com.yuxin.messaging.model.User;
import com.yuxin.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public void sendMessage(@RequestHeader("Login-Token") String loginToken) throws MessagingServiceException {
        User user = this.userService.authenticate(loginToken);
    }
}
