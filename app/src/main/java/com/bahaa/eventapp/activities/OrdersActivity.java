package com.bahaa.eventapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.MockedData;
import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.OrdersAdapter;
import com.bahaa.eventapp.models.EventModel;
import com.bahaa.eventapp.models.UserModel;
import com.bahaa.eventapp.utils.NavigationHeaderViewHolder;
import com.google.android.material.navigation.NavigationView;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrdersActivity extends AppCompatActivity {

    @BindView(R.id.orders_drawer)
    public DrawerLayout drawerLayout;

    @BindView(R.id.orders_nv)
    public NavigationView navigationView;

    @BindView(R.id.orders_toolbar)
    public Toolbar toolbar;

    @BindView(R.id.order_rv)
    public RecyclerView ordersRV;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View header;
    private NavigationHeaderViewHolder holder;
    private ArrayList<EventModel> ordersList;
    private OrdersAdapter ordersAdapter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupNavigationDrawer();
        setupNavigationDrawerHeader();
        setupOrdersRV();

        for (int j = 0; j < 3; j++) {
            EventModel model = new EventModel();
            model.setImage(MockedData.images[j]);
            model.setDate(MockedData.dates[j]);
            model.setTitle(MockedData.titles[j]);

            ordersList.add(model);
        }
        ordersAdapter.notifyDataSetChanged();
    }

    private void setupOrdersRV() {
        ordersList = new ArrayList<>();
        ordersAdapter = new OrdersAdapter(this, ordersList);
        ordersRV.setAdapter(ordersAdapter);
        int cardWidth = convertToPixels(300);
        CardSliderLayoutManager layoutManager = new CardSliderLayoutManager(30, cardWidth, 20f);
        ordersRV.setLayoutManager(layoutManager);
        new CardSnapHelper().attachToRecyclerView(ordersRV);

    }

    private int convertToPixels(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);

    }

    private void setupNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        navigationView.getMenu().getItem(3).setChecked(true);

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
        Intent intent = new Intent(OrdersActivity.this, TargetActivity);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
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
            navigationView.getMenu().getItem(3).setChecked(true);
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
