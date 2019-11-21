package com.example.opskrift.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.opskrift.Fragments.DrinkFavoriteFragment;
import com.example.opskrift.Fragments.DrinkListFragment;
import com.example.opskrift.Fragments.PlainSearchFragment;
import com.example.opskrift.Fragments.SettingsFragment;
import com.example.opskrift.R;
import com.example.opskrift.ViewModels.DrinkViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private FirebaseAuth mAuth;

    DrinkViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //UI
        Toolbar toolbar = findViewById(R.id.app_bar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //removes title in actionbar

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        //Set username in nav header
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_username);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            navUsername.setVisibility(View.VISIBLE);
            navUsername.setText("Welcome "+currentUser.getEmail()+"!");
        } else {
            navUsername.setVisibility(View.INVISIBLE);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Architecture
        viewModel = ViewModelProviders.of(this).get(DrinkViewModel.class);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new PlainSearchFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case(R.id.nav_home):
                if(viewModel.getCurrentDrinkSearch() == null || viewModel.getCurrentDrinkSearch() == "") {
                    selectedFragment = new PlainSearchFragment();
                }  else {
                    selectedFragment = new DrinkListFragment();
                }
                break;
            case(R.id.nav_favorites):
                selectedFragment = new DrinkFavoriteFragment();
                break;
            case(R.id.nav_settings):
                selectedFragment = new SettingsFragment();
                break;
            case(R.id.nav_signout):
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        if(selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
