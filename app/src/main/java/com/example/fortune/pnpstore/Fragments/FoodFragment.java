package com.example.fortune.pnpstore.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amigold.fundapter.FunDapter;
import com.example.fortune.pnpstore.Interfaces.Products;
import com.example.fortune.pnpstore.R;
import com.kosalgeek.genasync12.AsyncResponse;

import java.util.ArrayList;


public class FoodFragment extends Fragment implements AsyncResponse {
        public static final String PREFS = "prefFile";

        //ImageButton phones, microwave, remotes, appliances;

        final static String url = "https://leary-bricks.000webhostapp.com/FreshFood.php";

        private ArrayList<Products> productList;
        private ListView lv;
        FunDapter<Products> adapter;
        View view;



    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        //Create the necessary tabs needed
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.foodTab);
        tabLayout.addTab(tabLayout.newTab().setText("Product List"));

        tabLayout.setTabTextColors(Color.parseColor("#e3141e"), Color.parseColor("#e3141e"));

        //initialise the view pager
        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewPager_food);
        viewPager.setAdapter(new CustomAdapter(getChildFragmentManager(),
                tabLayout.getTabCount()));

        //Add listener to respond to touches and slides
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Override
    public void processFinish(String s) {

    }

    private class CustomAdapter extends FragmentStatePagerAdapter {
        int numberOfTabs;

        private CustomAdapter(FragmentManager fragmentManager, int numberOfTabs) {
            super( fragmentManager);
            this.numberOfTabs = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    FoodMainFragment main = new FoodMainFragment();
                    return main;
                case 1:
                    FoodSideFragment side = new FoodSideFragment();
                    return side;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return numberOfTabs;
        }
    }

}
