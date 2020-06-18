package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;
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
        String time1 = "2020-11-11";
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(time1, dtf1);
        String time2 = "2020-11-17";
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime end = LocalDateTime.parse(time2, dtf2);
        if (coupon.getCouponType() == 4 && coupon.getStartTime().isAfter(start) && coupon.getEndTime().isBefore(end)){
            return true;
        }

        return false;
    }
}
