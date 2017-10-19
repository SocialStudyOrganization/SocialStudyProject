package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ndlp.socialstudy.R;

/**
 * Activity to handle the fragments with bottomNavigationView and navigationDrawer
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  listener für click events in navigationDrawer -> calls method
        setNavigationViewListner();

        //  set toolbar and get a ActionBarDrawerToggle for onOptionsItemsSelected
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        //  set a drawerListener for onOptionsItemsSelected
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * BottomNavigationView
         */

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_view);

        //  listen for clicks and navigate between fragments
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = NewsFeedFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = MainMenuFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = CalendarFragment.newInstance();
                                break;
                        }

                        //  get an transaction when switching the fragment -> UE seems less buggy
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //  Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, NewsFeedFragment.newInstance());
        transaction.commit();

        //  Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);


    }

    //  listens for click events in the navigationDrawer
    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //  Öffnen des Navigation Drawers
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //  handles click events for the menu items -> starts activities
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_feedback_senden: {
                Intent intent = new Intent (MainActivity.this, FeedbackActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.nav_about_us: {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.nav_fehler_melden: {
                Intent intent = new Intent(MainActivity.this, FehlerMeldenAcivity.class);
                this.startActivity(intent);
                break;
            }

        }

        //  close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}

