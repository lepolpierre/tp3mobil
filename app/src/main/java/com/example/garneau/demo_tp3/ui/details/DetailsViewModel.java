package com.example.garneau.demo_tp3.ui.details;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.garneau.demo_tp3.data.LocationRoomDatabase;
import com.example.garneau.demo_tp3.model.Location;

public class DetailsViewModel extends AndroidViewModel {

    private LocationRoomDatabase mDb;
    // todo : déclaration correcte du point retourné


    // todo : constructeur
    public DetailsViewModel(Application application) {
        super(application);

    }

    // todo : 1 méthode pour retourner le point à id défini
    // accédé par le fragment
}