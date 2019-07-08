package com.bahaa.eventapp.fragments;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bahaa.eventapp.MockedData;
import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.CurrentEventsAdapter;
import com.bahaa.eventapp.adapters.NearbyEventsAdapter;
import com.bahaa.eventapp.models.EventModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NearbyFragment extends Fragment {

    @BindView(R.id.nearby_events_rv)
    public RecyclerView nearbyEventsRV;

    private Unbinder unbinder;
    private ArrayList<EventModel> nearbyEventsList;
    private NearbyEventsAdapter nearbyEventsAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private int distanceLimiter = 1000000;
    private double mLatitude;
    private double mLongitude;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nearby, container, false);

        unbinder = ButterKnife.bind(this, v);

        //My location
        double mLat = 30.000868333333333;
        double mLong = 31.165094999999997;

        //Test Location1
        double vLat1 = 30.003722;
        double vLong1 = 31.168816;

        //Test Location2
        double vLat2 = 30.044740;
        double vLong2 = 31.235614;

        //Test Location3
        double vLat3 = 26.563984;
        double vLong3 = 31.695623;


        float dist = (float) meterDistanceBetweenLocations(mLat, mLong, vLat3, vLong3) / 1000;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.UP);


        Log.i("Distance: ", df.format(dist) + " Kilometers");
        ////////////
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

        return v;
    }

    private void getDeviceLocation() {
        try {
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
