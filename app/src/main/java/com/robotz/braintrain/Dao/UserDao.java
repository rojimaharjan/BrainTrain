package com.robotz.braintrain.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    /*@Query("SELECT * FROM user_table WHERE username = :username")
    User getUser(User username);*/

}
