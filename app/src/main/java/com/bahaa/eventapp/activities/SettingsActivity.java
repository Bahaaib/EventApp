package com.bahaa.eventapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.models.UserModel;
import com.bahaa.eventapp.utils.NavigationHeaderViewHolder;
import com.google.android.material.navigation.NavigationView;
import com.ramotion.fluidslider.FluidSlider;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kotlin.Unit;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.settings_drawer)
    public DrawerLayout drawerLayout;

    @BindView(R.id.settings_nv)
    public NavigationView navigationView;

    @BindView(R.id.settings_toolbar)
    public Toolbar toolbar;

    @BindView(R.id.settings_distance_slider)
    public FluidSlider slider;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View header;
    private NavigationHeaderViewHolder holder;
    private float sliderPosition;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private final String PREFS_KEY = "general_prefs";
    private final String DISTANCE_KEY = "slider_position";
    private Unbinder unbinder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        retrieveSharedPrefs();
        setSliderListener();
        setupNavigationDrawer();
        setupNavigationDrawerHeader();



    }

    private void setupNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        navigationView.getMenu().getItem(5).setChecked(true);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_home:
                    navigateToActivity(MainActivity.class);
                    return true;

                case R.id.action_profile:
                    navigateToActivity(ProfileActivity.class);
                    return true;

                case R.id.action_notification:
                    navigateToActivity(NotificationActivity.class);
                    return true;

                case R.id.action_orders:
                    navigateToActivity(OrdersActivity.class);
                    return true;

                case R.id.action_points:
                    navigateToActivity(PointsActivity.class);
                    return true;

                default:
                    return true;
            }
        });

    }

    private void setupNavigationDrawerHeader() {
        header = navigationView.getHeaderView(0);
        holder = new NavigationHeaderViewHolder(header);

        //Mocked User data
        UserModel user = new UserModel();
        //user.setImageUrl(R.drawable.bahaa);
        user.setName("Bahaa Ibrahim");
        user.setEmail("Bahaa@test.com");

        if (user.getImageUrl() != 0) {
            Picasso.get().load(user.getImageUrl()).fit().into(holder.userImageView);
        } else {
            char letter = user.getName().charAt(0);
            holder.userInitLetter.setText(String.valueOf(letter));
            holder.userInitLetter.setVisibility(View.VISIBLE);
        }
        holder.usernameTv.setText(user.getName());
        holder.usermailTv.setText(user.getEmail());
    }

    private void displayToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void navigateToActivity(Class<? extends AppCompatActivity> TargetActivity) {
        Intent intent = new Intent(SettingsActivity.this, TargetActivity);
        startActivity(intent);
    }

    private void uncheckAllDrawerItems() {
        int size = navigationView.getMenu().size();

        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    private void setSliderListener(){
        slider.setPositionListener(pos -> {
            sliderPosition = pos;
            return Unit.INSTANCE;
        });
    }

    private void updateSharedPrefs(){
        editor = getSharedPreferences(PREFS_KEY, MODE_PRIVATE).edit();
        editor.putFloat(DISTANCE_KEY, sliderPosition);
        editor.apply();
    }

    private void retrieveSharedPrefs(){
        preferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        sliderPosition = preferences.getFloat(DISTANCE_KEY, 0.5f);

        setSliderPosition(sliderPosition);
    }

    private void setSliderPosition(float pos){
        slider.setPosition(pos);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateSharedPrefs();
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
            uncheckAllDrawerItems();
            navigationView.getMenu().getItem(5).setChecked(true);
            super.onResume();
        } else {
            super.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
