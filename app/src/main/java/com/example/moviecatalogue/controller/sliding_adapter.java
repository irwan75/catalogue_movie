package com.example.moviecatalogue.controller;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moviecatalogue.view.fragment_now_playing;
import com.example.moviecatalogue.view.fragment_upcoming;

public class sliding_adapter extends FragmentPagerAdapter {

    int numberTabs;

    public sliding_adapter(FragmentManager fm, int behavior) {
        super(fm);
        this.numberTabs = behavior;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new fragment_now_playing();
            case 1:
                return new fragment_upcoming();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
