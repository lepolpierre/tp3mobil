package com.example.garneau.demo_tp3.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garneau.demo_tp3.MainActivity;
import com.example.garneau.demo_tp3.data.LocationDao;
import com.example.garneau.demo_tp3.data.LocationRoomDatabase;
import com.example.garneau.demo_tp3.databinding.FragmentMapsBinding;
import com.example.garneau.demo_tp3.databinding.MarkerLayoutBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.garneau.demo_tp3.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    public static final int LOCATION_PERMISSION_CODE = 1;
    private static final String TAG = "tag";
    private MapsViewModel mapsViewModel;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Location locationUser;
    private TextView id_distance;

    FragmentMapsBinding binding;

    private Boolean addLocation = false;
    private Boolean isMarkerOpen = false;

    private LatLng userLoc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // todo : instanciation correcte du binding

        // todo : instanciation correcte du ViewModel

        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // todo : get mapFragment

        Activity activity = getActivity();

        // todo : lancer la demande de permission pour géolocalisation

        // todo : clic sur fab
        // 1. activer la finction d'ajout de points
        // 2. régler la couleur du fab



    }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // todo : Vérification des permissions et positionnement si permission OK
            // normalement, ici, la demande de permission a déjà été traitée (onViewCreated)


            // todo : régler le comportement de l'observe sur la liste de points retourné par le view model
            // --> méthode onChanged de l'Observer : afficher les marqueurs sur la carte depuis la liste de tous les points
            // !!! Penser à passer le point courant au marqueur (setTag(Object)) à chaque ajout
            //     Ainsi le point est inclus dans le marqueur et accessible au getInfoContents(Marker)


            // todo : clic sur carte
            // 2 cas : Mode Ajout de Point et Mode normal


            // todo : placer la barre de zoom


            // Configuration du Layout pour les popups (InfoWindow)
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    // todo : clic sur marqueur
                    // 1. affichage de distance sur le fragment
                    // 2. Déployer le layout de la vue Marker et passer les valeurs du point cliqué afin d'affichage

                return null;
                }

            });

        }

//////////////////////////////////////////////// UTILS

        // geolocalisation : déja en place
        // vérifie la permission et positionne la carte si OK
        public void getLastLocation() {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            // Instance du service de localisation
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            // Définir la dernière localisation connue de l'utilisateur
            fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        locationUser = location;
                        userLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        Marker userLocation = mMap.addMarker(new MarkerOptions().position(userLoc).title("Je suis ici !"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 13));
                    }
                }
            });
        }

    // todo : si permission Ok
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // todo : positionnement si permission OK
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d("TAG", "onRequestPermissionsResult: ");
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
                    dialog.setTitle("Permission requise !");
                    dialog.setMessage("Cette permission est importante pour la géolocalisation...");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(
                                    new String[]{
                                            Manifest.permission.ACCESS_FINE_LOCATION
                                    }, LOCATION_PERMISSION_CODE);
                        }
                    });
                    dialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(requireActivity(), "Impossible de vous localiser", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
            }
        }
    }

    // Calcule la distance entre la position de l'utilisateur et le marker
    private void showDistance(Marker mMarkerA) {
        Location markerLoc = new Location("MarkerMessage");
        markerLoc.setLatitude(mMarkerA.getPosition().latitude);
        markerLoc.setLongitude(mMarkerA.getPosition().longitude);

        // obtention de la valeur de distance
        float distance = markerLoc.distanceTo(locationUser);
        // todo : affichage de la valeur de distance en kms
    }

    // todo : méthode privée de recherche d'adresse



    // Popup dialog sur clic carte pour ajout de point dans la BD
    private void actionClicCarte(com.example.garneau.demo_tp3.model.Location location) {
        View setView = getLayoutInflater().inflate(R.layout.set_location_dialog, null);

        // HACK : pour forcer 2 edittext sur le dialog
        // on définit dynamiquement un LinearLayout
        LinearLayout layout = new LinearLayout(setView.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        // Ajout EditText 1 et 2
        final EditText txtSetTv1 = new EditText(setView.getContext());
        txtSetTv1.setHint("Nom");

        layout.addView(txtSetTv1);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        // todo : passage au spinner des valeurs de catégorie possibles

        Spinner spinner = new Spinner(getActivity());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        layout.addView(spinner);


        // Création d'un objet permettant de gérer l'événement sur le bouton "OK" dans l'AlertDialog
        BtnSetHandler setHandler = new BtnSetHandler(txtSetTv1, spinner, location);
        //BtnSetHandler setHandler = new BtnSetHandler(txtNom, txtPrix);

        /** AlertDialog : c'est le popup
         *  Un objet AlertDialog permet la définition à la volée de 2 boutons : typiquement Ok et Cancel
         */
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Nouveau point")
                .setView(layout)
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Ok", setHandler)
                .show();
        //}
    }

    /**
     * Classe interne pour gérer le clic sur le bouton "OK" du pop-up de modification.
     * hérite de DialogInterface.OnClickListener
     */

    private class BtnSetHandler implements DialogInterface.OnClickListener {

        private EditText setTv1;
        private Spinner spinner;
        private com.example.garneau.demo_tp3.model.Location location;

        //public BtnSetHandler(EditText tv1_txtSet, EditText tv2_txtSet, com.example.garneau.demo_tp3.model.Location location) {
        public BtnSetHandler(EditText tv1_txtSet, Spinner spinner, com.example.garneau.demo_tp3.model.Location location) {

            this.setTv1= tv1_txtSet;
            this.spinner= spinner;
            this.location = location;
        }

        @Override
        public void onClick(DialogInterface p_dialog, int p_which) {
            // todo : ajout dans la BD du nouveau point
            // construction du point

            // todo : ajout dans la BD du nouveau point
            // ajout du point

        }
    }

    // méthode asynchrone d'insertion dans la BD
    // Rappel : les accès à la Bd doivent toujours se faire hors du fil d'exécution courant
    private static class InsertLocationDbAsync extends AsyncTask<Void, Void, Void> {
        private final MapsViewModel mapsViewModel;
        com.example.garneau.demo_tp3.model.Location location;

        public InsertLocationDbAsync(MapsViewModel mvm, com.example.garneau.demo_tp3.model.Location location) {
            this.mapsViewModel = mvm;
            this.location = location;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // todo : insertion d'un point dans la BD
            return null;
        }
    }
}
