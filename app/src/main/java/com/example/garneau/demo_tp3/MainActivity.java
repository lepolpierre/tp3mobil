package com.example.garneau.demo_tp3;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
//import android.widget.Toolbar;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.garneau.demo_tp3.databinding.AppBarMainBinding;
import com.example.garneau.demo_tp3.databinding.ContentMainBinding;
import com.example.garneau.demo_tp3.databinding.FragmentHomeBinding;
import com.example.garneau.demo_tp3.model.Location;
import com.example.garneau.demo_tp3.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garneau.demo_tp3.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;
    private Toolbar app_bar_main;
    private HomeFragment homeFragment;
    private FragmentHomeBinding fragmentHomeBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // todo : bonne instanciation du binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // todo : ActionBar --> toolBar
        setSupportActionBar(binding.toolbar.tbar);

        // todo : instanciation des éléments du layout


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        // todo : bonne instanciation du AppBarConfiguration.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_map, R.id.detailsFragment)
                .setOpenableLayout(drawer)
                .build();

        // todo : bonne déclaration et instanciation du NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        // todo : passage du navController à l'appBar, puis à la View parente

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // todo : déclaration et instanciation du NavController

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}