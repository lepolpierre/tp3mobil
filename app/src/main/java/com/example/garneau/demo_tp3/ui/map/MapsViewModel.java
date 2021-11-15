package com.example.garneau.demo_tp3.ui.map;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.example.garneau.demo_tp3.data.LocationRoomDatabase;
import com.example.garneau.demo_tp3.model.Location;

import java.util.List;

public class MapsViewModel extends AndroidViewModel {

    // todo : déclaration correcte de la liste de points retournée
    private LiveData<List<Location>> allLocation;

    private LocationRoomDatabase mDb;

    // todo : constructeur
    public MapsViewModel(Application application) {
        super(application);
        // todo : obtenir la liste de tous les points
        mDb = LocationRoomDatabase.getDatabase(application);
        allLocation = mDb.LocationDao().getAllLocations();
    }


    // todo : méthode pour obtenir depuis la BD la liste de tous les points
    public LiveData<List<Location>> getAllLocation () {return allLocation;}

    // todo : méthode pour insérer un point dans la BD
    public void insert (Location location) {mDb.LocationDao().insert(location);}




}