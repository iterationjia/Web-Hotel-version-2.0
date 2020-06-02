package com.example.hotel.controller.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {
    private HotelService hotelService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{managerid}/getManagerOrders")
    public ResponseVO retrieveManagerOrders(@PathVariable Integer managerid){
        // 检索某个酒店管理员已预订的订单
        return ResponseVO.buildSuccess(orderService.getManagerOrders(managerid));
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable Integer userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @GetMapping("{userid}/{hotelid}/getUserHotelOrders")
    public ResponseVO retrieveUserHotelOrders(@PathVariable Integer userid, @PathVariable Integer hotelid){
        return ResponseVO.buildSuccess(orderService.getUserHotelOrders(userid,hotelid));
    }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid) throws ParseException {
        return orderService.annulOrder(orderid);
    }

    @PostMapping("/checkOut")
    public ResponseVO checkOut(@RequestBody OrderVO orderVO){
        return orderService.checkOut(orderVO);
    }

    @GetMapping("/{orderid}/execOrder")
    //
    public ResponseVO execOrder(@PathVariable int orderid){
        return orderService.execOrder(orderid);
    }


    @PostMapping("/deleteOrder")
    public ResponseVO deleteOrder(@RequestBody OrderVO orderVO){
        return orderService.deleteOrder(orderVO);
    }

    @PostMapping("/updateOrderComment")
    public ResponseVO updateOrderComment(@RequestBody OrderVO orderVO){
        return orderService.updateOrderComment(orderVO);
    }


    @GetMapping("/{hotelId}/allOrders")
    //
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }


}
