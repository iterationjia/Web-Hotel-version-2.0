package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getUserOrders(@Param("userid") int userid);

    List<Order> getHotelOrders(@Param("hotelId") int hotelId);

    List<Order> getUserHotelOrders(@Param("userid") int userid, @Param("hotelid") int hotelid);

    int annulOrder(@Param("orderid") int orderid);
//
    int execOrder(@Param("orderid") int orderid);
    int setOrderExcep(@Param("orderid") int orderid);
    void checkOut(@Param("orderid") int orderid);
    int getUserHotelOrderNum(@Param("userid") int userid,@Param("hotelid") int hotelid);
//
    Order getOrderById(@Param("orderid") int orderid);

    int annualSubCredit(@Param("orderid") int orderid ,@Param("credit") double credit);
    int updateOrderComment(@Param("orderid") int orderid , @Param("star") int star,@Param("comment") String comment);
    int getCommentStar(@Param("hotelid") int hotelid, @Param("star") int star);
    int getCommentNum(@Param("hotelid") int hotelid);
}
