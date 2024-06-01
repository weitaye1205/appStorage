package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.model.Pin;
import java.util.List;

@Dao
public interface PinDao {

    @Insert
    void savePhoto(Pin pin);

    @Query("SELECT * FROM pins_table")
    List<Pin> getAllSavedPhotos();

    @Query("DELETE FROM pins_table")
    void clearSavedPhotos();

    @Query("DELETE FROM pins_table WHERE id=:id")
    void removeSavedPhotos(int id);
}
