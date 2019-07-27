package com.bahaa.eventapp.dialogs;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bahaa.eventapp.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BookingDialog extends DialogFragment {

    @BindView(R.id.front_layout)
    public RelativeLayout frontLayout;

    @BindView(R.id.booking_name_layout)
    public TextInputLayout nameLayout;

    private Unbinder unbinder;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_booking, container, false);

        unbinder = ButterKnife.bind(this, v);
        setupDialogRoundedCorners();

        int distance = 4000;
        float scale = getResources().getDisplayMetrics().density * distance;

        v.setCameraDistance(scale);


        return v;
    }

    @OnClick(R.id.booking_ok_button)
    public void confirmBooking() {

        AnimatorSet anim = new AnimatorSet();
        AnimatorSet rightOut = (AnimatorSet)AnimatorInflater.loadAnimator(getActivity(), R.animator.right_out);

        rightOut.setTarget(frontLayout);

        anim.play(rightOut);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                ConfirmationDialog confDialog = new ConfirmationDialog();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("confirmation_dialog");
                if (fragment != null) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.add(confDialog, "confirmation_dialog").commit();


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();


    }

    @OnClick(R.id.booking_cancel_button)
    public void dismissDialog() {
        if (getDialog() != null) {
            AnimatorSet anim = new AnimatorSet();
            AnimatorSet leftOut = (AnimatorSet)AnimatorInflater.loadAnimator(getActivity(), R.animator.left_out);
            AnimatorSet leftIn = (AnimatorSet)AnimatorInflater.loadAnimator(getActivity(), R.animator.left_in);

            anim.playTogether(leftOut, leftIn);
            //anim.setTarget(main);
            anim.start();
        }
    }

    private void setupDialogRoundedCorners() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            //getDialog().getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
