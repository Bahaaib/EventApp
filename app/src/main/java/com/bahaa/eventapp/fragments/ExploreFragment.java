package com.bahaa.eventapp.fragments;


import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bahaa.eventapp.MockedData;
import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.CurrentEventsAdapter;
import com.bahaa.eventapp.models.CurrentEventsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ExploreFragment extends Fragment {

    @BindView(R.id.search_view)
    public SearchView searchView;
    @BindView(R.id.current_events_rv)
    public RecyclerView currentEventsRV;

    private Unbinder unbinder;
    private ArrayList<CurrentEventsModel> currentEventsList;
    private CurrentEventsAdapter currentEventsAdapter;
    private LinearLayoutManager linearLayoutManager;


    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore, container, false);

        unbinder = ButterKnife.bind(this, v);

        setupCurrentEventsRV();

        //fill mocked data
        for (int i = 0; i < 3; i++) {
            CurrentEventsModel model = new CurrentEventsModel();
            model.setImage(MockedData.images[i]);
            model.setDate(MockedData.dates[i]);
            model.setTitle(MockedData.titles[i]);
            model.setCapacity(MockedData.capacity[i]);
            model.setTicketsAvailable(MockedData.ticketsAvailable[i]);

            currentEventsList.add(model);
        }


        currentEventsAdapter.notifyDataSetChanged();


        return v;
    }

    private void setupCurrentEventsRV() {
        currentEventsList = new ArrayList<>();
        currentEventsAdapter = new CurrentEventsAdapter(getActivity(), currentEventsList);
        currentEventsRV.setAdapter(currentEventsAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        currentEventsRV.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
