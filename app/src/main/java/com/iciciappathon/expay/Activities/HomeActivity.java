package com.iciciappathon.expay.Activities;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.iciciappathon.expay.Adapters.ViewPagerAdapter;
import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.Fragments.GroupFragment;
import com.iciciappathon.expay.Fragments.NavigationFragment;
import com.iciciappathon.expay.Fragments.TransactionFragment;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.R;

public class HomeActivity extends AppCompatActivity {
    NavigationFragment mNavFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar appToolBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseHandler databaseHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addDataToDatabase();

        setupToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.homedrawerLayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, appToolBar , R.string.open, R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        mNavFragment = new NavigationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.homenavfragment_container, mNavFragment).commit();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GroupFragment(), "Groups");
        adapter.addFragment(new TransactionFragment(), "Transaction");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setupToolbar() {
        appToolBar = (Toolbar)findViewById(R.id.toolbar);
        if(appToolBar != null) {
            setSupportActionBar(appToolBar);
            appToolBar.setTitle(getString(R.string.app_name));
            appToolBar.setTitleTextColor(getColor(R.color.white));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public  void  closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void addDataToDatabase() {

        databaseHandler.addGroup(new Group("Trip"));
        databaseHandler.addGroup(new Group("Apartment"));
        databaseHandler.addGroup(new Group("Kirana"));

        databaseHandler.addMember(new GroupMemberListItem("Heerain","heerain@icici","1"));
        databaseHandler.addMember(new GroupMemberListItem("Heerain","heerain@hdfc","2"));
        databaseHandler.addMember(new GroupMemberListItem("Heerain","heerain@axis","3"));
        databaseHandler.addMember(new GroupMemberListItem("Nitesh","nitesh@icici","1"));
        databaseHandler.addMember(new GroupMemberListItem("Nitesh","nitesh@hdfc","2"));
        databaseHandler.addMember(new GroupMemberListItem("Nitesh","nitesh@axis","3"));
        databaseHandler.addMember(new GroupMemberListItem("Janak","janak@icici","1"));
        databaseHandler.addMember(new GroupMemberListItem("Janak","janak@hdfc","2"));
        databaseHandler.addMember(new GroupMemberListItem("Janak","janak@axis","3"));
    }
}
