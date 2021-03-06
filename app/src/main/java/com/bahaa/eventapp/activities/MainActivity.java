package com.bahaa.eventapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.PagerAdapter;
import com.bahaa.eventapp.models.UserModel;
import com.bahaa.eventapp.utils.NavigationHeaderViewHolder;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_coordinator_layout)
    public CoordinatorLayout mainCooLayout;

    @BindView(R.id.main_toolbar)
    public Toolbar toolbar;

    @BindView(R.id.btm_nav)
    public BottomNavigationView bottomNavigationView;

    @BindView(R.id.pager)
    public ViewPager viewPager;

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @BindView(R.id.nv)
    public NavigationView navigationView;

    @BindView(R.id.main_toolbar_triangle)
    public ImageView triangle;

    @BindView(R.id.main_points_layout)
    public RelativeLayout pointsLayout;

    @BindView(R.id.main_toolbar_points_icon)
    public ImageView pointsIcon;


    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupCrashytics();
        setupViewPager();
        setupBottomNavigationView();
        setupNavigationDrawer();
        setupNavigationDrawerHeader();

    }

    @OnClick(R.id.main_toolbar_points_icon)
    public void showPointsLayout() {
        if (pointsLayout.getVisibility() == View.VISIBLE) {
            pointsLayout.setVisibility(View.INVISIBLE);
            triangle.setVisibility(View.INVISIBLE);
        } else {
            pointsLayout.setVisibility(View.VISIBLE);
            triangle.setVisibility(View.VISIBLE);
        }
    }

    private void setupCrashytics() {
        Crashlytics.log(Log.DEBUG, "Android API level: ", String.valueOf(Build.VERSION.SDK_INT));

    }

    private void setupViewPager() {
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                bottomNavigationView.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupBottomNavigationView() {

        bottomNavigationView.setSelectedItemId(R.id.action_explore);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_nearby:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_explore:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_interested:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });
    }

    private void setupNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
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

                case R.id.action_settings:
                    navigateToActivity(SettingsActivity.class);
                    return true;

                default:
                    return true;
            }
        });

    }

    private void setupNavigationDrawerHeader() {
        View header = navigationView.getHeaderView(0);
        NavigationHeaderViewHolder holder = new NavigationHeaderViewHolder(header);

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

    public CoordinatorLayout getMainCoordinatorLayout() {
        return mainCooLayout;
    }

    private void navigateToActivity(Class<? extends AppCompatActivity> TargetActivity) {
        Intent intent = new Intent(MainActivity.this, TargetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void uncheckAllDrawerItems() {
        int size = navigationView.getMenu().size();

        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

            displayToast("Permission Denied!");
        } else {
            //Get Device Location
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            navigationView.getMenu().getItem(0).setChecked(false);
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
            navigationView.getMenu().getItem(0).setChecked(true);
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
