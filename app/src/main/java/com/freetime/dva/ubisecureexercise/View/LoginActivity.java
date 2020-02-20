package com.freetime.dva.ubisecureexercise.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.freetime.dva.ubisecureexercise.R;
import com.freetime.dva.ubisecureexercise.Services.DatabaseHelper;
import com.freetime.dva.ubisecureexercise.Services.InputValidation;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;
    private InputValidation inputValidation;
    private TextInputEditText username, password;
    private Button loginButton, registerButton;
    private SharedPreferences sp;
    private DatabaseHelper databaseHelper;
    private TextInputLayout textInputLayoutUsername, textInputLayoutPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();

    }

    private void initViews(){
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutLoginUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutLoginPassword);
        username = (TextInputEditText) findViewById(R.id.loginUsername);
        password = (TextInputEditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.loginRegister);
    }

    private void initListeners(){
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginButton:
                verifyFromSqLite();
                break;
            case R.id.loginRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }

    }

    private void verifyFromSqLite(){
        if(!inputValidation.isEditTextFilled(username, textInputLayoutUsername, getString(R.string.error_message_username))){
            return;
        }
        if(!inputValidation.isEditTextFilled(password, textInputLayoutPassword, getString(R.string.error_message_password))){
            return;
        }
        if(databaseHelper.loginCheck(username.getText().toString().trim(), password.getText().toString().trim())){

            Intent menuIntent = new Intent(activity, ShowInfoActivity.class);

            menuIntent.putExtra("USERNAME", username.getText().toString().trim());
            emptyInputEditText();
            startActivity(menuIntent);


        }
        else {
            Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }


    }

    private void emptyInputEditText(){
        username.setText(null);
        password.setText(null);
    }
}
