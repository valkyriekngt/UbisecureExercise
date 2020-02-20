package com.freetime.dva.ubisecureexercise.View;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.freetime.dva.ubisecureexercise.R;
import com.freetime.dva.ubisecureexercise.Services.DatabaseHelper;
import com.freetime.dva.ubisecureexercise.Services.InputValidation;
import com.freetime.dva.ubisecureexercise.models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;
    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutEmail;

    private TextInputEditText editTextFirstName;
    private TextInputEditText editTextLastName;
    private TextInputEditText editTextUsername;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextEmail;

    private Button registerButton;
    private InputValidation inputValidation;
    private DatabaseHelper dbHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObject();
    }

    private void initViews(){

        textInputLayoutFirstName = (TextInputLayout) findViewById(R.id.textInputLayoutFirstName);
        textInputLayoutLastName = (TextInputLayout) findViewById(R.id.textInputLayoutLastName);
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutRegUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutRegPassword);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);

        editTextFirstName = (TextInputEditText) findViewById(R.id.registerFirstName);
        editTextLastName = (TextInputEditText) findViewById(R.id.registerLastName);
        editTextUsername = (TextInputEditText) findViewById(R.id.registerUsername);
        editTextPassword = (TextInputEditText) findViewById(R.id.registerPassword);
        editTextEmail = (TextInputEditText) findViewById(R.id.registerEmail);

        registerButton = (Button) findViewById(R.id.registerButton);


    }

    private void initListeners(){
        registerButton.setOnClickListener(this);
    }

    private void initObject(){
        inputValidation = new InputValidation(activity);
        dbHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.registerButton:
                postDataToSQLite();
                break;
        }

    }

    private void postDataToSQLite(){
        if(!inputValidation.isEditTextFilled(editTextFirstName, textInputLayoutFirstName, getString(R.string.error_message_string))){
            return;
        }
        if(!inputValidation.isEditTextFilled(editTextLastName, textInputLayoutLastName, getString(R.string.error_message_string))){
            return;
        }
        if(!inputValidation.isEditTextFilled(editTextUsername, textInputLayoutUsername, getString(R.string.error_message_string))){
            return;
        }
        if(!inputValidation.isEditTextFilled(editTextPassword, textInputLayoutPassword, getString(R.string.error_message_string))){
            return;
        }
        if(!inputValidation.isEditTextFilled(editTextEmail, textInputLayoutEmail, getString(R.string.error_message_string))){
            return;
        }
        if(!dbHelper.checkUser(editTextUsername.getText().toString().trim())){
            user.setUserName(editTextUsername.getText().toString().trim());
            user.setPassword(editTextPassword.getText().toString().trim());
            user.setFirstName(editTextFirstName.getText().toString().trim());
            user.setLastName(editTextLastName.getText().toString().trim());
            user.setEmail(editTextEmail.getText().toString().trim());

            dbHelper.addUser(user);

        }
        else{
            Snackbar.make(textInputLayoutUsername, "User Exists", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void emptyEditText(){
        editTextUsername.setText(null);
        editTextPassword.setText(null);
        editTextLastName.setText(null);
        editTextFirstName.setText(null);
        editTextEmail.setText(null);
    }
}
