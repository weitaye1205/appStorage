package com.example.myapplication.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pins_table")
public class Pin {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Photo photoItem;

    public Pin(int id, Photo photoItem) {
        this.id = id;
        this.photoItem = photoItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Photo getPhotoItem() {
        return photoItem;
    }

    public void setPhotoItem(Photo photoItem) {
        this.photoItem = photoItem;
    }
}
