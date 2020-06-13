package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.Hotel;
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
    private final static String CREDIT_LACK = "您的信用值不足";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    HotelMapper hotelMapper;
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

            if(user.getCredit()<0){
                return ResponseVO.buildFailure(CREDIT_LACK);
            }
            HotelVO hotel = hotelMapper.selectById(orderVO.getHotelId());
            hotel.setTotalmoney(hotel.getTotalMoney()+orderVO.getPrice());
            hotelMapper.setTotalMoney(hotel.getId(),hotel.getTotalMoney());
            //System.out.println(hotel.getId());
            user.setTotalmoney(user.getTotalmoney()+orderVO.getPrice());
            user.setLv((int) ((user.getTotalmoney()<=10000)?(user.getTotalmoney()/1000):(9+user.getTotalmoney()/10000)));
            accountMapper.setLv(user.getId(),user.getLv());
            accountMapper.setTotalMoney(user.getId(),user.getTotalmoney());
//            orderVO.setClientName(user.getUserName());
//            orderVO.setPhoneNumber(user.getPhoneNumber());
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
    public ResponseVO recoverOrder (int orderid){
        Order order = orderMapper.getOrderById(orderid);
        order.setOrderState("已执行");
        HotelVO hotel = hotelMapper.selectById(order.getHotelId());
        hotel.setTotalmoney(hotel.getTotalMoney()+order.getPrice());
        hotelMapper.setTotalMoney(hotel.getId(),hotel.getTotalMoney());
        orderMapper.execOrder(orderid);
        User user = accountService.getUserInfo(order.getUserId());

        //加上totalmoney和lv
        user.setTotalmoney(user.getTotalmoney()+order.getPrice());
        user.setLv((int) ((user.getTotalmoney()<=10000)?(user.getTotalmoney()/1000):(9+user.getTotalmoney()/10000)));
        accountMapper.setLv(user.getId(),user.getLv());
        accountMapper.setTotalMoney(user.getId(),user.getTotalmoney());
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO setOrderExcep (int orderid){
        Order order = orderMapper.getOrderById(orderid);
        order.setOrderState("异常");
        orderMapper.setOrderExcep(orderid);
        HotelVO hotel = hotelMapper.selectById(order.getHotelId());
        hotel.setTotalmoney(hotel.getTotalMoney()-order.getPrice());
        hotelMapper.setTotalMoney(hotel.getId(),hotel.getTotalMoney());
        User user = accountService.getUserInfo(order.getUserId());

        //减去totalmoney和lv
        user.setTotalmoney(user.getTotalmoney()-order.getPrice());
        user.setLv((int) ((user.getTotalmoney()<=10000)?(user.getTotalmoney()/1000):(9+user.getTotalmoney()/10000)));
        accountMapper.setLv(user.getId(),user.getLv());
        accountMapper.setTotalMoney(user.getId(),user.getTotalmoney());
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
        for (int i = 0; i < managerOrders.size(); i++) {
            Order order = managerOrders.get(i);
            User user = accountService.getUserInfo(order.getUserId());
            order.setUserLv(user.getLv());
        }
        return managerOrders;
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public List<Order> getUserHotelOrders(int userid, int hotelid) {
        return orderMapper.getUserHotelOrders(userid,hotelid);
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

        HotelVO hotel = hotelMapper.selectById(orderannual.getHotelId());
        hotel.setTotalmoney(hotel.getTotalMoney()+orderannual.getPrice());
        //减去totalmoney和lv
        user.setTotalmoney(user.getTotalmoney()-orderannual.getPrice());
        user.setLv((int) ((user.getTotalmoney()<=10000)?(user.getTotalmoney()/1000):(9+user.getTotalmoney()/10000)));
        accountMapper.setLv(user.getId(),user.getLv());
        accountMapper.setTotalMoney(user.getId(),user.getTotalmoney());

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

    @Override
    public ResponseVO checkOut(OrderVO orderVO) {
        int orderId = orderVO.getId();
        int hotelId = orderVO.getHotelId();
        int roomNum = orderVO.getRoomNum();
        String roomType = orderVO.getRoomType();
        orderMapper.checkOut(orderId);
        roomMapper.updateRoomInfo(hotelId,roomType,roomNum*(-1));
        // roomMapper.updateRoomInfo写好的是扣除多少房间，所以我直接乘负一，退房的就加多少房间
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO execOrder(int orderid) {
        //执行订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        Order orderexec = orderMapper.getOrderById(orderid);
        orderexec.setOrderState("已执行");
        //数据库操作
        orderMapper.execOrder(orderid);
        //roomnum置为负
        //hotelService.updateRoomInfo(orderexec.getHotelId(),orderexec.getRoomType(),-orderexec.getRoomNum());
        return ResponseVO.buildSuccess(true);
    }


    @Override
    public ResponseVO updateOrderComment(OrderVO orderVO){
        Order order = new Order();
        BeanUtils.copyProperties(orderVO,order);
        orderMapper.updateOrderComment(order.getId(),order.getStar(),order.getComment());
        return ResponseVO.buildSuccess(true);
    }

}
