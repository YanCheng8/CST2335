package com.example.yan.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.app.PendingIntent.getActivity;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    Button buttonLogin;
    SharedPreferences sharedPref;
    String nameValue;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        et1 = (EditText)findViewById(R.id.text_EmailAddress);


        sharedPref = getSharedPreferences("activity_login", Context.MODE_PRIVATE);
  //      sharedPref = getPreferences(Context.MODE_PRIVATE);
        nameValue = sharedPref.getString("DefaultEmail", "email@domain.com");
//        String nameValue = sharedPref.getString(R.string."text_EmailAddress");
        et1.setText(nameValue);

        buttonLogin = (Button)findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String newString1 =  et1.getText().toString();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("DefaultEmail", newString1);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }

        } );
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



    public void callback () {

    }

}
