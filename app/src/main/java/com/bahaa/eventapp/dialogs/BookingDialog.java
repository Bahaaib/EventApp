package com.bahaa.eventapp.dialogs;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bahaa.eventapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BookingDialog extends DialogFragment {

    @BindView(R.id.front_layout)
    public RelativeLayout frontLayout;

    @BindView(R.id.booking_name_layout)
    public TextInputLayout nameLayout;


    static boolean isOld; //Detect old dialog to set different enter animation
    static boolean isSingleDismiss; //Decide to dismiss single dialog or all at once
    private View v;
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
        v = inflater.inflate(R.layout.dialog_booking, container, false);

        unbinder = ButterKnife.bind(this, v);

        setupDialogRoundedCorners();
        setAnimationScale();
        animateDialog();


        return v;
    }

    @OnClick(R.id.booking_ok_button)
    void confirmBooking() {
        Log.i("Statuss", "Clicked Booking OK");

        isSingleDismiss = true;
        AnimatorSet anim = new AnimatorSet();
        AnimatorSet rightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.right_out);

        rightOut.setTarget(frontLayout);

        anim.play(rightOut);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                PreviewDialog previewDialog = new PreviewDialog();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("preview_dialog");
                if (fragment != null) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.add(previewDialog, "preview_dialog").commit();


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
    void dismissDialog() {
        isOld = false;
        dismissAllDialogs();

    }

    private void setupDialogRoundedCorners() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }
    }

    private void setAnimationScale() {
        int distance = 4000;
        float scale = getResources().getDisplayMetrics().density * distance;
        v.setCameraDistance(scale);
    }

    private void dismissAllDialogs() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment instanceof DialogFragment) {
                Log.i("Statuss", "Popped " + fragment.getTag());
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismissAllowingStateLoss();
            }
        }
    }

    private void animateDialog() {
        if (isOld) {
            AnimatorSet anim = new AnimatorSet();
            AnimatorSet leftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.left_in);
            leftIn.setTarget(frontLayout);
            anim.play(leftIn);
            anim.start();
        } else {
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
            }

        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (isSingleDismiss) {
            isSingleDismiss = false;
            super.onDismiss(dialog);
        } else {
            dismissAllDialogs();
            isOld = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }


}
