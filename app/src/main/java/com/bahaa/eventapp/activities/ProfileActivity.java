package com.bahaa.eventapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.dialogs.MobileDialog;
import com.bahaa.eventapp.dialogs.PasswordDialog;
import com.bahaa.eventapp.dialogs.UsernameDialog;
import com.bahaa.eventapp.models.UserModel;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
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

    @BindView(R.id.profile_img_icon)
    public ImageView imageIconIV;

    private Unbinder unbinder;
    private final String USERNAME_TAG = "username_dialog";
    private final String PASS_TAG = "password_dialog";
    private final String  MOBILE_TAG = "mobile_dialog";
    private UsernameDialog usernameDialog;
    private PasswordDialog passwordDialog;
    private MobileDialog mobileDialog;
    private final int GALLERY_INTENT = 22;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        unbinder = ButterKnife.bind(this);
        showUserImage();
        usernameDialog = new UsernameDialog();
        passwordDialog = new PasswordDialog();
        mobileDialog = new MobileDialog();
        progressDialog = new ProgressDialog(this);


    }

    @OnClick(R.id.profile_img_icon)
    public void editProfileImg() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    @OnLongClick(R.id.profile_user_name)
    public void editUsername() {
        showDialogFragment(usernameDialog, USERNAME_TAG);
    }

    @OnLongClick(R.id.profile_user_password)
    public void changePassword() {
        showDialogFragment(passwordDialog, PASS_TAG);
    }

    @OnLongClick(R.id.profile_user_mobile)
    public void editMobileNumber(){
        showDialogFragment(mobileDialog, MOBILE_TAG);
    }


    private void showDialogFragment(DialogFragment dialogFragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.add(dialogFragment, tag).commit();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
            }

            if (uri != null) {
                String path = uri.getLastPathSegment();
                Log.i("Statuss", path);
                progressDialog.setMessage("Updating Profile Picture..");
                progressDialog.show();

                // @todo #4 Upload and Update profile img

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
