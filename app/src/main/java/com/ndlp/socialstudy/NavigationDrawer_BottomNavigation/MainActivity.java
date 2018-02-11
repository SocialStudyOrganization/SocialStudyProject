package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.NewsFeed.NewsFeedFragment;
import com.ndlp.socialstudy.NewsFeed.NotificationFragment;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Stundenplan.CalendarFragment;

/**
 * Activity to handle the fragments with bottomNavigationView and navigationDrawer
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    boolean doubleBackToExitPressedOnce = false;

    BottomNavigationView topnavigationview;
    Fragment selectedFragment;

    private Toolbar mToolbar;

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , quicksand_regular), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaring typefaces w/o regular because declared in applyFontToMenuItem

        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");
        Typeface quicksand_bolditalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        Typeface quicksand_italic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Italic.otf");
        Typeface quicksand_light = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Light.otf");
        Typeface quicksand_lightitalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-LightItalic.otf");

        //  listener für click events in navigationDrawer -> calls method
        setNavigationViewListner();

        //  set toolbar and get a ActionBarDrawerToggle for onOptionsItemsSelected
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");

        TextView tv_toolbar_title_workaround = (TextView) findViewById(R.id.tv_toolbar_title_workaround);
        tv_toolbar_title_workaround.setTypeface(quicksand_bold);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        //  set a drawerListener for onOptionsItemsSelected
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**
         * Get Permissions or check them
         */

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhaveWritePermission() || !checkIfAlreadyhaveReadPermission()) {
                requestForSpecificPermission();
            }
        }


        /**
         * BottomNavigationView
         */

        topnavigationview = (BottomNavigationView) findViewById(R.id.top_navigation_view);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setItemBackgroundResource(R.drawable.mainactivitybackgroundhighlight);
        bottomNavigationView.setItemIconTintList(null);

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 43, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 43, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        //  listen for clicks and navigate between fragments
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = NewsFeedFragment.newInstance();
                                topnavigationview.setVisibility(View.VISIBLE);
                                break;
                            case R.id.action_item2:
                                selectedFragment = MainMenuFragment.newInstance();
                                topnavigationview.setVisibility(View.GONE);
                                break;
                            case R.id.action_item3:
                                selectedFragment = CalendarFragment.newInstance();
                                topnavigationview.setVisibility(View.GONE);
                                break;
                        }

                        //  get an transaction when switching the fragment -> UE seems less buggy
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        FragmentManager fm = getSupportFragmentManager();
                        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        transaction.replace(R.id.frame_layout, selectedFragment);

                        transaction.commit();
                        return true;
                    }
        });

        topnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigaton_item_news:
                        selectedFragment = NewsFeedFragment.newInstance();
                        break;
                    case R.id.navigation_item_notification:
                        selectedFragment = NotificationFragment.newInstance();
                        break;

                }

                //  get an transaction when switching the fragment -> UE seems less buggy
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FragmentManager fm = getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                transaction.replace(R.id.frame_layout, selectedFragment);

                transaction.commit();
                return true;
            }
        });

        //  Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //clean backstack

        transaction.replace(R.id.frame_layout, NewsFeedFragment.newInstance());
        transaction.commit();

        //  Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);


    }

    //  listens for click events in the navigationDrawer & assigns typefaces to menu items
    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }

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
            case R.id.nav_account: {
                Intent intent = new Intent(MainActivity.this, MyAccount.class);
                this.startActivity(intent);
                break;
            }
            case R.id.nav_einstellungen: {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                this.startActivity(intent);
                break;
            }


        }

        //  close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean checkIfAlreadyhaveWritePermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfAlreadyhaveReadPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,}, 101);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read and write your External storage", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {

        /*int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0){

            //double back press to exit

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

        }else{

            getFragmentManager().popBackStack();

        }*/

        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }


    }





}

