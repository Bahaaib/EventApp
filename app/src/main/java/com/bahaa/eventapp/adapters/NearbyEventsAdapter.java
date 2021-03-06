package com.bahaa.eventapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.activities.DetailsActivity;
import com.bahaa.eventapp.models.EventModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearbyEventsAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<EventModel> adapterModel;


    public NearbyEventsAdapter(Context context, ArrayList<EventModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;


    }


    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.nearby_events_card, parent, false);
        return new NearbyEventsViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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


        NearbyEventsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
            if (title.length() > 60) {
                nearbyTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }

            setAdjustedTitle(nearbyTitle, position, 100);
        }

        private void setAdjustedTitle(TextView tv, int cardPos, int maxLength) {
            tv.setText(adapterModel.get(cardPos).getTitle());
            ViewTreeObserver treeObserver = tv.getViewTreeObserver();
            treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver observer = tv.getViewTreeObserver();
                    observer.removeOnGlobalLayoutListener(this);
                    int titleLen = tv.length();
                    if (titleLen > maxLength) {
                        String text = tv.getText().toString();
                        text = text.replace(text.substring(maxLength, titleLen), "");
                        text = text + "...";
                        tv.setText(text);
                    }
                }
            });

        }


        @OnClick(R.id.nearby_events_card)
        void openEvent() {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }

    }
}
