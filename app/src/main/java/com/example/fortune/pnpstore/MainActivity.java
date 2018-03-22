package com.example.fortune.pnpstore;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fortune.pnpstore.Fragments.CartFragment;
import com.example.fortune.pnpstore.Fragments.DrinksFragment;
import com.example.fortune.pnpstore.Fragments.ElectronicsFragment;
import com.example.fortune.pnpstore.Fragments.FoodFragment;
import com.example.fortune.pnpstore.Fragments.HomeFragment;
import com.example.fortune.pnpstore.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PREFS = "prefFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(PREFS, 0);
        final String customer = preferences.getString("username", null);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUsername);
        username.setText("Welcome to PnP " + customer);
        ImageView profilePic = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgUser);
        profilePic.setImageResource(R.drawable.ic_profile_pic);


        FoodFragment homeFragment = new FoodFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, homeFragment,
                homeFragment.getTag()).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if(id == R.id.action_shoping_cart)
        {
            CartFragment cartFragment = new CartFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, cartFragment, cartFragment.getTag())
                    .addToBackStack("Cart").commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_drinks) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, homeFragment,
                    homeFragment.getTag()).commit();

        } else if (id == R.id.nav_foods) {
            FoodFragment foodFragment = new FoodFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, foodFragment,
                    foodFragment.getTag()).addToBackStack("Food").commit();

        } else if (id == R.id.nav_drinks) {
            DrinksFragment drinksFragment = new DrinksFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, drinksFragment,
                    drinksFragment.getTag()).addToBackStack("Drink").commit();

        } else if (id == R.id.nav_electronics) {
            ElectronicsFragment electronicsFragment = new ElectronicsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, electronicsFragment,
                    electronicsFragment.getTag()).addToBackStack("Electronics").commit();

        } else if (id == R.id.nav_deals) {
            Intent intent = new Intent(this, DealActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
