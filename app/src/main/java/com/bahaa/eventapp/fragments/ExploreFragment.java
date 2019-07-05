package com.bahaa.eventapp.fragments;


import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bahaa.eventapp.MockedData;
import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.HorizontalEventAdapter;
import com.bahaa.eventapp.models.HorizontalEventModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ExploreFragment extends Fragment {

    @BindView(R.id.search_view)
    public SearchView searchView;
    @BindView(R.id.events_rv_horiz)
    public RecyclerView horizontalEventRV;

    private Unbinder unbinder;
    private ArrayList<HorizontalEventModel> horizontalEventsList;
    private HorizontalEventAdapter horizontalEventAdapter;
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

        setupHorizontalRV();

        //fill mocked data
        for (int i = 0; i < 3; i++) {
            HorizontalEventModel model = new HorizontalEventModel();
            model.setImage(MockedData.images[i]);
            model.setDate(MockedData.dates[i]);
            model.setTitle(MockedData.titles[i]);
            model.setCapacity(MockedData.capacity[i]);
            model.setTicketsAvailable(MockedData.ticketsAvailable[i]);

            horizontalEventsList.add(model);
        }


        horizontalEventAdapter.notifyDataSetChanged();


        return v;
    }

    private void setupHorizontalRV() {
        horizontalEventsList = new ArrayList<>();
        horizontalEventAdapter = new HorizontalEventAdapter(getActivity(), horizontalEventsList);
        horizontalEventRV.setAdapter(horizontalEventAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalEventRV.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
