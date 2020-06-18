package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    ResponseVO addOrder(OrderVO orderVO);

    ResponseVO setOrderExcep(int orderid);

    ResponseVO recoverOrder(int orderid);

    List<Order> getAllOrders();

    List<Order> getManagerOrders(Integer managerid);

    List<Order> getUserOrders(int userid);

    List<Order> getUserHotelOrders(int userid, int hotelid);

    List<Order> getHotelOrders(int hotelId);

    ResponseVO execOrder(int orderid);

    ResponseVO checkOut(OrderVO orderVO);

    ResponseVO annulOrder(int orderid) throws ParseException;

    ResponseVO deleteOrder(OrderVO orderVO);

    ResponseVO updateOrderComment(OrderVO orderVO);

    List<CommentVO> getComments(Integer hotelId);
}
