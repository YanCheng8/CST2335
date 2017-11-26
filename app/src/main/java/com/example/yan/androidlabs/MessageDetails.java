package com.example.yan.androidlabs;

import android.app.Activity;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        //btDelete = (Button) findViewById(R.id.)
        String message   = getIntent().getStringExtra("message");
        String messageId = getIntent().getStringExtra("messageId");

        Bundle bundle = new Bundle();
        //String myMessage = "Stackoverflow is cool!";
        bundle.putString("message", message );

        bundle.putString("messageId", messageId );
        MessageFragment messageFragment = MessageFragment.newInstance(null);//if(chatWindow==null) on phone
        //messageFragment.myText1.setText(string);
        //messageFragment.myText2.setText(messageId);
        messageFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, messageFragment).commit();//in layout/activity_message_details.xml

    }
}
