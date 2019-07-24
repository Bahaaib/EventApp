package com.bahaa.eventapp.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.bahaa.eventapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UsernameDialog extends DialogFragment {

    @BindView(R.id.username_textfield_layout)
    public TextInputLayout usernameLayout;

    @BindView(R.id.username_textfield)
    public TextInputEditText usernameInput;

    @BindView(R.id.username_ok_button)
    public AppCompatButton okButton;

    @BindView(R.id.username_cancel_button)
    public AppCompatButton cancelButton;

    private Unbinder unbinder;

    public UsernameDialog() {
    }

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
        View v = inflater.inflate(R.layout.dialog_username, container, false);

        unbinder = ButterKnife.bind(this, v);

        setupDialogRoundedCorners();

        return v;
    }

    private void setupDialogRoundedCorners() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    @OnClick(R.id.username_ok_button)
    public void onOKPressed(){
        final String username = usernameInput.getText().toString();

        if (username.isEmpty()){
            usernameLayout.setError("Username cannot be empty");
            watchText(usernameInput, usernameLayout);
        }else if (!isValidUsername(username)){
            usernameLayout.setError("Username MUST be 20 letters maximum");
            watchText(usernameInput, usernameLayout);
        }else {
            // @todo #8 Update Username
        }
    }

    @OnClick(R.id.username_cancel_button)
    public void onCancelPressed(){
        getDialog().dismiss();
    }

    private boolean isValidUsername(String username){
        return username.length() > 0 && username.length() < 20;
    }

    private void watchText(TextInputEditText editText, TextInputLayout textInputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayout.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
