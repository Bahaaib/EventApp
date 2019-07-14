package com.bahaa.eventapp.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahaa.eventapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NavigationHeaderViewHolder {

    @BindView(R.id.user_image)
    public ImageView userImageView;

    @BindView(R.id.user_init_letter)
    public TextView userInitLetter;

    @BindView(R.id.user_name)
    public TextView usernameTv;

    @BindView(R.id.user_mail)
    public TextView usermailTv;

    private Unbinder unbinder;

    public NavigationHeaderViewHolder(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    public void unbind() {
        unbinder.unbind();
    }
}
