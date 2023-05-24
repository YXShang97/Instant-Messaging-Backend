package com.yuxin.messaging.dao;

import com.yuxin.messaging.model.UserValidationCode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserValidationCodeDAO {
    @Insert("INSERT INTO user_validation_code (user_id, validation_code)" +
            "VALUES (#{userId}, #{validationCode})")
    void insert(UserValidationCode userValidationCode);
}
