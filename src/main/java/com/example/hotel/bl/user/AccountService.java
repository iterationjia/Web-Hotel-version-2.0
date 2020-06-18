package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    ResponseVO registerAccount(UserVO userVO);

    User login(UserForm userForm);

    User getUserInfo(int id);

    ResponseVO updateUserInfo(int id, String password,String username,String phoneNumber,String avatarUrl);

    ResponseVO updateUserImg(MultipartFile file, Integer userId);

    String getUserImg(Integer userId);

    //供其它serviceImpl使用，用以根据行为更新credit
    ResponseVO creditSet(UserVO userVO);

    //供其它serviceImpl使用，用以根据行为更新totalMoney和lv
    ResponseVO lvSet(UserVO userVO);

    User getAccountByEmail(String email);

    //供其它serviceImpl使用，用以根据totalMoney更新totalMoney和lv
    void updateVip(Integer userId, Double money);
}
