package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;
    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = orderService.getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    @Override
    public List<Order> getManagerOrders(Integer managerid){
        List<HotelVO> managerHotels = hotelService.retrieveManagerHotels(managerid);
        List<Order> managerOrders = new ArrayList<>();
        for (int i=0;i<managerHotels.size();i++){
            managerOrders.addAll(getHotelOrders(managerHotels.get(i).getId()));
        }
        return managerOrders;
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public ResponseVO annulOrder(int orderid) throws ParseException {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        Order orderannual = orderMapper.getOrderById(orderid);
        orderannual.setOrderState("已撤销");

        //数据库操作
        orderMapper.annulOrder(orderid);

        //如果撤销的订单距离最晚订单执行时间不足6个小时，撤销的同时扣除信用值，信用值为订单的（总价值*1/2）
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        String checkIn_date = orderannual.getCheckInDate();

        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        long from = simpleFormat.parse(curdate).getTime();
        long to = simpleFormat.parse(checkIn_date).getTime();
        int hours = (int)((to-from)/1000/60/60);

        User user = accountService.getUserInfo(orderannual.getUserId());
        //注意等号，比如15:59到21:01,不足6小时，hours=6
        if(hours<=6){
            double curcredit = user.getCredit()-orderannual.getPrice()/2;
            user.setCredit(curcredit);
            orderMapper.annualSubCredit(orderid,curcredit);
        }
        //roomnum置为负
        hotelService.updateRoomInfo(orderannual.getHotelId(),orderannual.getRoomType(),-1*orderannual.getRoomNum());
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO deleteOrder(OrderVO orderVO){
        double per = orderVO.getPrice();
        int orderid = orderVO.getId();
        Order orderdel = orderMapper.getOrderById(orderid);
        orderdel.setOrderState("已撤销");

        //数据库操作
        orderMapper.annulOrder(orderid);

        //恢复信用值
        User user = accountService.getUserInfo(orderdel.getUserId());
        double curcredit = user.getCredit()+orderdel.getPrice()/2*per;
        user.setCredit(curcredit);
        orderMapper.annualSubCredit(orderid,curcredit);
        return ResponseVO.buildSuccess(true);
    }

    public ResponseVO execOrder(int orderid) {
        //执行订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        Order orderexec = orderMapper.getOrderById(orderid);
        //执行时间设为createdate
        orderexec.setCreateDate(curdate);
        orderexec.setOrderState("已执行");
        /*
        //先不扣；如果撤销的订单距离最晚订单执行时间不足6个小时，撤销的同时扣除信用值，信用值为订单的（总价值*1/2）
        User user = accountService.getUserInfo(orderexec.getUserId());
        boolean subcredit = false;
        if(subcredit){
            user.setCredit(user.getCredit()-orderannual.getPrice()/2);
        }
        */
        //数据库操作
        orderMapper.execOrder(orderid);
        //roomnum置为负
        //hotelService.updateRoomInfo(orderexec.getHotelId(),orderexec.getRoomType(),-orderexec.getRoomNum());
        return ResponseVO.buildSuccess(true);
    }


//    @Override
//    public ResponseVO updateOrderComment(OrderVO orderVO){
//        Order order = new Order();
//        BeanUtils.copyProperties(orderVO,order);
//        orderMapper.updateOrderComment(order);
//        return ResponseVO.buildSuccess(true);
//    }

}
