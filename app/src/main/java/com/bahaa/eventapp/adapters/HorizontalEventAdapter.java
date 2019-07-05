package com.bahaa.eventapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.models.HorizontalEventModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HorizontalEventAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<HorizontalEventModel> adapterModel;
    private Unbinder unbinder;

    {
        adapterModel = new ArrayList<>();
    }

    public HorizontalEventAdapter(Context context, ArrayList<HorizontalEventModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;


    }


    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.event_card_horz, parent, false);
        return new HorizontalEventAdapter.HorizontalEventViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HorizontalEventAdapter.HorizontalEventViewHolder) holder).BindView(position);

    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }

    //Here we bind all the children views of each cardView with their corresponding
    // actions to show & interact with them
    public class HorizontalEventViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_horz_img)
        public ImageView image;
        @BindView(R.id.event_horz_date)
        public TextView date;
        @BindView(R.id.event_horz_title)
        public TextView title;
        @BindView(R.id.event_horz_capacity)
        public TextView capacity;
        @BindView(R.id.event_horz_avialable)
        public TextView ticketsAvailable;


        public HorizontalEventViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }


        //Here where all the glory being made..!
        public void BindView(final int position) {
            Picasso.get()
                    .load(adapterModel.get(position).getImage())
                    .fit()
                    .into(image);

            date.setText(adapterModel.get(position).getDate());
            title.setText(adapterModel.get(position).getTitle());
            capacity.setText(String.valueOf(adapterModel.get(position).getCapacity()));
            if (adapterModel.get(position).getTicketsAvailable() == 0) {
                ticketsAvailable.setText("SOLD OUT");
                ticketsAvailable.setTextColor(Color.RED);
                ticketsAvailable.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            } else {
                ticketsAvailable.setText(String.valueOf(adapterModel.get(position).getTicketsAvailable()));
            }


        }


    }
}
