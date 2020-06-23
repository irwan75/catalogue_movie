package com.example.moviecatalogue;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.moviecatalogue.controller.fragment_controller;
import com.example.moviecatalogue.view.*;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {

    public static String API_KEY = "ad5f8b8c7050cd1ce18f2e08959a6dab";

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle abdt;
    NavigationView nav_view;

    Toolbar mToolbar;

    fragment_controller fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_menu);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        abdt = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.Open, R.string.Close);

//        abdt.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(abdt);
//        abdt.syncState();

        fm = new fragment_controller(getSupportFragmentManager());
        fm.setFragment(new fragment_home());

        nav_view = findViewById(R.id.nav_view);
//        mToolbar.setNavigationOnClickListener(nav_selected);
        nav_view.setNavigationItemSelectedListener(nav_selected);

    }

    private NavigationView.OnNavigationItemSelectedListener nav_selected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.it_home :
                    fm.setFragment(new fragment_home());
                    break;
                case R.id.it_search:
                    fm.setFragment(new fragment_search());
                    break;
                case R.id.it_favourite:
                    fm.setFragment(new fragment_favourite());
                    break;
                case R.id.it_setting:
                    fm.setFragment(new fragment_setting());
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

}
