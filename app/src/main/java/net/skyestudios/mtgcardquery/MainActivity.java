package net.skyestudios.mtgcardquery;


import android.os.Bundle;
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

import net.skyestudios.mtgcardquery.fragments.DecksFragment;
import net.skyestudios.mtgcardquery.fragments.QueryFragment;
import net.skyestudios.mtgcardquery.fragments.SettingsFragment;
import net.skyestudios.mtgcardquery.fragments.WishlistFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Fragment fragment;
    private int currentDrawerID;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);

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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START, true);
        } else {
            super.onBackPressed();
        }
    }
}
