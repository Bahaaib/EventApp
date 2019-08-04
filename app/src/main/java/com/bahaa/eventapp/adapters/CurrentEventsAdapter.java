package com.bahaa.eventapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrentEventsAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<EventModel> adapterModel;


    public CurrentEventsAdapter(Context context, ArrayList<EventModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;


    }


    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.current_events_card, parent, false);
        return new CurrentEventsViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CurrentEventsViewHolder) holder).BindView(position);

    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }

    //Here we bind all the children views of each cardView with their corresponding
    // actions to show & interact with them
    public class CurrentEventsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.current_events_img)
        public ImageView image;

        @BindView(R.id.current_events_date)
        public TextView date;

        @BindView(R.id.current_events_title)
        public TextView title;

        @BindView(R.id.current_events_capacity)
        public TextView capacity;

        @BindView(R.id.current_events_avialable)
        public TextView ticketsAvailable;


        CurrentEventsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        //Here where all the glory being made..!
        public void BindView(final int position) {
            Picasso.get()
                    .load(adapterModel.get(position).getImage())
                    .fit()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(image);

            setAdjustedTitle(title, position, 80);

            date.setText(adapterModel.get(position).getDate());
            capacity.setText(String.valueOf(adapterModel.get(position).getCapacity()));
            if (adapterModel.get(position).getTicketsAvailable() == 0) {
                ticketsAvailable.setText("SOLD OUT");
                ticketsAvailable.setTextColor(Color.RED);
                ticketsAvailable.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                ticketsAvailable.setText(String.valueOf(adapterModel.get(position).getTicketsAvailable()));
            }


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


        @OnClick(R.id.current_events_card)
        void openEvent() {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }


    }
}
