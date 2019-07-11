package com.bahaa.eventapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NavigationHeaderViewHolder {

    @BindView(R.id.user_image)
    protected ImageView userImageView;

    @BindView(R.id.user_init_letter)
    protected TextView userInitLetter;

    @BindView(R.id.user_name)
    protected TextView usernameTv;

    @BindView(R.id.user_mail)
    protected TextView usermailTv;

    private Unbinder unbinder;

    public NavigationHeaderViewHolder(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    public void unbind() {
        unbinder.unbind();
    }
}
