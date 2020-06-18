package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;

import java.util.List;


public interface AdminService {
    ResponseVO addManager(UserForm userForm);

    ResponseVO editUserInfo(UserForm userForm,int userid);

    ResponseVO deleteUser(int userId);

    List<User> getAllManagers();

    List<User> getAllUsers();

    List<User> getVip();
}
