package com.zjclugger.lib.persistence.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * @title <br>
 * Created by King.Zi on 2020/5/25.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Dao
public interface BaseDao<T> {
    /**
     * @param item
     * @Insert方法只接收一个参数, 返回一个long, 表示新插入项的rowId;
     * 当参数是数组或者集合时, 返回long[]或者List<Long>.
     * (@Update 可以将这个方法返回int值, 表示在数据库中被修改的行数)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertItem(T item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(T item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertItems(List<T> items);

    @Delete
    Single<Integer> deleteItem(T item);

    @Update
    Single<Integer> updateItem(T item);

}
