package com.example.hotel.blImpl.db;

import com.example.hotel.bl.db.DbService;
import com.example.hotel.data.db.DbMapper;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbServiceImpl implements DbService {
    @Autowired
    private DbMapper dbMapper;

    @Override
    public int updateDatabase() {
        dbMapper.addOrderListStarColumn();
        dbMapper.addOrderListCommentColumn();
        dbMapper.addUserLvColumn();
        dbMapper.addUserTotalMoneyColumn();
        dbMapper.insertSuperUsers();
        dbMapper.changeManagerId();
        int lv = dbMapper.testLv();
        if (lv==0){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int updateDatabase2() {
        try{
            dbMapper.addHotelTotalMoneyColumn();
            dbMapper.addUserAvatarColumn();
            return 1;
        } catch (Exception e){
            return 0;
        }
    }
}
