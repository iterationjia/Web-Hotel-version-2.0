package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/hotelTargetMoney")
    public ResponseVO addHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {

        CouponVO couponVO = couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/time")
    public ResponseVO addTimeCoupon(@RequestBody TimeCouponVO timeCouponVO) {

        CouponVO couponVO = couponService.addTimeCoupon(timeCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/member")
    public ResponseVO addMemberCoupon(@RequestBody MemberCouponVO memberCouponVO) {

        CouponVO couponVO = couponService.addMemberCoupon(memberCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/manyHouses")
    public ResponseVO addManyHousesCoupon(@RequestBody ManyHousesCouponVO manyHousesCouponVO) {

        CouponVO couponVO = couponService.addManyHousesCoupon(manyHousesCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @GetMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut) {
        OrderVO requestOrderVO = new OrderVO();
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }

    @GetMapping("/getCouponList")
    public ResponseVO getCouponList(){
        return ResponseVO.buildSuccess(couponService.getCouponList());
    }

    @PostMapping("/{couponid}/deleteCoupon")
    public ResponseVO deleteUser(@PathVariable int couponid){
        return couponService.deleteCoupon(couponid);
    }
}


