package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController()
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/{orderid}/setOrderExcep")
    public ResponseVO setOrderExcep(@PathVariable int orderid){
        return orderService.setOrderExcep(orderid);
    }
    @GetMapping("/{orderid}/recoverOrder")
    public ResponseVO recoverOrder(@PathVariable int orderid){
        return orderService.recoverOrder(orderid);
    }
    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    // 获得酒店管理员所管理酒店的全部订单
    @GetMapping("/{managerid}/getManagerOrders")
    public ResponseVO retrieveManagerOrders(@PathVariable Integer managerid){
        return ResponseVO.buildSuccess(orderService.getManagerOrders(managerid));
    }

    // 获得某个用户的全部订单
    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable Integer userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    // 获得某个用户在某个酒店的订单
    @GetMapping("{userid}/{hotelid}/getUserHotelOrders")
    public ResponseVO retrieveUserHotelOrders(@PathVariable Integer userid, @PathVariable Integer hotelid){
        return ResponseVO.buildSuccess(orderService.getUserHotelOrders(userid,hotelid));
    }

    //用户撤销自己的订单
    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid) throws ParseException {
        return orderService.annulOrder(orderid);
    }

    @PostMapping("/checkOut")
    public ResponseVO checkOut(@RequestBody OrderVO orderVO){
        return orderService.checkOut(orderVO);
    }

    @GetMapping("/{orderid}/execOrder")
    public ResponseVO execOrder(@PathVariable int orderid){
        return orderService.execOrder(orderid);
    }

    //网站营销人员删除订单
    @PostMapping("/deleteOrder")
    public ResponseVO deleteOrder(@RequestBody OrderVO orderVO){
        return orderService.deleteOrder(orderVO);
    }

    @PostMapping("/updateOrderComment")
    public ResponseVO updateOrderComment(@RequestBody OrderVO orderVO){
        return orderService.updateOrderComment(orderVO);
    }

    @GetMapping("/{hotelId}/comments")
    public ResponseVO retrieveComments(@PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(orderService.getComments(hotelId));
    }
}
