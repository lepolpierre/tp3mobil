package com.example.garneau.demo_tp3.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.net.wifi.aware.WifiAwareManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.renderscript.Sampler;
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
import com.example.garneau.demo_tp3.databinding.DetailsFragmentBinding;
import com.example.garneau.demo_tp3.databinding.FragmentMapsBinding;
import com.example.garneau.demo_tp3.databinding.MarkerLayoutBinding;
import com.example.garneau.demo_tp3.ui.details.DetailsViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.garneau.demo_tp3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    public static final int LOCATION_PERMISSION_CODE = 1;
    private static final String TAG = "tag";
    private MapsViewModel mapsViewModel;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Location locationUser;
    private TextView id_distance;
    private Boolean modeAjoutPoint = false;
    private MarkerLayoutBinding bindingMarker;
    private FragmentMapsBinding binding;
    private FragmentManager manager;

    Geocoder geocoder;
    List<Address> addresse;

    private com.example.garneau.demo_tp3.model.Location markerInfo;

    private Boolean addLocation = false;
    private Boolean isMarkerOpen = false;

    private LatLng userLoc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //instanciation du binding
        binding = FragmentMapsBinding.inflate(getLayoutInflater());

        // instanciation du view model
        mapsViewModel = new ViewModelProvider(requireActivity()).get(MapsViewModel.class);

        // instanciation de geocoder pour les adresses
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        // instanciation du childFragmentManager
        manager = getChildFragmentManager();

        // binding et renvoie de la vue
        View view = binding.getRoot();

        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // recuperation du fragment map
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Activity activity = getActivity();

        //demande de permition au lancement de l'application pour utiliser la localisation de l'appareil
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);
        }


        // gestion du fab pour le mod ajout
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // au click du fab il change d'etat entre mode ajout et mode "normal"
                modeAjoutPoint = !modeAjoutPoint;

                // si il est en mode ajout il sera vert pour indiquer que l'utilisateur
                // peut desormer ajout un point en cliquant sur la map
                if (modeAjoutPoint == true){
                    binding.fab.setBackgroundTintList(ColorStateList.valueOf(Color
                            .parseColor("#45F41E")));
                }else{
                    // si il n'est pas en mode ajout il sera rouge
                    binding.fab.setBackgroundTintList(ColorStateList.valueOf(Color
                            .parseColor("#EA0202")));
                }
            }
        });
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
            // recupperation de la carte Google map
            mMap = googleMap;

            // Placement de la carte sur l'utilisateur une fois que la carte est prete
            getLastLocation();


            // Recuperation de tout les marqueur pour les afficher sur la carte
            mapsViewModel.getAllLocation().observe(getViewLifecycleOwner(),
                    new Observer<List<com.example.garneau.demo_tp3.model.Location>>() {
                        @Override
                        public void onChanged(List<com.example.garneau.demo_tp3.model.Location> locations) {
                            // pour toutes les locations enregistrer on recupere les coordonnees et cree un marker a ces
                            // coordonnees avec la location enregistrer en tag
                            for (com.example.garneau.demo_tp3.model.Location location:locations) {
                                LatLng pointCoordonees = new LatLng(location.getLatitude(), location.getLongitude());

                                mMap.addMarker(new MarkerOptions().position(pointCoordonees).title(location.getNom())).setTag(location);
                            }

                        }
                    });




            // Gestion de l'ajout d'un point au click sur la map
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
            {
                @Override
                public void onMapClick(LatLng arg0)
                {
                    // si l'utilisateur est passer en mode ajout (fab en vert)
                    // le click sur la carte declanchera l'ajout d'un market et l'enregiistrement de la location
                    if (modeAjoutPoint)
                    {
                        // creation d'une location vide pour l'envoyer a la methode actionCliclCarte
                        // qui s'occupera d'afficher un dialog qui demandera a l'utilisateur les information
                        // concernant la location et s'occupera de "remplir" la location vide qui aura
                        // ete passer en parametre
                        com.example.garneau.demo_tp3.model.Location location =
                                new com.example.garneau.demo_tp3.model.Location("","","", arg0.latitude, arg0.longitude);
                        actionClicCarte(location);
                    }
                }
            });


            // Activation du controlle du zoom sur la map
            mMap.getUiSettings().setZoomControlsEnabled(true);


            // Configuration du Layout pour les popups (InfoWindow)
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }


                // affichage des donnees au click sur un marker existant
                @Override
                public View getInfoContents(Marker marker) {

                    List<Address> uneAddresse = null;

                    bindingMarker = MarkerLayoutBinding.inflate(getLayoutInflater());
//
                    TextView tvNom = bindingMarker.tvNomMap;
                    TextView tvAdress = bindingMarker.tvAdrMap;
                    TextView tvCat = bindingMarker.tvCatMap;
                    ImageView ivPhotoMap = bindingMarker.ivPhotoMap;

                    // recuperation de la location associer au marker
                    markerInfo= (com.example.garneau.demo_tp3.model.Location) marker.getTag();

                    // si la location associer au marker existe
                    if (markerInfo != null) {

                        // association des information de la location aux layout

                        tvCat.setText(markerInfo.getAdresse());
                        tvCat.setText(markerInfo.getCategorie());
                        tvNom.setText(markerInfo.getNom());

                        // associe une image en fonction de la categorie de la location
                        if (markerInfo.getCategorie().equals("parc")) {
                            ivPhotoMap.setImageResource(R.drawable.parc);
                        } else if (markerInfo.getCategorie().equals("maison")) {
                            ivPhotoMap.setImageResource(R.drawable.maison);
                        } else {
                            ivPhotoMap.setImageResource(R.drawable.foret);
                        }
                    }

                        // affiche la distance entre la ou se trouve l'utilisateur et le marker selectionner
                        showDistance(marker);
                        return bindingMarker.getRoot();
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
                    // Si la localisation existe cree uun marker a sont emplacement et positionne
                    // la camera en zoomant a cette endroit
                    if (location != null) {
                        locationUser = location;
                        userLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        Marker userLocation = mMap.addMarker(new MarkerOptions().position(userLoc).title("Je suis ici !"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 13));
                    }
                }
            });
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLastLocation();
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


        // affiche la distance en km entre l'utilisateur et le marker
        Toast.makeText(getContext(), "distance jusqu'a la location "+String.format("%.2f", distance/1000)+" km",
                Toast.LENGTH_SHORT).show();


    }

    // cherche l'adress aux coordonnees de la localisation en passant par l'api geocoder
    private List<Address> rechercheAdresse(double latitude, double longitude) throws IOException {
        addresse = geocoder.getFromLocation(latitude, longitude,1);
        return addresse;
    }



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

        // ajout des differentes categories dans le spinner
        spinnerArray.add("parc");
        spinnerArray.add("foret");
        spinnerArray.add("maison");

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
            // ajout du nouveau point dans la bd
            List<Address> uneAddresse = null;
            location.setNom(setTv1.getText().toString());
            location.setCategorie(spinner.getSelectedItem().toString());

            // si la localisation existe recupere l'adresse
            if (location != null) {
                try {
                     uneAddresse = rechercheAdresse(location.getLatitude(), location.getLongitude());
                    location.setAdresse(uneAddresse.get(0).getAddressLine(0).toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // ajout a la base de donnees de facons asynchrone
            new InsertLocationDbAsync(mapsViewModel, location).execute();

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
            // ajout de la location a la base de donnee en passant par le view model
            mapsViewModel.insert(location);
            return null;
        }
    }
}
