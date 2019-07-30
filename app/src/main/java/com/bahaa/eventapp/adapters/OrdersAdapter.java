package com.bahaa.eventapp.adapters;

import android.content.Context;
import android.content.Intent;
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

public class OrdersAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<EventModel> adapterModel;
    private Unbinder unbinder;

    {
        adapterModel = new ArrayList<>();
    }

    public OrdersAdapter(Context context, ArrayList<EventModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;


    }


    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_card, parent, false);
        return new OrdersAdapter.OrdersViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OrdersAdapter.OrdersViewHolder) holder).BindView(position);

    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }

    //Here we bind all the children views of each cardView with their corresponding
    // actions to show & interact with them
    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ticket_event_img)
        public ImageView orderImage;

        @BindView(R.id.ticket_cancel)
        public TextView ticketCancel;

        @BindView(R.id.ticket_payment_note)
        public TextView paymentNote;

        @BindView(R.id.ticket_date)
        public TextView orderDate;

        @BindView(R.id.ticket_event_title)
        public TextView orderTitle;

        @BindView(R.id.ticket_status_confirmed)
        public TextView orderConfirm;

        @BindView(R.id.ticket_status_pending)
        public TextView orderPending;


        public OrdersViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }


        //Here where all the glory being made..!
        public void BindView(final int position) {
            Picasso.get()
                    .load(adapterModel.get(position).getImage())
                    .fit()
                    .into(orderImage);

            orderDate.setText(adapterModel.get(position).getDate());
            orderTitle.setText(adapterModel.get(position).getTitle());

            if (position % 2 != 0) {
                orderConfirm.setVisibility(View.INVISIBLE);
                orderPending.setVisibility(View.VISIBLE);
                ticketCancel.setVisibility(View.VISIBLE);
                paymentNote.setVisibility(View.VISIBLE);
            }

        }

        @OnClick(R.id.ticket_card)
        public void openEvent() {
            Intent intent = new Intent(context, DetailsActivity.class);
            context.startActivity(intent);
        }

    }
}
