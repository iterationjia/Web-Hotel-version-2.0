package com.example.hotel.data.db;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface DbMapper {
    void createCommentTable();
}
