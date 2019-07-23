package com.bahaa.eventapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bahaa.eventapp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.details_event_collapsing_toolbar)
    public CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.details_event_toolbar)
    public Toolbar toolbar;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupActionBar();
        setupCollapsingToolbar();


    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupCollapsingToolbar() {
        String title = "Software Engineering Event Software Engineering Event";
        title = title.substring(0, 31);
        title = title.concat("...");
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
