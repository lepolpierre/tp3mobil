package com.example.garneau.demo_tp3.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.garneau.demo_tp3.databinding.DetailsFragmentBinding;
import com.example.garneau.demo_tp3.databinding.LocationRowBinding;
import com.example.garneau.demo_tp3.model.Location;

import com.example.garneau.demo_tp3.R;
import com.example.garneau.demo_tp3.ui.details.DetailsFragment;
import com.example.garneau.demo_tp3.ui.home.HomeFragment;
import com.example.garneau.demo_tp3.ui.home.HomeFragmentDirections;
import com.example.garneau.demo_tp3.ui.home.HomeViewModel;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.NoteHolder> {

    private List<Location> locationsList; // = new ArrayList<>();
    private Context context;
    LocationRowBinding binding;


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // todo : créer et instancier le layout
        binding = LocationRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        // todo : retourner un NoteHolder correctement constitué

        return new NoteHolder(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {


        // todo : récupérer le point
        Location current = locationsList.get(position);

        // todo : passer les bonnes valeurs aux éléments du holder
        holder.txtNom.setText(current.getNom());
        holder.txtAdress.setText(current.getAdresse());
        holder.txtCategorie.setText(current.getCategorie());


        // todo : image
        holder.img.setImageResource(R.drawable.pleinair);

        // todo : clic sur rangée
        // Navigation vers le fragment Détail avec Id du point détaillé
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = (NavDirections) HomeFragmentDirections.actionNavHomeToDetailsFragment(current.getId());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (locationsList != null)
            return locationsList.size();
        else return 0;

    }

    public void setLocations(List<Location> locations) {
        // todo : instancier la liste
        locationsList = locations;
        // todo : notifier l'adapteur
        notifyDataSetChanged();

    }


    // todo : classe interne NoteHolder
    // référence les éléments de la vue
    public class NoteHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView txtCategorie, txtNom, txtAdress;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            img = binding.ivLocCat;
            txtCategorie = binding.tvCategorie;
            txtNom = binding.tvNom;
            txtAdress = binding.tvAdresse;
        }
    }
}