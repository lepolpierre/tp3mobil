package com.example.garneau.demo_tp3.ui.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

import com.example.garneau.demo_tp3.data.LocationRoomDatabase;
import com.example.garneau.demo_tp3.model.Location;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    // déclaration  de la liste de points retournée
    private LiveData<List<Location>> allLocation;

    private LocationRoomDatabase mDb;

    // homeViewModel constructeur
    public HomeViewModel(Application application) {
        super(application);
        mDb = LocationRoomDatabase.getDatabase(application);
        allLocation = mDb.LocationDao().getAllLocations();
    }

    //  méthode pour retourner la liste de tous les points
    // accédé par le fragment
    public LiveData<List<Location>> getAllLocation () {return allLocation;}
}