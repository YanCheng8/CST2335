package com.example.yan.androidlabs;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MessageFragment extends Fragment {
    ChatWindow chatWindow;
    TextView myText1,myText2;
    Button btDelete;
    String messageId;

    public MessageFragment() {
        // Required empty public constructor
    }


    public static MessageFragment newInstance(ChatWindow chatWindow) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.chatWindow = chatWindow;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String myValue   = bundle.getString("message");
        messageId = bundle.getString("messageId");
 //       mId= bundle.getLong("mId");
        View myView = inflater.inflate(R.layout.fragment_message, container, false);

        btDelete   = (Button)myView.findViewById(R.id.deletemsgbutton2);
        btDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(chatWindow==null) {//on phone
                    // Code here executes on main thread after user presses button
                    Intent intent = new Intent(getActivity(), ChatWindow.class);
                    //Intent intent = new Intent();
                    //getActivity().setResult(Activity.RESULT_OK, data);
                    getActivity().setResult(Integer.parseInt(messageId), intent);//void setResult (int resultCode, Intent data)
                    //  Call this to set the result that your activity will return to its caller.
                    getActivity().finish();
                    //startActivityForResult(intent, Integer.parseInt(messageId));
                }
                else//on tablet
                {
                    chatWindow.deleteTabletMsg(Integer.parseInt(messageId));
                }
            }
        });

        myText2 = (TextView) myView.findViewById(R.id.textViewvv2v);
        myText2.setText("");
        myText2.setText(messageId);
        myText1 = (TextView) myView.findViewById(R.id.textViewvv1v);
        myText1.setText("");
        myText1.setText(myValue);

        // return inflater.inflate(R.layout.activity_message_details, container, false);
        return myView;
  //      return inflater.inflate(R.layout.fragment_message, container, false);
    }


}
