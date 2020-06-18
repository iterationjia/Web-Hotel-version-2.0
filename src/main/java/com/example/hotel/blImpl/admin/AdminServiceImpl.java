package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    AdminMapper adminMapper;
    @Override
    public ResponseVO addManager(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setCredit(userForm.getCredit());
        user.setUserName(userForm.getUserName());
        user.setUserType(UserType.HotelManager);
        try {
            adminMapper.addManager(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO editUserInfo(UserForm userForm,int userid) {
        User user = new User();
        user.setId(userForm.getId());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setUserName(userForm.getUserName());
        try {
            adminMapper.editUserInfo(userid,user.getPassword(),user.getUserName(),user.getPhoneNumber());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }
    @Override
    public List<User> getAllUsers() {
        return adminMapper.getAllUsers();
    }
    //
    @Override
    public List<User> getVip() {
        return adminMapper.getVip();
    }
    @Override
    public ResponseVO deleteUser(int userid) {
        //删除用户逻辑的具体实现（注意可能有和别的业务类之间的交互）
        //数据库操作
        adminMapper.deleteUser(userid);
        return ResponseVO.buildSuccess(true);
    }

}
