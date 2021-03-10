package com.example.countriesinasia.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class},version = 1)

public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();

//    private static UserDatabase INSTANCE;
//
//    static UserDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (UserDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            UserDatabase.class, "country_database")
//                            // Wipes and rebuilds instead of migrating
//                            // if no Migration object.
//                            .fallbackToDestructiveMigration()
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
