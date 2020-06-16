package com.example.hotel.bl.admin;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.HotelVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface AdminService {

    /**
     * 添加酒店管理人员账号
     * @param userForm
     * @return
     */
    ResponseVO addManager(UserForm userForm);
    ResponseVO editUserInfo(UserForm userForm,int userid);

    //
    ResponseVO deleteUser(int userId);

    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<User> getAllManagers();
    List<User> getAllUsers();
    List<User> getVip();
    //
    List<HotelVO> getHotels();

}
