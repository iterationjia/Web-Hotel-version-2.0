package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomService roomService;

    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if(manager == null ||
                !(manager.getUserType().equals(UserType.HotelManager)
                        ||(manager.getUserType().equals(UserType.Manager))))
        {
            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
        }
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
    public ResponseVO deleteHotel(Integer hotelid) {
        //删除酒店逻辑的具体实现（注意可能有和别的业务类之间的交互）
        //数据库操作
        hotelMapper.deleteHotel(hotelid);
        roomMapper.deleteHotelRooms(hotelid);
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

        return hotelMapper.selectAllHotel();
    }

    @Override
    public List<HotelVO> retrieveHotels(int userid) {
        List<HotelVO> allHotels =  hotelMapper.selectAllHotel();
        for (int i=0;i<allHotels.size();i++){
            HotelVO hotelVO = allHotels.get(i);
            Integer minPrice = roomMapper.getMinPrice(hotelVO.getId());
            int orderNum = orderMapper.getUserHotelOrderNum(userid, hotelVO.getId());
            if (orderNum==0){
                hotelVO.setScheduled(false);
            } else {
                hotelVO.setScheduled(true);
            }
            if (minPrice!=null){
                hotelVO.setMinPrice(roomMapper.getMinPrice(hotelVO.getId()));
            } else {
                hotelVO.setMinPrice(-1);
            }
            hotelVO.setImg(getHotelImg(hotelVO.getId()));
        }
        return allHotels;
    }

    @Override
    public List<HotelVO> retrieveManagerHotels(Integer managerId) {
        return hotelMapper.selectManagerHotels(managerId);
    }

    @Override
    public List<HotelVO> retrieveSearchedHotels(String region,String address,String name,String star, Integer rate0,Integer rate1,int userid) {
        List<HotelVO> searchedHotelsList = hotelMapper.selectSearchedHotel(region, address, name, star, rate0, rate1);
        for (int i=0;i<searchedHotelsList.size();i++){
            HotelVO hotelVO = searchedHotelsList.get(i);
            Integer minPrice = roomMapper.getMinPrice(hotelVO.getId());
            int orderNum = orderMapper.getUserHotelOrderNum(userid, hotelVO.getId());
            if (orderNum==0){
                hotelVO.setScheduled(false);
            } else {
                hotelVO.setScheduled(true);
            }
            if (minPrice!=null){
                hotelVO.setMinPrice(roomMapper.getMinPrice(hotelVO.getId()));
            } else {
                hotelVO.setMinPrice(-1);
            }
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

        return hotelVO;
    }
    @Override
    public ResponseVO setHotelManager(Integer hotelid,int managerid){
        hotelMapper.setHotelManager(hotelid,managerid);
        return ResponseVO.buildSuccess(true);
    };
    /**
     * @param hotelId
     * @return
     */

    @Override
    public List<CommentVO> getComments(Integer hotelId) {
        List<Order> hotelOrders = orderMapper.getHotelOrders(hotelId);
        List<CommentVO> comments = new ArrayList<CommentVO>();
        for (int i = 0; i<hotelOrders.size(); i++){
            Order order = hotelOrders.get(i);
            if ((order.getStar()!=null)&&(order.getComment()!=null)){
                User user = accountService.getUserInfo(order.getUserId());
                CommentVO comment = new CommentVO();
                comment.setAuthor(user.getUserName());
//            comment.setAvatar();
                comment.setComment(order.getComment());
                comment.setStar(order.getStar());
                comment.setDate(order.getCheckOutDate());
                comments.add(comment);
            }
        }
        return comments;
    }

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

}
