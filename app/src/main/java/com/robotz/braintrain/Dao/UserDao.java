package com.robotz.braintrain.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.robotz.braintrain.Entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    Long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM user_table ORDER BY userId DESC LIMIT 1")
    List<User> getAllUsers();

    @RawQuery
    int checkpoint(SupportSQLiteQuery supportSQLiteQuery);


    /*@Query("SELECT * FROM user_table WHERE username = :username")
    User getUser(User username);*/

}
