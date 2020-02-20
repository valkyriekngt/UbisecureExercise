package com.freetime.dva.ubisecureexercise.View;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.freetime.dva.ubisecureexercise.Interface.IMyApiEndpoint;
import com.freetime.dva.ubisecureexercise.R;
import com.freetime.dva.ubisecureexercise.Services.DatabaseHelper;
import com.freetime.dva.ubisecureexercise.Services.RetrofitClientInstance;
import com.freetime.dva.ubisecureexercise.models.JsonTrainObject;
import com.freetime.dva.ubisecureexercise.models.User;



import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = ShowInfoActivity.this;
    private TextView showFirstName;
    private TextView showLastName;
    private TextView showUsername;
    private TextView showEmail;
    private TextView latitude, longitude, destination, trainName, speed;
    private Button logoutButton, deleteButton;


    private DatabaseHelper dbHelper;
    private User user;
    private String username;
    private List<User> allUsers;
    private double lat, lon;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        initView();
        initListeners();
        initObjects();



    }

    private void initView(){
        showFirstName = (TextView) findViewById(R.id.showFirstName);
        showLastName = (TextView) findViewById(R.id.showLastName);
        showUsername = (TextView) findViewById(R.id.showUsername);
        showEmail = (TextView) findViewById(R.id.showEmail);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        destination = (TextView) findViewById(R.id.destination);
        trainName = (TextView) findViewById(R.id.trainName);
        logoutButton = (Button) findViewById(R.id.logout);
        deleteButton= (Button) findViewById(R.id.deleteUser);
        speed = (TextView) findViewById(R.id.speed);

    }
    private void initListeners(){
        logoutButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

    }

    private void initObjects(){
        dbHelper = new DatabaseHelper(activity);
        username = getIntent().getStringExtra("USERNAME");
        getDataFromSQL();
        getTrainData();



    }

    private void getDataFromSQL(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                user = dbHelper.getCurrentUser(username);
                allUsers = dbHelper.getAllUser();

                for(User users : allUsers){
                    if(users.getUserName().equals(username)){
                        showFirstName.setText("First Name:  " +users.getFirstName());
                        showLastName.setText("Last Name:  "+users.getLastName());
                        showUsername.setText("Username:  "+users.getUserName());
                        showEmail.setText("Email:  " +users.getEmail());
                    }

                }
                return null;

            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void getTrainData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                IMyApiEndpoint endpoint = RetrofitClientInstance.getRetrofitInstance().create(IMyApiEndpoint.class);
                Call<JsonTrainObject> call = endpoint.getTrain();
                call.enqueue(new Callback<JsonTrainObject>() {
                    @Override
                    public void onResponse(Call<JsonTrainObject> call, Response<JsonTrainObject> response) {
                        generateTrain(response.body());
                    }

                    @Override
                    public void onFailure(Call<JsonTrainObject> call, Throwable t) {
                        Toast.makeText(ShowInfoActivity.this, "Something wrong", Toast.LENGTH_LONG);
                    }
                });
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
            }
        }.execute();
        }


    private void generateTrain(JsonTrainObject trainObject){
        lat = trainObject.getLatitude();
        lon = trainObject.getLongitude();
        destination.setText(trainObject.getDestination());
        latitude.setText(Double.toString(trainObject.getLatitude()));
        longitude.setText(Double.toString(trainObject.getLongitude()));
        trainName.setText(trainObject.getName());
        speed.setText(Double.toString(trainObject.getSpeed()));

    }



    public void showMap(View view) {
                Intent openMap = new Intent(Intent.ACTION_VIEW);
                openMap.setData(Uri.parse("geo:"+ lat+", "+lon));
                startActivity(openMap);
    }

    /*
    public void deleteUser(View view){

        DatabaseHelper db = new DatabaseHelper(this);
        db.deleteUser(username);
        Intent intentDelete = new Intent(getApplicationContext(), LoginActivity.class);
        intentDelete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentDelete);

    }
    */

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.deleteUser:
                DatabaseHelper db = new DatabaseHelper(this);
                db.deleteUser(username);
                Intent intentDelete = new Intent(getApplicationContext(), LoginActivity.class);
                intentDelete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentDelete);
                break;


            case R.id.logout:
                Intent intentLogout = new Intent(getApplicationContext(), LoginActivity.class);
                intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLogout);
                break;



        }
    }
}
