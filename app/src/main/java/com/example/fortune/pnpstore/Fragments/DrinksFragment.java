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

import com.example.fortune.pnpstore.R;


public class DrinksFragment extends Fragment {


    public DrinksFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.drinkTab);
        tabLayout.addTab(tabLayout.newTab().setText("Alcohol"));
        tabLayout.addTab(tabLayout.newTab().setText("Soft Drink"));
        tabLayout.addTab(tabLayout.newTab().setText("Milk Shake"));
        tabLayout.setTabTextColors(Color.parseColor("#ff5722"), Color.parseColor("#ff8a65"));

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager_drink);
        viewPager.setAdapter(new CustomAdapter(getChildFragmentManager(),
                tabLayout.getTabCount()));

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

    private class CustomAdapter extends FragmentStatePagerAdapter {
        int numberOfTabs;

        public CustomAdapter(FragmentManager fragmentManager, int numberOfTabs) {
            super( fragmentManager);
            this.numberOfTabs = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    AlcoholFragment alcohol = new AlcoholFragment();
                    return alcohol;
                case 1:
                    SoftDrinkFragment softDrink = new SoftDrinkFragment();
                    return softDrink;
                case 2:
                    MilkShakeFragment milkShake = new MilkShakeFragment();
                    return milkShake;
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
