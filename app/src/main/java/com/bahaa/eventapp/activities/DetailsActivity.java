package com.bahaa.eventapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.bahaa.eventapp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity {

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


    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupActionBar();
        setupCollapsingToolbar();
        resizeAddressDrawable();
        setupExpandableDescription();




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

    private void resizeAddressDrawable() {
        locationIcon.setBounds(
                0,
                0,
                convertToPixels(24),
                convertToPixels(24));
        address.setCompoundDrawables(locationIcon, null, null, null);

    }

    private int convertToPixels(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);

    }

    private void setupExpandableDescription(){

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

    @OnClick(R.id.details_event_description)
    public void toggleExpandableDescription(){

        description.toggle();
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
