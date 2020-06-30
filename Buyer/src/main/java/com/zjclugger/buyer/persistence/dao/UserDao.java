package com.zjclugger.buyer.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.zjclugger.lib.persistence.dao.BaseDao;
import com.zjclugger.buyer.persistence.entity.User;

import java.util.List;

/**
 * @title <br>
 * Created by King.Zi on 2020/5/25.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Dao
public interface UserDao extends BaseDao<User> {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getUsers();//获取user集合

    @Query("SELECT * FROM user WHERE id=:id")
    LiveData<User> getUserById(int id);//通过id获取user
}