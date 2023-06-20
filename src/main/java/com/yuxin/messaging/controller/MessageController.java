package com.yuxin.messaging.controller;

import com.yuxin.messaging.annotation.NeedAuthentication;
import com.yuxin.messaging.exception.MessagingServiceException;
import com.yuxin.messaging.model.User;
import com.yuxin.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    @NeedAuthentication

    public void sendMessage(User user, @RequestBody String body) throws MessagingServiceException {
        System.out.println(user);
    }
}
