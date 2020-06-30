package com.zjclugger.buyer.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.zjclugger.lib.persistence.dao.BaseDao;
import com.zjclugger.buyer.persistence.entity.Shopcart;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @title <br>
 * Created by King.Zi on 2020/5/25.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Dao
public interface ShopcartDao extends BaseDao<Shopcart> {
    @Query("SELECT * FROM shopcart")
    LiveData<List<Shopcart>> getShopCart();

    @Query("SELECT * FROM shopcart")
    Flowable<List<Shopcart>> getShopCartList();

    @Query("SELECT * FROM shopcart WHERE id=:id")
    LiveData<Shopcart> getShopCartById(int id);
}