package com.example.garneau.demo_tp3.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.garneau.demo_tp3.databinding.DetailsFragmentBinding;
import com.example.garneau.demo_tp3.databinding.FragmentMapsBinding;
import com.example.garneau.demo_tp3.ui.home.HomeViewModel;
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

    private DetailsFragmentBinding binding;

    private LiveData<Location> location;

    private GoogleMap mMap;

    private int id;

    private static LatLng CURRENTPOINT;


    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // instanciation  du binding
        binding = DetailsFragmentBinding.inflate(getLayoutInflater());

        //instanciation  du ViewModel
        detailsViewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);

        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id = DetailsFragmentArgs.fromBundle(getArguments()).getIdLocation();
        assert getArguments() != null;
        Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
        location = detailsViewModel.getLocation(id);

        detailsViewModel.getLocation(id).observe(getViewLifecycleOwner(),
                new Observer<Location>() {
                    @Override
                    public void onChanged(Location location) {
                        binding.tvNomDetails.setText(location.getNom());
                        binding.tvAdresseDetails.setText(location.getAdresse());
                        binding.tvCategorieDetails.setText(location.getCategorie());
                        if (location.getCategorie().equals("parc")) {
                            binding.ivLocationBottom.setImageResource(R.drawable.parc);
                        } else if (location.getCategorie().equals("maison")) {
                            binding.ivLocationBottom.setImageResource(R.drawable.maison);
                        } else {
                            binding.ivLocationBottom.setImageResource(R.drawable.foret);
                        }
                    }
                });

        // recuperation du fragement map
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        // recuperatiion de la map
        mMap = googleMap;

        detailsViewModel.getLocation(id).observe(getViewLifecycleOwner(),
                new Observer<Location>() {
                    @Override
                    public void onChanged(Location location) {
                        CURRENTPOINT = new LatLng(location.getLatitude(),location.getLongitude());

                        mMap.addMarker(new MarkerOptions().position(CURRENTPOINT).title(location.getNom())).setTag(location);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENTPOINT, 11));

                    }
                });

    }
}