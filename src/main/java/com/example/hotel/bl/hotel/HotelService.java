package com.example.hotel.bl.hotel;

import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface    HotelService {

    /**
     * 添加酒店
     * @param hotelVO
     * @throws
     */
    void addHotel(HotelVO hotelVO);


    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType,Integer rooms);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels();

    List<HotelVO> retrieveManagerHotels(Integer managerId);

    List<HotelVO> retrieveSearchedHotels(String region,String address,String name,String star, Integer rate0,Integer rate1);
    ResponseVO setHotelManager(Integer hotelid,int managerid);
    ResponseVO deleteHotel(Integer hotelId);
    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId,String roomType);

    void editHotel(HotelVO hotelVO);
    ResponseVO updateHotelImg(MultipartFile file, Integer hotelId);
    String getHotelImg(Integer hotelId);
    void updateTotalMoney(Integer hotelId, Double money);
    void updateRate(Integer hotelId, Integer count, Integer newRate);
}
