package com.example.myapplication.database;

import android.app.Application;

import com.example.myapplication.manager.RoomManager;
import com.example.myapplication.model.Pin;

import java.util.List;


public class PinRepository {

    private RoomManager dp;
    private PinDao pinDao;

    public PinRepository(Application application) {
        dp = RoomManager.getInstance(application);
        pinDao = dp.pinDao();
    }

    public void savePhoto(Pin pin) {
        pinDao.savePhoto(pin);
    }

    public List<Pin> getAllSavedPhotos() {
        return pinDao.getAllSavedPhotos();
    }

    public void clearSavedPhotos() {
        pinDao.clearSavedPhotos();
    }

    public void removeSavedPhotos(int id) {
        pinDao.removeSavedPhotos(id);
    }
}
