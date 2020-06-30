package com.zjclugger.buyer.persistence.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zjclugger.lib.persistence.DBTypeConverters;
import com.zjclugger.buyer.persistence.dao.ShopcartDao;
import com.zjclugger.buyer.persistence.dao.UserDao;
import com.zjclugger.buyer.persistence.entity.Shopcart;
import com.zjclugger.buyer.persistence.entity.User;
import com.zjclugger.lib.base.BaseApplication;

/**
 * @title <br>
 * Created by King.Zi on 2020/5/25.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Database(entities = {User.class, Shopcart.class}, version = 3, exportSchema = false)
@TypeConverters({DBTypeConverters.class})
public abstract class BuyerDatabase extends RoomDatabase {
    //exportSchemaF编译时，是否将数据库的模式信息导出到JSON文件中
    private static final String DATABASE_NAME = "buyer_db";
    private static BuyerDatabase Instance;

    public abstract UserDao getUserDao();

    public abstract ShopcartDao getShopCartDao();

    public static BuyerDatabase getInstance() {
        if (Instance == null) {
            synchronized (BuyerDatabase.class) {
                if (Instance == null) {
                    init();
                }
            }
        }
        return Instance;
    }

    public static void init() {
        Instance = Room.databaseBuilder(BaseApplication.getInstance(), BuyerDatabase.class,
                DATABASE_NAME)
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build();
        //allowMainThreadQueries    //指定，可以在主线程中运行
        //.fallbackToDestructiveMigration() //清除原来数据不必保留
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };
}