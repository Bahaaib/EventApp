package com.bahaa.eventapp.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
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

import java.text.NumberFormat;
import java.text.ParsePosition;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MobileDialog extends DialogFragment {

    @BindView(R.id.mobile_textfield_layout)
    public TextInputLayout textInputLayout;

    @BindView(R.id.mobile_textfield)
    public TextInputEditText mobileEditText;

    @BindView(R.id.mobile_ok_button)
    public AppCompatButton okButton;

    @BindView(R.id.mobile_cancel_button)
    public AppCompatButton cancelButton;

    private Unbinder unbinder;

    public MobileDialog() {
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
        View v = inflater.inflate(R.layout.dialog_mobile, container, false);

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

    @OnClick(R.id.mobile_ok_button)
    public void onOKPressed() {
        String mobileNumber = mobileEditText.getText().toString();
        mobileNumber = mobileNumber.replace("\\s+", "");

        textInputLayout.setErrorEnabled(true);

        if (mobileNumber.isEmpty()){
            textInputLayout.setError("Please type a mobile number");
            watchText(mobileEditText, textInputLayout);
        }else if (!isValidNumeric(mobileNumber)){
            textInputLayout.setError("Only numbers are allowed");
            watchText(mobileEditText, textInputLayout);
        }else if (!isValidMobileNumberFormat(mobileNumber)){
            textInputLayout.setError("Invalid mobile number format");
            watchText(mobileEditText, textInputLayout);
        }else if (!hasValidStarters(mobileNumber)){
            textInputLayout.setError("Mobile Number MUST start with 0 or +20");
            watchText(mobileEditText, textInputLayout);
        }else {
            // @todo #5 Update UI with valid mobile number
        }
    }

    @OnClick(R.id.mobile_cancel_button)
    public void onCancelPressed() {
        getDialog().dismiss();
    }

    private boolean isValidNumeric(String number) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(number, pos);
        return number.length() == pos.getIndex();
    }

    private boolean isValidMobileNumberFormat(String number) {

        return Patterns.PHONE.matcher(number).matches() && (number.length() > 10);
    }

    private boolean hasValidStarters(String number) {
        if (number.charAt(0) == '0' && number.charAt(1) == '1') {
            return true;
        } else return number.charAt(0) == '+' && number.charAt(1) == '2' && number.charAt(2) == '0';
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
