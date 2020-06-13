package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseVO retrieveAllHotels(@RequestParam Integer userid){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels(userid));
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
                                             @RequestParam Integer rate0, @RequestParam Integer rate1,
                                             @RequestParam Integer userid){
        return ResponseVO.buildSuccess(hotelService.retrieveSearchedHotels(region,address,name,star,rate0,rate1,userid));
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        return roomService.insertRoomInfo(hotelRoom);
//        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @PostMapping("/editHotel")
    public ResponseVO editHotel(@RequestBody HotelVO hotelVO){
        hotelService.editHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }
    @PostMapping("/{hotelId}/updateHotelImg")
    public ResponseVO updateHotelImg(@RequestParam("file") MultipartFile file, @PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(hotelService.updateHotelImg(file,hotelId));
    }
    @GetMapping("/{hotelId}/getHotelImg")
    public ResponseVO getHotelImg(@PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(hotelService.getHotelImg(hotelId));
    }
    @GetMapping("/editRoomPrice")
    public ResponseVO editRoomPrice(@RequestParam Integer roomId,@RequestParam Integer val){
        return ResponseVO.buildSuccess(roomService.editRoomPrice(roomId,val));
    }

    @GetMapping("/editRoomTotal")
    public ResponseVO editRoomTotal(@RequestParam Integer roomId,@RequestParam Integer val){
        return ResponseVO.buildSuccess(roomService.editRoomTotal(roomId,val));
    }

    @GetMapping("/editRoomCurNum")
    public ResponseVO editRoomCurNum(@RequestParam Integer roomId,@RequestParam Integer val){
        return ResponseVO.buildSuccess(roomService.editRoomCurNum(roomId,val));
    }

    @PostMapping("/{roomId}/deleteRoom")
    public ResponseVO deleteRoom(@PathVariable Integer roomId){
        return ResponseVO.buildSuccess(roomService.deleteRoom(roomId));
    }

    @GetMapping("/{hotelId}/comments")
    public ResponseVO retrieveComments(@PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(hotelService.getComments(hotelId));
    }
}
