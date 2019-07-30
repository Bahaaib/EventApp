package com.bahaa.eventapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.activities.DetailsActivity;
import com.bahaa.eventapp.models.EventModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NearbyEventsAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<EventModel> adapterModel;
    private Unbinder unbinder;

    {
        adapterModel = new ArrayList<>();
    }

    public NearbyEventsAdapter(Context context, ArrayList<EventModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;


    }


    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.nearby_events_card, parent, false);
        return new NearbyEventsViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NearbyEventsViewHolder) holder).BindView(position);

    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }

    //Here we bind all the children views of each cardView with their corresponding
    // actions to show & interact with them
    public class NearbyEventsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nearby_events_image)
        public ImageView nearbyImage;

        @BindView(R.id.nearby_events_date)
        public TextView nearbyDate;

        @BindView(R.id.nearby_events_title)
        public TextView nearbyTitle;

        @BindView(R.id.nearby_events_dist)
        public TextView nearbyDistance;


        public NearbyEventsViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }


        //Here where all the glory being made..!
        public void BindView(final int position) {
            Picasso.get()
                    .load(adapterModel.get(position).getImage())
                    .fit()
                    .into(nearbyImage);

            nearbyDate.setText(adapterModel.get(position).getDate());
            nearbyDistance.setText(adapterModel.get(position).getDistance() + " KM");

            String title = adapterModel.get(position).getTitle();
            if (title.length() > 60){
                nearbyTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
            nearbyTitle.setText(adapterModel.get(position).getTitle());

        }

        @OnClick(R.id.nearby_events_card)
        public void openEvent() {
            Intent intent = new Intent(context, DetailsActivity.class);
            context.startActivity(intent);
        }

    }
}
