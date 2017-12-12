package com.example.yan.androidlabs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class TestToolbar extends AppCompatActivity {
    String responseText = "You selected item 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                Log.d("Toolbar", responseText);
         //       if(responseText==null)    responseText = "You selected item 1";
                View v2 = (View)findViewById(R.id.testtoolbar);
                Snackbar.make(v2, responseText, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //fab.performClick();
                break;

            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.dialogTitle);


// Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


                break;

            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                LayoutInflater li= getLayoutInflater();
                LinearLayout rootTag = (LinearLayout)li.inflate(R.layout.customlayout, null);
                final EditText et = (EditText)rootTag.findViewById(R.id.messagename);
    //            final String sss = et.getText().toString();

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(R.string.customDialogTitle);
                LayoutInflater inflater = getLayoutInflater();

                //final View v22 = (View)findViewById(R.id.testmessagename);
                //final EditText et = (EditText)v22.findViewById(R.id.messagename);
                //final String sss = et.getText().toString();

// Add the buttons
                builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //String sss = et.getText().toString();
                        Log.d("Toolbar", "jump");

                        responseText = et.getText().toString();
                        Log.d("Toolbar", responseText);
/*                     View v2 = (View)findViewById(R.id.testtoolbar);
                        Snackbar.make(v2, responseText, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
*/
                    }
                });

                builder2.setView(rootTag);

                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

// Create the AlertDialog
                AlertDialog dialog2 = builder2.create();
                dialog2.show();

                break;
            case R.id.about:
                Toast t = Toast.makeText(this, "Version 1.0, by YanCheng", Toast.LENGTH_LONG);
                t.show();
                break;
        }
        //noinspection SimplifiableIfStatement
    /*    if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
    return true;
    }
}
