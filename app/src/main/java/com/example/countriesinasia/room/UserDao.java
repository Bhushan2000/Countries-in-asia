package com.example.countriesinasia.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    // The conflict strategy defines what happens,
    // if there is an existing entry.
    // The default action is ABORT.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);


    @Query("Select * from country_database")
    List<User> getCountries();


    // Simple query that does not take parameters and returns nothing.
    @Query("Delete from country_database")
    void deleteCountry();
}