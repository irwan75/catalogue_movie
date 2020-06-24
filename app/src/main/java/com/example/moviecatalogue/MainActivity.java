package com.example.moviecatalogue;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.moviecatalogue.controller.fragment_controller;
import com.example.moviecatalogue.view.*;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity  {

    public static String API_KEY = "ad5f8b8c7050cd1ce18f2e08959a6dab";
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

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
        }else if (back_pressed+TIME_DELAY > System.currentTimeMillis()){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Tekan back lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

}
