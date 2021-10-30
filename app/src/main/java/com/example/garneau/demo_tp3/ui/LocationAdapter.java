package com.example.garneau.demo_tp3.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.garneau.demo_tp3.model.Location;

import com.example.garneau.demo_tp3.R;
import com.example.garneau.demo_tp3.ui.home.HomeFragment;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.NoteHolder> {
    private List<Location> locations = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // todo : créer et instancier le layout

        context = parent.getContext();
        // todo : retourner un NoteHolder correctement constitué

    return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        // todo : récupérer le point

        // todo : passer les bonnes valeurs aux éléments du holder

        // todo : image


        // todo : clic sur rangée
        // Navigation vers le fragment Détail avec Id du point détaillé

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setLocations(List<Location> locations) {
        // todo : instancier la liste

        // todo : notifier l'adapteur
    }

    // todo : classe interne NoteHolder
    // référence les éléments de la vue
    class NoteHolder extends RecyclerView.ViewHolder {

        public NoteHolder(View itemView) {
            super(itemView);


        }
    }
}