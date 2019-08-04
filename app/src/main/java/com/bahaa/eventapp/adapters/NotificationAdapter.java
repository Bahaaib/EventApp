package com.bahaa.eventapp.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.models.NotificationModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<NotificationModel> adapterModel;


    public NotificationAdapter(Context context, ArrayList<NotificationModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;


    }


    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification_card, parent, false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NotificationAdapter.NotificationViewHolder) holder).BindView(position);

    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }

    //Here we bind all the children views of each cardView with their corresponding
    // actions to show & interact with them
    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.notification_icon)
        public ImageView iconIV;

        @BindView(R.id.notification_body)
        public TextView bodyTV;

        NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        //Here where all the glory being made..!
        public void BindView(final int position) {
            String id = adapterModel.get(position).getId();
            int points = adapterModel.get(position).getPoints();
            String destination = adapterModel.get(position).getPointsDestination();
            String ticketBody = "Your Ticket #a76W123kB has been confirmed";
            String pointsBody = "You've been charged xx points by ";

            if (!id.isEmpty()) {
                ticketBody = ticketBody.replace(ticketBody.substring(13, 22), id);
                bodyTV.setText(ticketBody);
                iconIV.setImageResource(R.drawable.ic_check_circle);

            } else {
                if (points > 9) {
                    pointsBody = pointsBody.replace(pointsBody.substring(20, 22), String.valueOf(points));

                } else {
                    pointsBody = pointsBody.replace(pointsBody.substring(20, 22), "0" + points);
                }
                pointsBody = pointsBody.concat(destination);

                if (pointsBody.length() > 65) {
                    bodyTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                } else {
                    bodyTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                }
                bodyTV.setText(pointsBody);
                iconIV.setImageResource(R.drawable.ic_points);
            }

        }

    }
}
