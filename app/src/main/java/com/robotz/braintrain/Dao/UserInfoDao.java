package com.robotz.braintrain.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.robotz.braintrain.Entity.UserInfo;

import java.util.List;

@Dao
public interface UserInfoDao {

    @Insert
    void insert(UserInfo userInfo);

    @Update
    void update(UserInfo userInfo);

    @Delete
    void delete(UserInfo userInfo);

    @Query("DELETE FROM userInfo_table")
    void deleteAllUserInfos();

    @Query("SELECT * FROM userInfo_table WHERE id = 'id'")
    LiveData<List<UserInfo>> getAllUserInfos();

}
