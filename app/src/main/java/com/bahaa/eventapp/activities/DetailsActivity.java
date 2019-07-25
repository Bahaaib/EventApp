package com.bahaa.eventapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bahaa.eventapp.MockedData;
import com.bahaa.eventapp.R;
import com.bahaa.eventapp.dialogs.BookingDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.details_event_collapsing_toolbar)
    public CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.details_event_toolbar)
    public Toolbar toolbar;

    @BindView(R.id.details_event_address)
    public TextView address;

    @BindDrawable(R.drawable.ic_location)
    public Drawable locationIcon;

    @BindView(R.id.details_event_description)
    public ExpandableTextView description;

    @BindView(R.id.details_event_map_container)
    public RelativeLayout mapContainer;

    @BindView(R.id.details_event_fab)
    public FloatingActionButton fab;

    @BindView(R.id.details_event_interest)
    public TextView interested;

    @BindDrawable(R.drawable.ic_star)
    public Drawable starIcon;

    @BindDrawable(R.drawable.ic_check_empty)
    public Drawable checkIcon;

    @BindView(R.id.details_event_booking_button)
    public AppCompatButton bookingButton;

    private boolean isInterested = false;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupActionBar();
        setupCollapsingToolbar();
        setupInterestedText();
        setupAddressText();
        setupExpandableDescription();
        setupEventLocationMap();


    }

    @OnClick(R.id.details_event_interest)
    public void toggleInterestedState() {

        controlIconState();
    }

    @OnClick(R.id.details_event_description)
    public void toggleDescriptionState() {

        description.toggle();


        if (!description.isExpanded()) {
            adjustMapPosition(470);
        } else {
            adjustMapPosition(280);
        }


    }


    @OnClick(R.id.details_event_fab)
    public void shareEvent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, \nI'm interested in Software Engineering Event. \nCheck it out on \n" + "https://play.google.com/store/apps/details?id=" + getPackageName());
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share with"));
    }

    @OnClick(R.id.details_event_booking_button)
    public void launchBookingDialog(){

        BookingDialog bookingDialog = new BookingDialog();
        final String tag = "booking_dialog";

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.add(bookingDialog, tag).commit();
    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupCollapsingToolbar() {
        String title = "Software Engineering Event Software Engineering Event";

        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedText);
    }

    private void setupInterestedText() {
        controlIconState();
    }

    private void controlIconState() {
        if (isInterested) {
            interested.setBackground(getResources().getDrawable(R.drawable.shape_round_side_on));
            int rightPx = convertToPixels(18);
            int bottomPx = convertToPixels(18);
            checkIcon = resizeDrawable(checkIcon, rightPx, bottomPx);
            interested.setCompoundDrawables(null, null, checkIcon, null);
            isInterested = false;
        } else {
            interested.setBackground(getResources().getDrawable(R.drawable.shape_round_side_off));
            int rightPx = convertToPixels(18);
            int bottomPx = convertToPixels(18);
            starIcon = resizeDrawable(starIcon, rightPx, bottomPx);
            interested.setCompoundDrawables(null, null, starIcon, null);
            isInterested = true;
        }
    }

    private Drawable resizeDrawable(Drawable drawable, int right, int bottom) {
        drawable.setBounds(
                0,
                0,
                right,
                bottom);
        return drawable;
    }

    private int convertToPixels(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);

    }

    private void setupExpandableDescription() {

        description.setInterpolator(new OvershootInterpolator());

        String descriptionText = "It is a long established fact that a reader will be distracted by " +
                "the readable content of a page when looking at its layout. The point of using Lorem" +
                " Ipsum is that it has a more-or-less normal distribution of letters, as opposed to" +
                " using 'Content here, content here', making it look like readable English. Many " +
                "desktop publishing packages and web page editors now use Lorem Ipsum as their" +
                " default model text, and a search for 'lorem ipsum' will uncover many web sites" +
                " still in their infancy. Various versions have evolved over the years, sometimes" +
                " by accident, sometimes on purpose (injected humour and the like).";

        description.setText(descriptionText);
    }

    private void setupAddressText() {
        int rightPx = convertToPixels(24);
        int bottomPx = convertToPixels(24);

        locationIcon = resizeDrawable(locationIcon, rightPx, bottomPx);
        address.setCompoundDrawables(locationIcon, null, null, null);
    }

    private void setupEventLocationMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.details_event_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    private void adjustMapPosition(int top) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mapContainer.getLayoutParams();

        int topPx = convertToPixels(top);

        params.setMargins(0, topPx, 0, 80);
        mapContainer.setLayoutParams(params);
        animateMap();

    }

    private void animateMap() {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(mapContainer, autoTransition);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = new LatLng(MockedData.latitude[0], MockedData.longitude[0]);

        //Add Marker to location
        googleMap.addMarker(
                new MarkerOptions().position(latLng).title("Event Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Zoom in to location
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(17)
                .bearing(90)
                .tilt(30)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setOnMapClickListener(latLng1 -> {
            Uri mapUri = Uri.parse("geo:" + latLng.latitude + "," + latLng.longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_order:
                Toast.makeText(getApplicationContext(), "Booked!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_share:
                shareEvent();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

}
