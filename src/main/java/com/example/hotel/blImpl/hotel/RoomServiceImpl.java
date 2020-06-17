package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public ResponseVO insertRoomInfo(HotelRoom hotelRoom) {
        String type = hotelRoom.getRoomType().toString();
        if (type.equals("大床房")){
            type = "BigBed";
        } else if (type.equals("双床房")){
            type = "DoubleBed";
        } else {
            type = "Family";
        }
        try {
            int rNum = getRoomCurNum(hotelRoom.getHotelId(), type);
            return ResponseVO.buildFailure("已存在此类房间");
        } catch (Exception e){
            roomMapper.insertRoom(hotelRoom);
            return ResponseVO.buildSuccess(true);
        }
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomMapper.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public int editRoomPrice(Integer roomId, Integer val) {
        return roomMapper.editRoomPrice(roomId,val);
    }

    @Override
    public int editRoomTotal(Integer roomId, Integer val) {
        return roomMapper.editRoomTotal(roomId,val);
    }

    @Override
    public int editRoomCurNum(Integer roomId, Integer val) {
        return roomMapper.editRoomCurNum(roomId,val);
    }

    @Override
    public int deleteRoom(Integer roomId) {
        return roomMapper.deleteRoom(roomId);
    }

    @Override
    public int getMinPrice(Integer hotelId) {
        return roomMapper.getMinPrice(hotelId);
    }
}
