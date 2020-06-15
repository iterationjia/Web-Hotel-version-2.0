package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.po.Hotel;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
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
    @Autowired
    HotelService hotelService;
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
    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {

        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
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
    //
    @GetMapping("/getHotels")
    public ResponseVO getHotels(){return ResponseVO.buildSuccess(hotelService.retrieveHotels());
   // public ResponseVO getHotels(){return ResponseVO.buildSuccess(adminService.getHotels());
    }
    @PostMapping("/{userid}/deleteUser")
    public ResponseVO deleteUser(@PathVariable int userid){
    return adminService.deleteUser(userid);
}
    @PostMapping("/{hotelid}/deleteHotel")
    public ResponseVO deleteHotel(@PathVariable Integer hotelid){
        return hotelService.deleteHotel(hotelid);
    }
    @PostMapping("/{hotelid}/{managerid}/setHotelManager")
    public ResponseVO setHotelManager(@PathVariable Integer hotelid,@PathVariable int managerid){
//        System.out.println(hotelid);
//        System.out.println(managerid);
        return hotelService.setHotelManager(hotelid,managerid);}
}
