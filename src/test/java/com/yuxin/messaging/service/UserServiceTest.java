package com.yuxin.messaging.service;

import lombok.var;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserServiceTest {

    @Test
    public void testMd5_happyCase() throws Exception {
        String input = "password";
        var md5String = UserService.md5(input);
        assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", md5String);
    }

    @Test // testTarget_scenario_expectation()
    public void testMd5_nullInput_returnsNull() throws Exception {
        String input = null;
        assertNull(UserService.md5(input));
    }
}
