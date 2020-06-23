package com.example.moviecatalogue.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.controller.sliding_adapter;
import com.google.android.material.tabs.TabLayout;

public class fragment_home extends Fragment {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    sliding_adapter mSliding_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTabLayout = view.findViewById(R.id.tab_slider);

        mViewPager = view.findViewById(R.id.view_pager_sliding);

        mSliding_adapter = new sliding_adapter(getActivity().getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mSliding_adapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0){
                    mSliding_adapter.notifyDataSetChanged();
                }else if (tab.getPosition()==1){
                    mSliding_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

}
