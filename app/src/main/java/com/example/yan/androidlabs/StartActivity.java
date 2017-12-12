package com.example.yan.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button bts1 = (Button)findViewById(R.id.button);
        bts1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivityForResult(new Intent(StartActivity.this, ListItemsActivity.class), 10);
            }

        } );

        Button bt_weather = (Button)findViewById(R.id.button_weather);
        bt_weather.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //          startActivity(new Intent(StartActivity.this, WeatherForecast.class));
                startActivityForResult(new Intent(StartActivity.this, WeatherForecast.class), 10);

            }

        } );

        Button st1 = (Button)findViewById(R.id.startchat) ;
        st1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                //Start New Activity
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");

            }
        });

        Button tb = (Button)findViewById(R.id.button_toolbar);
        tb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, TestToolbar.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "User clicked Test Toolbar");
            }

        });

    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data)  {
        if (requestCode == 10)  {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }

        if(responseCode==Activity.RESULT_OK){
            //String result = data.getExtras().getString("Response");
            String result = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this , result, Toast.LENGTH_LONG); //this is the ListActivity
            toast.show(); //display your message box
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}
