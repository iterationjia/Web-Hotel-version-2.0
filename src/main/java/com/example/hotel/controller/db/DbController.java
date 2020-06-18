package com.example.hotel.controller.db;

import com.example.hotel.bl.db.DbService;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db")
public class DbController {
    @Autowired
    DbService dbService;

    @PostMapping("/updateDatabase")
    public ResponseVO updateDatabase(){
        return ResponseVO.buildSuccess(dbService.updateDatabase());
    }

    @PostMapping("/updateDatabase2")
    public ResponseVO updateDatabase2(){
        return ResponseVO.buildSuccess(dbService.updateDatabase2());
    }
}
