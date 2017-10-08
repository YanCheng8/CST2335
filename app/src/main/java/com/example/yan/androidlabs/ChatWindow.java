package com.example.yan.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    private ListView listView;
    private EditText echat1;
    private Button bchat1;
    ChatAdapter messageAdapter;
    ArrayList<String> chatMessage = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView = (ListView)findViewById(R.id.list_view);
        echat1   = (EditText) findViewById(R.id.editTextChat);
        //listView.setAdapter();
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        bchat1 = (Button)findViewById(R.id.buttonChat);
        bchat1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                String newString1 = echat1.getText().toString();
                chatMessage.add(newString1);
                messageAdapter.notifyDataSetChanged();
                echat1.setText("");
                //listView.setAdapter(new ArrayAdapter<String>(this,
                //        android.R.layout.simple_list_item_1,"pls"));
            }
        });
    }


    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return chatMessage.size();
        }

        public  String getItem(int position){
            return chatMessage.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }
    }
}
