package com.bahaa.eventapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.bahaa.eventapp.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_toolbar)
    public Toolbar toolbar;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        unbinder = ButterKnife.bind(this);
        setupActionBar();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search for events");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent homeIntent = new Intent(this, MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
