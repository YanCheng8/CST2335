package com.example.yan.androidlabs;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    ChatDatabaseHelper chatDbHelper;
    ChatAdapter messageAdapter;
    Cursor cursor;
    ArrayList<String> chatMessage = new ArrayList<String>();
    protected static final String ACTIVITY_NAME = "ChatWindow";
    SQLiteDatabase db;
    MessageFragment messageFragment;
    Boolean fb1;


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

        fb1 = (findViewById(R.id.frameLayout) != null) ? true:false;//layout-sw600dp/activity_chat_window.xml

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                String messa=(String)adapter.getItemAtPosition(position);
                //      Log.d("**********", string);
                //      db = chatDbHelper.getWritableDatabase();
                long mId  = messageAdapter.getItemId(position);
                String messageId =String.valueOf( mId);
                /*
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                if (size.x > size.y) */
                //Tablet
                if(fb1||getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    //You can use Bundle or Class to pass message to Fragment
                    Bundle bundle = new Bundle();
                    //String myMessage = "Stackoverflow is cool!";
                    bundle.putString("message", messa );
                    bundle.putLong("mId",mId);

                    bundle.putString("messageId", messageId );
                    messageFragment = MessageFragment.newInstance(ChatWindow.this);//chatWindow not null, on tablet
                    //messageFragment.myText1.setText(string);
                    //messageFragment.myText2.setText(messageId);
                    messageFragment.setArguments(bundle);//Supply the construction arguments for this fragment.

                    getFragmentManager().beginTransaction().add(R.id.frameLayout, messageFragment).commit();//in layout-sw600dp//activity_chat_windows.xml
                    //FragmentTransaction ft = getFragmentManager().beginTransaction();
                    //abstract FragmentTransaction	add(int containerViewId, Fragment fragment)   Calls add(int, Fragment, String) with a null tag.
                    //Add a fragment to the activity state.
                } else {//on phone
                    //Please Bundle the Result Here

                    //i.putExtra("myParam", 1);

                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("message", messa);
                    intent.putExtra("messageId", messageId);

                    startActivityForResult(intent, 10);

                }
            }
        });




        chatDbHelper = new ChatDatabaseHelper(this);
  //      chatDbHelper = new ChatDatabaseHelper(this);
        db = chatDbHelper.getWritableDatabase();

     //   Cursor cursor = db.query("ChatInfo", null, null, null, null, null, null);
        cursor = db.rawQuery("select * from ChatInfo", null);

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

                echat1.setText("");
                //listView.setAdapter(new ArrayAdapter<String>(this,
                //        android.R.layout.simple_list_item_1,"pls"));

                ContentValues cValues = new ContentValues();
                cValues.put("name", newString1);
                db.insert("ChatInfo", null, cValues);

                cursor = db.rawQuery("select * from ChatInfo", null);
                chatMessage.clear();
               if(cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                        chatMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                        //           chatMessage.add(cursor.getString(1));
                        cursor.moveToNext();
                    }
                }

                messageAdapter.notifyDataSetChanged();

            }
        });
    }


    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return chatMessage.size();
        }

        public String getItem(int position) {
            return chatMessage.get(position);
        }

        public long getItemId(int position) {
            //      db = chatDbHelper.getWritableDatabase();
            cursor.moveToPosition(position);
            long dbId = 0;
            if (cursor.getCount() > position) {
                dbId = cursor.getLong(0);
            }
            //     Log.d("ww",String.valueOf(dbId));
            return dbId;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }

    public void deleteMsg(int id) {
            db = chatDbHelper.getWritableDatabase();
            db.delete("ChatInfo", "id=?", new String[]{String.valueOf(id)});

            cursor = db.rawQuery("select * from ChatInfo", null);
            chatMessage.clear();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                    chatMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                    //           chatMessage.add(cursor.getString(1));
                    cursor.moveToNext();
                }
            }
            //    db.close();
    }

    public void deleteTabletMsg(int id) {
            deleteMsg(id);
            messageAdapter.notifyDataSetChanged();

 //           listView.invalidate();//Invalidate the whole view. If the view is visible, onDraw(android.graphics.Canvas) will be called at some point in the future.
//            listView.refreshDrawableState();//all this to force a view to update its drawable state. This will cause drawableStateChanged to be called on this view. Views that are interested in the new state should call getDrawableState.
            getFragmentManager().beginTransaction().remove(messageFragment).commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 10) {   //Come back from Cell Phone delete

                int a = resultCode;
                deleteMsg(a);

                messageAdapter.notifyDataSetChanged();
//                listView.invalidate();
            }
    }



    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}
