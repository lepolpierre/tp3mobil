package com.example.garneau.demo_tp3.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garneau.demo_tp3.R;
import com.example.garneau.demo_tp3.databinding.ActivityMainBinding;
import com.example.garneau.demo_tp3.databinding.FragmentHomeBinding;
import com.example.garneau.demo_tp3.model.Location;
import com.example.garneau.demo_tp3.ui.LocationAdapter;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private LocationAdapter locationAdapter;
    private LiveData<List<Location>> ListLocation;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // instanciation  du binding
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        // instanciation  du ViewModel
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        // recuperation de toutes les locations pour les mettres dans les live data
        ListLocation = homeViewModel.getAllLocation();

        View view = binding.getRoot();
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // déclaration et instanciation du RecyclerView
        binding.rvLocation.setLayoutManager(new LinearLayoutManager(getContext()));

        // instanciation de l'adapteur et passage de l'adapteur au RecyclerView
        locationAdapter = new LocationAdapter();
        binding.rvLocation.setAdapter(locationAdapter);

        ListLocation = homeViewModel.getAllLocation();
        ListLocation.observe(getActivity(),new Observer<List<Location>>() {
            @Override
            // Paramètre : dernière version observée de la liste de todos
            public void onChanged(List<Location> todos) {
                locationAdapter.setLocations(todos);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}