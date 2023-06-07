package com.yuxin.messaging.dao;

import java.util.List;

import com.yuxin.messaging.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

// data access object
@Mapper
@Repository
public interface UserDAO {
    // user.getUsername();

    @Insert("INSERT INTO user (username, nickname, password, register_time, gender, email, address, is_valid) " +
            "VALUES (#{username}, #{nickname}, #{password}, #{registerTime}, #{gender}, #{email}, #{address}, #{valid})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM user WHERE email = #{email}")
    List<User> selectByEmail(String email);

    @Select("SELECT * FROM user WHERE username = #{username}")
    List<User> selectByUserName(String username);

    @Update("UPDATE user SET is_valid = 1 WHERE id = #{userId}")
    void updateValid(int userId);

    @Delete("DELETE FROM user")
    void deleteAll();
}
//
//public class UserDAOImpl implements UserDAO {
//
//    @Override
//    public void insert(User user) {
//        var connector = new Connector();
//        connector.connect();
//        try {
//            connector.executeQuery("INSERT INTO user (username, nickname, password, register_time, gender, email, " +
//                                           "address, is_valid) VALUES (?,?,?,?)", user.getUsername(), user
//                                           .getNickname());\
//        } catch (NetworkException exception) {
//
//        } finally {
//            connector.cleanup();
//        }
//    }
//}
