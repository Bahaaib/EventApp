package com.bahaa.eventapp.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bahaa.eventapp.MockedData;
import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.NearbyEventsAdapter;
import com.bahaa.eventapp.models.EventModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.LOCATION_SERVICE;


public class NearbyFragment extends Fragment {

    @BindView(R.id.nearby_events_rv)
    public RecyclerView nearbyEventsRV;

    @BindView(R.id.nearby_no_events)
    public RelativeLayout noEventsLayout;


    private ArrayList<EventModel> nearbyEventsList;
    private NearbyEventsAdapter nearbyEventsAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private int distanceLimiter;
    private double mLatitude;
    private double mLongitude;
    private final String PREFS_KEY = "general_prefs";
    private final String DISTANCE_KEY = "slider_position";
    private SharedPreferences preferences;
    private Unbinder unbinder;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nearby, container, false);

        unbinder = ButterKnife.bind(this, v);
        retrieveSharedPrefs();
        setupNearbyEventsRV();
        getDeviceLocation();

        for (int j = 0; j < 3; j++) {
            EventModel model = new EventModel();
            model.setImage(MockedData.images[j]);
            model.setDate(MockedData.dates[j]);
            model.setTitle(MockedData.titles[j]);

            double distance = meterDistanceBetweenLocations(mLatitude, mLongitude,
                    MockedData.latitude[j], MockedData.longitude[j]) / 1000;

            if (distance < distanceLimiter) {
                model.setDistance(getRoundedFormat(distance));
                //add event only if within distance limits
                nearbyEventsList.add(model);
            }

        }
        nearbyEventsAdapter.notifyDataSetChanged();
        changeListState();

        return v;
    }

    private void retrieveSharedPrefs() {
        if (getActivity() != null) {
            preferences = getActivity().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
            calculatePreferenceDistance();
        }

    }

    private void calculatePreferenceDistance() {
        float ratio = preferences.getFloat(DISTANCE_KEY, 0.5f);
        distanceLimiter = (int) (ratio * 100);
    }

    private void getDeviceLocation() {
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        try {
            for (String provider : providers) {
                Location loc = mLocationManager.getLastKnownLocation(provider);
                if (loc == null) {
                    continue;
                }
                if (bestLocation == null || loc.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = loc;
                }
            }

            mLatitude = bestLocation.getLatitude();
            mLongitude = bestLocation.getLongitude();

        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    private void setupNearbyEventsRV() {
        nearbyEventsList = new ArrayList<>();
        nearbyEventsAdapter = new NearbyEventsAdapter(getActivity(), nearbyEventsList);
        nearbyEventsRV.setAdapter(nearbyEventsAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        nearbyEventsRV.setLayoutManager(staggeredGridLayoutManager);
    }

    private double meterDistanceBetweenLocations(
            double originLatitude, double originLongitude,
            double remoteLatitude, double remoteLongitude) {

        double pk = 180.f / Math.PI;

        double a1 = originLatitude / pk;
        double a2 = originLongitude / pk;

        double b1 = remoteLatitude / pk;
        double b2 = remoteLongitude / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);

        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }

    private String getRoundedFormat(double number) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(number);

    }

    private void changeListState() {
        if (nearbyEventsList.isEmpty()) {
            noEventsLayout.setVisibility(View.VISIBLE);
        } else {
            noEventsLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
