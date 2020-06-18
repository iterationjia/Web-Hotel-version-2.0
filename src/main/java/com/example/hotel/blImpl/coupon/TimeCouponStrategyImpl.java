package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

//import java.io.Console;
//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {

    /**
     * 判断某个订单是否满足某种限时优惠策略
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        String time1 = "2020-06-11 00:00:00";
        String time2 = "2020-11-17 00:00:00";
        //LocalDateTime start1= LocalDateTime.of(2020,11,11);
        //LocalDateTime end1= LocalDateTime.of(2020,11,17);
        //String st=orderVO.getCheckInDate();
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //LocalDateTime start = LocalDateTime.parse(st, dtf1);
        //String ed=orderVO.getCheckOutDate();
        //DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //LocalDateTime end = LocalDateTime.parse(ed, dtf1);
        LocalDateTime begin = LocalDateTime.parse(time1, dtf1);
        LocalDateTime finish = LocalDateTime.parse(time2, dtf1);
        LocalDateTime now=LocalDateTime.now();
        if (coupon.getCouponType() == 4 && now.isAfter(begin) && now.isBefore(finish)){
            return true;
        }

        return false;
    }
}
