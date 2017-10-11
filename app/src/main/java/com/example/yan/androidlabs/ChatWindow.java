package com.example.yan.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    protected static final String ACTIVITY_NAME = "ChatWindow";

 //  SQLiteDatabase db;
//    ChatDatabaseHelper chatDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        listView = (ListView)findViewById(R.id.list_view);
        echat1   = (EditText) findViewById(R.id.editTextChat);
        //listView.setAdapter();
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        ChatDatabaseHelper chatDbHelper = new ChatDatabaseHelper(this);
  //      chatDbHelper = new ChatDatabaseHelper(this);
        final SQLiteDatabase db = chatDbHelper.getWritableDatabase();

        Cursor cursor = db.query("ChatInfo", null, null, null, null, null, null);
//        Cursor cursor = db.rawQuery("select * from ChatInfo",null);

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                chatMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                //           chatMessage.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }

        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount() );

        for(int i = 0; i < cursor.getColumnCount(); i++){
            System.out.println(cursor.getColumnName(i));
            	}

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

                ContentValues cValues = new ContentValues();
                cValues.put("name", newString1);
                db.insert("ChatInfo", null, cValues);

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

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
