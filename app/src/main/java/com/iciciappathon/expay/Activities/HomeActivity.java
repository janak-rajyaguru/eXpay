package com.iciciappathon.expay.Activities;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import com.iciciappathon.expay.Fragments.HomeFragment;
import com.iciciappathon.expay.Fragments.NavigationFragment;
import com.iciciappathon.expay.R;

public class HomeActivity extends AppCompatActivity {
    HomeFragment mHomeFragment;
    NavigationFragment mNavFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = (DrawerLayout) findViewById(R.id.poi_homedrawerLayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        mActionBar =  getActionBar();
        if(mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(getString(R.string.app_name));
        }
        mHomeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.gridfragment_container,mHomeFragment).commit();


        mNavFragment = new NavigationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.homenavfragment_container, mNavFragment).commit();


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public  void  closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
