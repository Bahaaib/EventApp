package com.bahaa.eventapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bahaa.eventapp.R;
import com.bahaa.eventapp.dialogs.MobileDialog;
import com.bahaa.eventapp.dialogs.PasswordDialog;
import com.bahaa.eventapp.dialogs.UsernameDialog;
import com.bahaa.eventapp.models.UserModel;
import com.bahaa.eventapp.utils.NavigationHeaderViewHolder;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

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

    @BindView(R.id.profile_img_icon)
    public ImageView imageIconIV;

    @BindView(R.id.profile_drawer)
    public DrawerLayout drawerLayout;

    @BindView(R.id.profile_nv)
    public NavigationView navigationView;

    @BindView(R.id.profile_toolbar)
    public Toolbar toolbar;


    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View header;
    private NavigationHeaderViewHolder holder;
    private final String USERNAME_TAG = "username_dialog";
    private final String PASS_TAG = "password_dialog";
    private final String MOBILE_TAG = "mobile_dialog";
    private UsernameDialog usernameDialog;
    private PasswordDialog passwordDialog;
    private MobileDialog mobileDialog;
    private final int GALLERY_INTENT = 22;
    private ProgressDialog progressDialog;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupNavigationDrawer();
        setupNavigationDrawerHeader();

        showUserImage();
        initDialogs();

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
    public void editMobileNumber() {
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

    private void setupNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        navigationView.getMenu().getItem(1).setChecked(true);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_home:
                    navigateToActivity(MainActivity.class);
                    return true;

                case R.id.action_notification:
                    navigateToActivity(NotificationActivity.class);
                    return true;

                case R.id.action_orders:
                    navigateToActivity(OrdersActivity.class);
                    return true;

                case R.id.action_points:
                    navigateToActivity(PointsActivity.class);
                    return true;

                case R.id.action_settings:
                    navigateToActivity(SettingsActivity.class);
                    return true;

                default:
                    return true;
            }
        });

    }

    private void setupNavigationDrawerHeader() {
        header = navigationView.getHeaderView(0);
        holder = new NavigationHeaderViewHolder(header);

        //Mocked User data
        UserModel user = new UserModel();
        //user.setImageUrl(R.drawable.bahaa);
        user.setName("Bahaa Ibrahim");
        user.setEmail("Bahaa@test.com");

        if (user.getImageUrl() != 0) {
            Picasso.get().load(user.getImageUrl()).fit().into(holder.userImageView);
        } else {
            char letter = user.getName().charAt(0);
            holder.userInitLetter.setText(String.valueOf(letter));
            holder.userInitLetter.setVisibility(View.VISIBLE);
        }
        holder.usernameTv.setText(user.getName());
        holder.usermailTv.setText(user.getEmail());
    }

    private void displayToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void navigateToActivity(Class<? extends AppCompatActivity> TargetActivity) {
        Intent intent = new Intent(ProfileActivity.this, TargetActivity);
        startActivity(intent);
    }

    private void initDialogs() {
        usernameDialog = new UsernameDialog();
        passwordDialog = new PasswordDialog();
        mobileDialog = new MobileDialog();
        progressDialog = new ProgressDialog(this);
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

    private void uncheckAllDrawerItems() {
        int size = navigationView.getMenu().size();

        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
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
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
            uncheckAllDrawerItems();
            navigationView.getMenu().getItem(1).setChecked(true);
            super.onResume();
        } else {
            super.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
