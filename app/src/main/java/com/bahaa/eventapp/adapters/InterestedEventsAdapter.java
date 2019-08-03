package com.bahaa.eventapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.activities.DetailsActivity;
import com.bahaa.eventapp.activities.MainActivity;
import com.bahaa.eventapp.models.EventModel;
import com.google.android.material.snackbar.Snackbar;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InterestedEventsAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<EventModel> adapterModel;
    private Unbinder unbinder;
    private EventModel recentlyDeletedItem;
    private int recentlyDeletedItemPosition;

    {
        adapterModel = new ArrayList<>();
    }

    public InterestedEventsAdapter(Context context, ArrayList<EventModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;


    }

    /*Swipe to Delete feature methods
     *
     */
    public Context getContext() {
        return context;
    }

    public void deleteItem(int position) {
        recentlyDeletedItem = adapterModel.get(position);
        recentlyDeletedItemPosition = position;
        adapterModel.remove(position);
        notifyItemRemoved(position);
        showSnackBar();
    }

    private void showSnackBar() {
        //init Snackbar
        View view = ((MainActivity) context).getMainCoordinatorLayout();
        Snackbar snackbar = Snackbar.make(view, "Item Deleted",
                Snackbar.LENGTH_LONG);

        //Hang Snackbar above Bottom Nav View
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snackbar.getView().getLayoutParams();
        params.setAnchorId(R.id.btm_nav);
        params.gravity = Gravity.TOP;
        params.anchorGravity = Gravity.TOP;
        snackbar.getView().setLayoutParams(params);

        //Set Actions to Snackbar
        snackbar.setAction("UNDO", v -> undoDelete());
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void undoDelete() {
        adapterModel.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
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

        @BindView(R.id.interested_events_layout)
        public FoldingCell layout;

        @BindView(R.id.interested_events_img)
        public ImageView intstdImage;

        @BindView(R.id.title_event_title)
        public TextView intstdTitle;

        @BindView(R.id.title_date)
        public TextView intstdDate;

        @BindView(R.id.title_capacity)
        public TextView intstdCapacity;

        @BindView(R.id.title_tickets_available)
        public TextView intstdAvailable;

        @BindView(R.id.interested_events_content_title)
        public TextView contentTitle;

        @BindView(R.id.content_event_description)
        public TextView contentDescription;

        @BindView(R.id.content_date)
        public TextView contentDate;


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


            //Title
            setAdjustedTitle(intstdTitle, position, 50);


            //Date
            String date = adapterModel.get(position).getDate();
            date = date.replace(date.substring(3, 4), "\n");
            intstdDate.setText(date);

            //Capacity & Available
            intstdCapacity.setText(String.valueOf(adapterModel.get(position).getCapacity()));
            if (adapterModel.get(position).getTicketsAvailable() == 0) {
                intstdAvailable.setText("SOLD OUT");
                intstdAvailable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                intstdAvailable.setTextColor(Color.RED);
                intstdAvailable.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) intstdAvailable.getLayoutParams();
                int rightMarginDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
                params.setMarginEnd(rightMarginDP);
                intstdAvailable.setLayoutParams(params);
                intstdAvailable.setBackgroundColor(Color.WHITE);
                intstdAvailable.setPadding(5, 0, 5, 8);
            } else {
                intstdAvailable.setText(String.valueOf(adapterModel.get(position).getTicketsAvailable()));

            }

            //Content Title
            setAdjustedTitle(contentTitle, position, 95);

            //Content Description
            setDescription();

            //Content Date
            setContentDate(position);


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

        private void setDescription() {
            String descriptionText = "It is a long established fact that a reader will be distracted by " +
                    "the readable content of a page when looking at its layout. The point of using Lorem" +
                    " Ipsum is that it has a more-or-less normal distribution of letters, as opposed to" +
                    " using 'Content here, content here', making it look like readable English. Many " +
                    "desktop publishing packages and web page editors now use Lorem Ipsum as their" +
                    " default model text, and a search for 'lorem ipsum' will uncover many web sites" +
                    " still in their infancy. Various versions have evolved over the years, sometimes" +
                    " by accident, sometimes on purpose (injected humour and the like).";

            descriptionText = descriptionText.replace(descriptionText.substring(200, descriptionText.length()), "");
            descriptionText = descriptionText + "...(Read more)";
            contentDescription.setText(descriptionText);
        }

        private void setContentDate(int cardPosition) {
            contentDate.setText(adapterModel.get(cardPosition).getDate());
        }

        @OnClick(R.id.interested_events_layout)
        public void animateCell() {
            layout.toggle(false);
        }

        @OnClick(R.id.content_event_description)
        public void openEventDetails(){
            Intent intent = new Intent(context, DetailsActivity.class);
            context.startActivity(intent);
        }

    }
}
