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
import com.bahaa.eventapp.adapters.FutureEventsAdapter;
import com.bahaa.eventapp.models.EventModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ExploreFragment extends Fragment {

    @BindView(R.id.search_view)
    public SearchView searchView;
    @BindView(R.id.current_events_rv)
    public RecyclerView currentEventsRV;
    @BindView(R.id.future_events_rv)
    public RecyclerView futureEventsRV;

    private Unbinder unbinder;
    private ArrayList<EventModel> currentEventsList;
    private CurrentEventsAdapter currentEventsAdapter;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<EventModel> futureEventsList;
    private FutureEventsAdapter futureEventsAdapter;

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
            EventModel model = new EventModel();
            model.setImage(MockedData.images[i]);
            model.setDate(MockedData.dates[i]);
            model.setTitle(MockedData.titles[i]);
            model.setCapacity(MockedData.capacity[i]);
            model.setTicketsAvailable(MockedData.ticketsAvailable[i]);

            currentEventsList.add(model);
        }

        currentEventsAdapter.notifyDataSetChanged();

        setupFutureEventsRV();

        for (int j = 0; j < 3; j++) {
            EventModel model = new EventModel();
            model.setImage(MockedData.images[j]);
            model.setDate(MockedData.dates[j]);
            model.setTitle(MockedData.titles[j]);

            futureEventsList.add(model);
        }
        futureEventsAdapter.notifyDataSetChanged();


        return v;
    }

    private void setupCurrentEventsRV() {
        currentEventsList = new ArrayList<>();
        currentEventsAdapter = new CurrentEventsAdapter(getActivity(), currentEventsList);
        currentEventsRV.setAdapter(currentEventsAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        currentEventsRV.setLayoutManager(linearLayoutManager);
    }

    private void setupFutureEventsRV() {
        futureEventsList = new ArrayList<>();
        futureEventsAdapter = new FutureEventsAdapter(getActivity(), futureEventsList);
        futureEventsRV.setAdapter(futureEventsAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        futureEventsRV.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
