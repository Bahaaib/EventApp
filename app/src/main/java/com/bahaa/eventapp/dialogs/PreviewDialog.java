package com.bahaa.eventapp.dialogs;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bahaa.eventapp.R;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.bahaa.eventapp.dialogs.BookingDialog.isOld;
import static com.bahaa.eventapp.dialogs.BookingDialog.isSingleDismiss;

public class PreviewDialog extends DialogFragment {

    @BindView(R.id.back_layout)
    public RelativeLayout backLayout;

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
        v = inflater.inflate(R.layout.dialog_preview, container, false);

        unbinder = ButterKnife.bind(this, v);

        setupDialogRoundedCorners();
        setGlobalFlags();
        setAnimationScale();
        animateDialog();


        return v;
    }

    @OnClick(R.id.preview_ok_button)
    void performBooking() {
        if (getDialog() != null) {
            dismissAllDialogs();
        }
    }

    @OnClick(R.id.preview_cancel_button)
    void editBooking() {
        isSingleDismiss = true;
        AnimatorSet anim = new AnimatorSet();
        AnimatorSet leftOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.left_out);
        leftOut.setTarget(backLayout);
        anim.play(leftOut);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {
                BookingDialog bookingDialog = new BookingDialog();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("booking_dialog");
                if (fragment != null) {
                    fragmentTransaction.remove(fragment);
                }

                fragmentTransaction.add(bookingDialog, "booking_dialog").commit();
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

    private void setupDialogRoundedCorners() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }
    }

    private void setGlobalFlags() {
        isOld = true;
        isSingleDismiss = false;
    }

    private void setAnimationScale() {
        int distance = 4000;
        float scale = getResources().getDisplayMetrics().density * distance;

        v.setCameraDistance(scale);
    }

    private void animateDialog() {
        AnimatorSet anim = new AnimatorSet();
        AnimatorSet rightIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.right_in);
        rightIn.setTarget(backLayout);
        anim.play(rightIn);
        anim.start();
    }

    private void dismissAllDialogs() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment instanceof DialogFragment) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismissAllowingStateLoss();
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
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
