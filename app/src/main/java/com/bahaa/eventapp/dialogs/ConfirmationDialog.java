package com.bahaa.eventapp.dialogs;

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

import com.bahaa.eventapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConfirmationDialog extends DialogFragment {

    @BindView(R.id.back_layout)
    public RelativeLayout backLayout;

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
        View v = inflater.inflate(R.layout.dialog_confirmation, container, false);

        unbinder = ButterKnife.bind(this, v);

        setupDialogRoundedCorners();

        int distance = 4000;
        float scale = getResources().getDisplayMetrics().density * distance;

        v.setCameraDistance(scale);

        AnimatorSet anim = new AnimatorSet();
        AnimatorSet rightIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.right_in);
        rightIn.setTarget(backLayout);
        anim.play(rightIn);
        anim.start();


        return v;
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
