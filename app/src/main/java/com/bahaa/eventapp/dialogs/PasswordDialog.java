package com.bahaa.eventapp.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
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

public class PasswordDialog extends DialogFragment {

    @BindView(R.id.password_old_textfield_layout)
    public TextInputLayout oldPasswordLayout;

    @BindView(R.id.password_new_textfield_layout)
    public TextInputLayout newPasswordLayout;

    @BindView(R.id.password_confirm_textfield_layout)
    public TextInputLayout confirmPasswordLayout;

    @BindView(R.id.password_old_textfield)
    public TextInputEditText oldPasswordInput;

    @BindView(R.id.password_new_textfield)
    public TextInputEditText newPasswordInput;

    @BindView(R.id.password_confirm_textfield)
    public TextInputEditText confirmPasswordInput;

    @BindView(R.id.password_ok_button)
    public AppCompatButton okButton;

    @BindView(R.id.password_cancel_button)
    public AppCompatButton cancelButton;

    private Unbinder unbinder;

    public PasswordDialog() {
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
        View v = inflater.inflate(R.layout.dialog_password, container, false);

        unbinder = ButterKnife.bind(this, v);

        setupDialogRoundedCorners();


        return v;
    }

    private void setupDialogRoundedCorners(){
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    @OnClick(R.id.password_ok_button)
    public void onOKPressed(){

        final String oldPassword = oldPasswordInput.getText().toString();
        final String newPassword = newPasswordInput.getText().toString();
        final String confirmPassword = confirmPasswordInput.getText().toString();

        if (!isValidPassword(oldPassword)){
            oldPasswordLayout.setError("Password Must be between 8 and 64 characters");
        }else {
            // @todo #6 verify old password
        }

        if (!isValidPassword(newPassword)){
            newPasswordLayout.setError("Password Must be between 8 and 64 characters");
        }else if (!confirmPassword.equals(newPassword)){
            confirmPasswordLayout.setError("Passwords are not matching");
        }else {
            // @todo #7 Update password
        }
    }

    private boolean isValidPassword(String password){
        return !password.isEmpty() && password.length() > 7;
    }

    private void watchText(TextInputEditText editText, TextInputLayout textInputLayout){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editText.setTransformationMethod(new PasswordTransformationMethod());
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
