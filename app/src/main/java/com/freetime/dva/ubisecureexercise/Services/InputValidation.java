package com.freetime.dva.ubisecureexercise.Services;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputValidation {
    private Context context;

    public InputValidation(Context context){
        this.context = context;
    }

    public boolean isEditTextFilled(TextInputEditText editText, TextInputLayout layout, String message){
        String value = editText.getText().toString().trim();
        if (value.isEmpty()){
            layout.setError(message);
            hideKeyboardFrom(editText);
            return false;
        } else{
            layout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isEditTextMatches(EditText editText1, EditText editText2, TextInputLayout layout, String message){
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if(!value1.contentEquals(value2)){
            layout.setError(message);
            hideKeyboardFrom(editText2);
            return false;
        }else {
            layout.setErrorEnabled(false);

        }
        return true;
    }

    private void hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
