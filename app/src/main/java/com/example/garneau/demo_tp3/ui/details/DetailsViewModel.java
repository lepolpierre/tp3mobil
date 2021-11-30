package com.example.garneau.demo_tp3.ui.details;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.garneau.demo_tp3.data.LocationRoomDatabase;
import com.example.garneau.demo_tp3.model.Location;

import java.util.List;

public class DetailsViewModel extends AndroidViewModel {

    private LocationRoomDatabase mDb;
    // déclaration  du point retourné
    private LiveData<Location> location;


    // DetailsViewModel constructeur
    public DetailsViewModel(Application application) {
        super(application);
        mDb = LocationRoomDatabase.getDatabase(application);


    }


    // méthode pour retourner le point avec id en parametre
    public LiveData<Location> getLocation (int id) {
        return mDb.LocationDao().getLocation(id);
    }
    // accédé par le fragment
}