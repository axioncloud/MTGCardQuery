package net.skyestudios.mtgcardquery;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import net.skyestudios.mtgcardquery.data.Settings;
import net.skyestudios.mtgcardquery.db.MTGCardDataSource;
import net.skyestudios.mtgcardquery.frags.DecksFragment;
import net.skyestudios.mtgcardquery.frags.QueryFragment;
import net.skyestudios.mtgcardquery.frags.SettingsFragment;
import net.skyestudios.mtgcardquery.frags.WishlistFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Settings settings;
    private Boolean backPressedOnce;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Fragment fragment;
    private int currentDrawerID;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private Toast backPressedToast;
    private Handler handler;
    private MTGCardDataSource mtgCardDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);

        //New settings or load from file
        settings = new Settings(getApplicationContext());

        backPressedOnce = false;

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            fragment = new QueryFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

            navigationView.setCheckedItem(R.id.item_card_search);
            currentDrawerID = R.id.item_card_search;
        } else {
            currentDrawerID = savedInstanceState.getInt("currentDrawerId");
            displayFragment();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mtgCardDataSource.closeDb();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentDrawerId", currentDrawerID);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (!item.isChecked()) {
            currentDrawerID = item.getItemId();
            displayFragment();
            drawer.closeDrawer(GravityCompat.START, true);
        }
        return false;
    }

    private void displayFragment() {
        switch (currentDrawerID) {
            case R.id.item_card_search:
                toolbar.setTitle(R.string.app_name);
                fragment = new QueryFragment();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.item_wishlists:
                toolbar.setTitle(getString(R.string.app_name) + ": Wishlists");
                fragment = new WishlistFragment();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.item_settings:
                toolbar.setTitle(getString(R.string.app_name) + ": Settings");
                fragment = new SettingsFragment();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.item_decks:
                toolbar.setTitle(getString(R.string.app_name) + ": Decks");
                fragment = new DecksFragment();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
                break;
            default:
                toolbar.setTitle(getString(R.string.app_name));
                fragment = new QueryFragment();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
                break;
        }
        navigationView.setCheckedItem(currentDrawerID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressedOnce = false;
        handler = new Handler();
        if (!settings.isDatabaseOpened()) {
            settings.openDb(getApplicationContext());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START, true);
        } else if (!backPressedOnce) {
            backPressedOnce = true;
            backPressedToast = Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backPressedToast.show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedToast.cancel();
                    backPressedOnce = false;
                }
            }, 2500);
        } else {
            backPressedToast.cancel();
            finish();
        }
    }
}
