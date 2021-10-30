package com.example.garneau.demo_tp3.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.garneau.demo_tp3.databinding.DetailsFragmentBinding;
import com.example.garneau.demo_tp3.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.garneau.demo_tp3.MainActivity;
import com.example.garneau.demo_tp3.R;
import com.example.garneau.demo_tp3.data.AppExecutors;
import com.example.garneau.demo_tp3.model.Location;

public class DetailsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "tag";
    private DetailsViewModel detailsViewModel;
    private int id;

    private DetailsFragmentBinding binding;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // todo : instanciation correcte du binding
        //binding = DetailsFragmentBinding.inflate(getLayoutInflater());
        // todo : instanciation correcte du ViewModel
        //detailsViewModel = new DetailsViewModel(detailsViewModel.getApplication());
    return null;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // todo : titre de fragment dans l'ActionBar


        // todo : instanciation correcte de l'id d'élément détaillé
        assert getArguments() != null;

        // todo : régler le comportement de l'observe sur le point retourné par le view model
        // --> méthode onChanged de l'Observer : passer les valeurs du point courant à la View

        // todo : get mapFragment


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // todo : régler le comportement de l'observe sur élément Location retourné par le view model
        // --> méthode onChanged de l'Observer : affichage correct du marqueur pour ce point

    }
}