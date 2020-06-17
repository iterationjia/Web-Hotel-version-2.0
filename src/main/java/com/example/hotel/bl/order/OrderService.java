package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);
    ResponseVO setOrderExcep(int orderid);
    ResponseVO recoverOrder(int orderid);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    List<Order> getManagerOrders(Integer managerid);

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);
    List<Order> getUserHotelOrders(int userid, int hotelid);
    List<Order> getHotelOrders(int hotelId);
    /**
     * 撤销订单
     * @param orderid
     * @return
     */

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO execOrder(int orderid);
    ResponseVO checkOut(OrderVO orderVO);

    ResponseVO annulOrder(int orderid) throws ParseException;

    ResponseVO deleteOrder(OrderVO orderVO);

    ResponseVO updateOrderComment(OrderVO orderVO);
}
