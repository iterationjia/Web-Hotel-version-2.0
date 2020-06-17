package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    List<HotelVO> selectAllHotel();

    List<HotelVO> selectManagerHotels(@Param("managerId") Integer managerId);

    HotelVO selectById(@Param("id") Integer id);

    List<HotelVO> selectSearchedHotel(@Param("region") String region,
                                      @Param("address") String address,
                                      @Param("name") String name,
                                      @Param("star") String star,
                                      @Param("rate0") Integer rate0,
                                      @Param("rate1") Integer rate1);

    int deleteHotel(@Param("hotelid")Integer hotelid);

    int setHotelManager(@Param("hotelid")Integer hotelid,
                        @Param("managerid")int managerid);

    int editHotel(@Param("id") Integer id,
                  @Param("name") String name,
                  @Param("address") String address,
                  @Param("hotelStar") String hotelStar,
                  @Param("description") String description,
                  @Param("phoneNum") String phoneNum);

    double getCur_rate(@Param("hotelid")Integer hotelid);
    int updateRate(@Param("hotelid")Integer hotelid,@Param("tar_rate")double tar_rate);
    void updateTotalMoney(@Param("hotelid") Integer hotelid, @Param("money")double money);
}
