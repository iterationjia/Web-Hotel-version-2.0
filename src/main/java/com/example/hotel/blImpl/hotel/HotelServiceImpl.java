package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private RoomService roomService;

    @Override
    public void addHotel(HotelVO hotelVO) {
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        if(hotelVO.getManagerId()!=0) {
            hotel.setManagerId(hotelVO.getManagerId());
        }
        hotel.setRate(hotelVO.getRate());
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public ResponseVO deleteHotel(Integer hotelid) {
        //删除酒店逻辑的具体实现（注意可能有和别的业务类之间的交互）
        //数据库操作
        hotelMapper.deleteHotel(hotelid);
        List<HotelRoom> hotelRooms =  roomService.retrieveHotelRoomInfo(hotelid);
        for (HotelRoom hotelRoom: hotelRooms){
            roomService.deleteRoom(hotelRoom.getId());
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomService.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public List<HotelVO> retrieveHotels() {
        // userid用来判断酒店是否预定过
        List<HotelVO> allHotels =  hotelMapper.selectAllHotel();
        for (int i=0;i<allHotels.size();i++){
            HotelVO hotelVO = allHotels.get(i);
            hotelVO.setMinPrice(roomService.getMinPrice(hotelVO.getId()));
            hotelVO.setImg(getHotelImg(hotelVO.getId()));
        }
        return allHotels;
    }

    @Override
    public List<HotelVO> retrieveManagerHotels(Integer managerId) {
        return hotelMapper.selectManagerHotels(managerId);
    }

    @Override
    public List<HotelVO> retrieveSearchedHotels(String region,String address,String name,String star, Integer rate0,Integer rate1) {
        List<HotelVO> searchedHotelsList = hotelMapper.selectSearchedHotel(region, address, name, star, rate0, rate1);
        for (int i=0;i<searchedHotelsList.size();i++){
            HotelVO hotelVO = searchedHotelsList.get(i);
            hotelVO.setMinPrice(roomService.getMinPrice(hotelVO.getId()));
            hotelVO.setImg(getHotelImg(hotelVO.getId()));
        }
        return searchedHotelsList;
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);
        hotelVO.setImg(getHotelImg(hotelId));
        return hotelVO;
    }
    @Override
    public ResponseVO setHotelManager(Integer hotelid,int managerid){
        hotelMapper.setHotelManager(hotelid,managerid);
        return ResponseVO.buildSuccess(true);
    };

    @Override
    public void editHotel(HotelVO hotelVO) {
        hotelMapper.editHotel(hotelVO.getId(),hotelVO.getName(),hotelVO.getAddress(),hotelVO.getHotelStar(),hotelVO.getDescription(),hotelVO.getPhoneNum());
    }

    @Override
    public ResponseVO updateHotelImg(MultipartFile file, Integer hotelId) {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf('.')+1);
        String newFileName = String.valueOf(hotelId) + ".jpg";
        String dirPath = '.'+ File.separator+"savedimgs"+File.separator+"hotel";
        String filePath = dirPath + File.separator + newFileName;
        File imgPath = new File(dirPath);
        if (!imgPath.exists()){
            imgPath.mkdirs();
        }
        File localFile = new File(filePath);
        try {
            file.transferTo(localFile.getAbsoluteFile());
            return ResponseVO.buildSuccess(true);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getHotelImg(Integer hotelId) {
        String newFileName = String.valueOf(hotelId) + ".jpg";
        String dirPath = '.'+ File.separator+"savedimgs"+File.separator+"hotel";
        String filePath = dirPath + File.separator + newFileName;
        InputStream in;
        byte[] data;
        try {
            in = new FileInputStream(new File(filePath));
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e){
            return null;
        } catch (IOException e){
            return null;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

    @Override
    public void updateTotalMoney(Integer hotelId, Double money) {
        hotelMapper.updateTotalMoney(hotelId,money);
    }

    @Override
    public void updateRate(Integer hotelId, Integer count, Integer newRate) {
        double cur_rate = hotelMapper.getCur_rate(hotelId);
        double tar_rate = (cur_rate*count+newRate)/(count+1);
        tar_rate=(double)Math.round(tar_rate*10)/10;
        hotelMapper.updateRate(hotelId,tar_rate);
    }
}
