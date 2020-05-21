package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    List<HotelVO> selectAllHotel();

    HotelVO selectById(@Param("id") Integer id);

    List<HotelVO> selectSearchedHotel(@Param("region") String region,
                                      @Param("address") String address,
                                      @Param("name") String name,
                                      @Param("star") String star,
                                      @Param("rate0") Integer rate0,
                                      @Param("rate1") Integer rate1);
}
