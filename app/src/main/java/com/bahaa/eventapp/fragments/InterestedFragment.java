package com.bahaa.eventapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.MockedData;
import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.InterestedEventsAdapter;
import com.bahaa.eventapp.models.EventModel;
import com.bahaa.eventapp.utils.SwipeToDeleteCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class InterestedFragment extends Fragment {

    @BindView(R.id.interested_no_events)
    public RelativeLayout noEventsLayout;

    @BindView(R.id.interested_event_rv)
    public RecyclerView intstdRV;

    @BindView(R.id.interested_events_header)
    public TextView textHeader;

    private ArrayList<EventModel> interestedEventsList;
    private InterestedEventsAdapter interestedEventsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Unbinder unbinder;


    public InterestedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_interested, container, false);
        unbinder = ButterKnife.bind(this, v);

        setupInterestedRV();

        //fill mocked data
        for (int i = 0; i < 3; i++) {
            EventModel model = new EventModel();
            model.setImage(MockedData.images[i]);
            model.setDate(MockedData.dates[i]);
            model.setTitle(MockedData.titles[i]);
            model.setCapacity(MockedData.capacity[i]);
            model.setTicketsAvailable(MockedData.ticketsAvailable[i]);

            interestedEventsList.add(model);
        }
        interestedEventsAdapter.notifyDataSetChanged();


        return v;
    }

    private void setupInterestedRV() {
        interestedEventsList = new ArrayList<>();
        interestedEventsAdapter = new InterestedEventsAdapter(getActivity(), interestedEventsList, this);
        intstdRV.setAdapter(interestedEventsAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        intstdRV.setLayoutManager(linearLayoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(interestedEventsAdapter));
        itemTouchHelper.attachToRecyclerView(intstdRV);
    }

    public void changeListState() {

        noEventsLayout.setVisibility(View.VISIBLE);
        textHeader.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
