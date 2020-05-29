package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {

        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @GetMapping("/{managerId}/managerHotels")
    public ResponseVO retrieveManagerAllHotels(@PathVariable Integer managerId){
        // 根据managerId获得他管理的酒店
        return ResponseVO.buildSuccess(hotelService.retrieveManagerHotels(managerId));
    }

    @GetMapping("/search")
    public ResponseVO retrieveSearchedHotels(@RequestParam String region,
                                             @RequestParam String address,
                                             @RequestParam String name,
                                             @RequestParam String star,
                                             @RequestParam Integer rate0, @RequestParam Integer rate1){
        return ResponseVO.buildSuccess(hotelService.retrieveSearchedHotels(region,address,name,star,rate0,rate1));
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess();
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }


}
