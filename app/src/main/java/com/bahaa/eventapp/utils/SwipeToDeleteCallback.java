package com.bahaa.eventapp.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.adapters.InterestedEventsAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private InterestedEventsAdapter interestedEventsAdapter;
    private Drawable icon;
    private ColorDrawable background;

    public SwipeToDeleteCallback(InterestedEventsAdapter interestedEventsAdapter) {
        super(0, ItemTouchHelper.LEFT);
        this.interestedEventsAdapter = interestedEventsAdapter;

        icon = ContextCompat.getDrawable(interestedEventsAdapter.getContext(),
                R.drawable.ic_delete);
        background = new ColorDrawable(Color.RED);

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        interestedEventsAdapter.deleteItem(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 0;

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        //Swiping to the left
        if (dX < 0) {
            int iconRight = itemView.getRight() - iconMargin + icon.getIntrinsicWidth() - 40;
            int iconLeft = iconRight - icon.getIntrinsicWidth();


            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
            // view is unSwiped
        } else {
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }
}
