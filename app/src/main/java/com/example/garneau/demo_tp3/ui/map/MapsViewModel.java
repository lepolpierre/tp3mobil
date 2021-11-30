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

    // declaration de la liste de point retourner
    private LiveData<List<Location>> allLocation;

    private LocationRoomDatabase mDb;

    // constructeur du view model
    public MapsViewModel(Application application) {
        super(application);
        // obtention de tout les points comptenue dans la bd en passant par le dao
        mDb = LocationRoomDatabase.getDatabase(application);
        allLocation = mDb.LocationDao().getAllLocations();
    }

    // methoode pouor recuperer toutes les location
    public LiveData<List<Location>> getAllLocation () {return allLocation;}

    // methode pour inserer une location dans la base de donnee
    public void insert (Location location) {mDb.LocationDao().insert(location);}




}