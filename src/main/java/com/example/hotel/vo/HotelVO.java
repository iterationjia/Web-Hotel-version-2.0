package com.example.hotel.vo;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.User;

import java.util.List;

public class HotelVO {
    private Integer id;
    private String name;
    private String address;
    private String bizRegion;
    private String hotelStar;
    private Double rate;
    private String description;
    private String phoneNum;
    private Integer managerId;
    private Integer minPrice;
    private double totalmoney;
    private boolean scheduled;
    private String img;

    private List<RoomVO> rooms;
    public double getTotalMoney() {
        return totalmoney;
    }

    public void setTotalmoney(double totalmoney) {
        this.totalmoney = totalmoney;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBizRegion() {
        return bizRegion;
    }

    public void setBizRegion(String bizRegion) {
        this.bizRegion = bizRegion;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(String hotelStar) {
        this.hotelStar = hotelStar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<RoomVO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomVO> rooms) {
        this.rooms = rooms;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getMinPrice() { return minPrice; }

    public void setMinPrice(Integer minPrice) { this.minPrice = minPrice; }

    public boolean getScheduled() { return scheduled; }

    public void setScheduled(boolean scheduled) { this.scheduled = scheduled; }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
