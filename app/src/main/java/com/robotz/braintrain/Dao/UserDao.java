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

    @Query("INSERT INTO user_table (fathers_first_name, mothers_maiden_name, date_of_birth, username) VALUES(:fathers_first_name, :mothers_maiden_name, :date_of_birth, :username)")
    long insert(String fathers_first_name, String mothers_maiden_name, String date_of_birth, String username);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM user_table where username = :username")
    User currentuserid(String username);

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    @RawQuery
    int checkpoint(SupportSQLiteQuery supportSQLiteQuery);

}
