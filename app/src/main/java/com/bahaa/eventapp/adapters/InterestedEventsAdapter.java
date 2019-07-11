package com.bahaa.eventapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.models.EventModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InterestedEventsAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<EventModel> adapterModel;
    private Unbinder unbinder;

    {
        adapterModel = new ArrayList<>();
    }

    public InterestedEventsAdapter(Context context, ArrayList<EventModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;


    }


    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.interested_events_card, parent, false);
        return new InterestedEventsViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((InterestedEventsViewHolder) holder).BindView(position);

    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }

    //Here we bind all the children views of each cardView with their corresponding
    // actions to show & interact with them
    public class InterestedEventsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.interested_events_relativeLayout)
        public RelativeLayout relativeLayout;

        @BindView(R.id.interested_events_img)
        public ImageView intstdImage;

        @BindView(R.id.interested_events_title)
        public TextView intstdTitle;

        @BindView(R.id.interested_events_date)
        public TextView intstdDate;

        @BindView(R.id.interested_events_capacity)
        public TextView intstdCapacity;

        @BindView(R.id.interested_events_availability)
        public TextView intstdAvailable;


        public InterestedEventsViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }


        //Here where all the glory being made..!
        public void BindView(final int position) {
            Picasso.get()
                    .load(adapterModel.get(position).getImage())
                    .fit()
                    .into(intstdImage);

            intstdTitle.setText(adapterModel.get(position).getTitle());
            intstdDate.setText(adapterModel.get(position).getDate());
            intstdCapacity.setText(String.valueOf(adapterModel.get(position).getCapacity()));
            if (adapterModel.get(position).getTicketsAvailable() == 0) {
                intstdAvailable.setText("SOLD OUT");
                intstdAvailable.setTextColor(Color.RED);
                intstdAvailable.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) intstdAvailable.getLayoutParams();
                int rightMarginDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics());
                params.setMarginEnd(rightMarginDP);
                intstdAvailable.setLayoutParams(params);
                intstdAvailable.setBackgroundColor(Color.WHITE);
                intstdAvailable.setPadding(5,0,5, 0);
            } else {
                intstdAvailable.setText(String.valueOf(adapterModel.get(position).getTicketsAvailable()));

            }


        }

    }
}
