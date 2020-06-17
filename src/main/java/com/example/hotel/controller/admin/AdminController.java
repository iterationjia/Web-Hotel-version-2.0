package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm){

        return adminService.addManager(userForm);
    }
    @PostMapping("/{userid}/editUserInfo")
    public ResponseVO editUserInfo(@RequestBody UserForm userForm ,@PathVariable int userid){
        System.out.println(userid);
        System.out.println(userForm.getPassword());
        System.out.println(userForm.getUserName());
        return adminService.editUserInfo(userForm,userid);
    }
    @GetMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }
    @GetMapping("/getAllUsers")
    public ResponseVO getAllUsers(){
        return ResponseVO.buildSuccess(adminService.getAllUsers());
    }
    @GetMapping("/getVip")
    public ResponseVO getVip(){
        return ResponseVO.buildSuccess(adminService.getVip());
    }
    @PostMapping("/{userid}/deleteUser")
    public ResponseVO deleteUser(@PathVariable int userid){
    return adminService.deleteUser(userid);
}
}
