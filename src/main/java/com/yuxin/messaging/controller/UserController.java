package com.yuxin.messaging.controller;


import com.yuxin.messaging.enums.Status;
import com.yuxin.messaging.exception.MessagingServiceException;
import com.yuxin.messaging.request.RegisterUserRequest;
import com.yuxin.messaging.response.CommonResponse;
import com.yuxin.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public CommonResponse register(@RequestBody RegisterUserRequest registerUserRequest) throws MessagingServiceException {
        this.userService.register(registerUserRequest.getUsername(),
                registerUserRequest.getNickname(),
                registerUserRequest.getEmail(),
                registerUserRequest.getPassword(),
                registerUserRequest.getRepeatPassword(),
                registerUserRequest.getAddress(),
                registerUserRequest.getGender());

        return new CommonResponse(Status.OK);
    }

    @PostMapping("/login")
    public String login() {
        return "";
    }
}
