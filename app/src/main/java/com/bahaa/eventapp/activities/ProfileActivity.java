package com.bahaa.eventapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.dialogs.UsernameDialog;
import com.bahaa.eventapp.models.UserModel;
import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.profile_user_img)
    public ImageView userIV;

    @BindView(R.id.profile_init_letter)
    public TextView userInitLetterTV;

    @BindView(R.id.profile_user_name)
    public TextView usernameTV;

    @BindView(R.id.profile_user_password)
    public TextView userPasswordTV;

    @BindView(R.id.profile_user_email)
    public TextView userMailTV;

    @BindView(R.id.profile_user_mobile)
    public TextView userMobileTV;

    @BindView(R.id.profile_save_btn)
    public Button saveBtn;

    @BindDrawable(R.drawable.rounded_rec)
    public Drawable rect;

    private Unbinder unbinder;
    private final String TAG = "username_dialog";
    private UsernameDialog usernameDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        unbinder = ButterKnife.bind(this);
        showUserImage();
        usernameDialog = new UsernameDialog();


    }

    @OnClick(R.id.profile_user_name)
    public void changeUsername() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.add(usernameDialog, TAG).commit();
    }

    private void showUserImage() {
        //Mocked User data
        UserModel user = new UserModel();
        user.setImageUrl(R.drawable.bahaa);
        user.setName("Bahaa Ibrahim");
        user.setEmail("Bahaa@test.com");

        if (user.getImageUrl() != 0) {
            Picasso.get().load(user.getImageUrl()).fit().into(userIV);
        } else {
            char letter = user.getName().charAt(0);
            userInitLetterTV.setText(String.valueOf(letter));
            userInitLetterTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
