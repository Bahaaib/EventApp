package com.bahaa.eventapp.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.PagerAdapter;
import com.bahaa.eventapp.models.UserModel;
import com.bahaa.eventapp.utils.NavigationHeaderViewHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
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


    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View header;
    private NavigationHeaderViewHolder holder;
    private Unbinder unbinder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupViewPager();
        setupBottomNavigationView();
        setupNavigationDrawer();
        setupNavigationDrawerHeader();

    }

    private void setupViewPager() {
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(pagerAdapter);

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

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_profile:
                    navigateToActivity(ProfileActivity.class);
                    return true;

                case R.id.action_explore:
                    displayToast("Clicked Explore");
                    return true;

                case R.id.action_interested:
                    displayToast("Clicked Interested");
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

    public CoordinatorLayout getMainCoordinatorLayout() {
        return mainCooLayout;
    }

    private void navigateToActivity(Class<? extends  AppCompatActivity> TargetActivity) {
        Intent intent = new Intent(MainActivity.this, TargetActivity);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
