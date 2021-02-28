package com.example.enterdetails.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.enterdetails.entities.User;

import java.util.List;

@Dao
public interface IUserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM USER ORDER BY ID")
    List<User> loadAllUser();

    @Update
    void updateUser(User user);

    @Delete
    void delete(User user);
}
