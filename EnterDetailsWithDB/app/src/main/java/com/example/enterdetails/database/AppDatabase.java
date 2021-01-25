package com.example.enterdetails.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.enterdetails.dao.IUserDAO;

public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "userList";
    private static final String LOG_TAG = AppDatabase.class.getName();

    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;


    public static AppDatabase getInstance(Context context){
        if(sInstance==null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract IUserDAO iUserDAO();

}
