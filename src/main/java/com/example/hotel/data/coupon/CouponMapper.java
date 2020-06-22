package com.example.hotel.data.coupon;

import com.example.hotel.po.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CouponMapper {
    int insertCoupon(Coupon coupon);

    List<Coupon> selectByHotelId(Integer hotelId);

    List<Coupon> selectAvailableCoupon(Integer hotelId);

    List<Coupon> getCouponList();

    int deleteCoupon(@Param("couponid")int couponid);
}
