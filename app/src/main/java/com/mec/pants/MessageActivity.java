package com.mec.pants;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private Button mSendMessage;
    private TextView mDisplayMessage;
    private EditText mInputMessage;
    private String roomName, username;
    private DatabaseReference room;
    String tempKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mSendMessage = (Button) findViewById(R.id.sendMessageButton);
        mDisplayMessage = (TextView) findViewById(R.id.displayMessage);
        mInputMessage = (EditText) findViewById(R.id.inputMessage);

        roomName = getIntent().getExtras().get("chatroomName").toString();
        username = getIntent().getExtras().get("username").toString();
        setTitle("Room - " + roomName);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        room = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Conversations").child(roomName);

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> uniqueKeyMap = new HashMap<String, Object>();
                tempKey = room.push().getKey();
                room.updateChildren(uniqueKeyMap);

                DatabaseReference userRef = room.child(tempKey);
                Map<String, Object> userMessageMap = new HashMap<String, Object>();
                userMessageMap.put("name", username);
                userMessageMap.put("message", mInputMessage.getText().toString());

                userRef.updateChildren(userMessageMap);

                mInputMessage.setText("");
                mInputMessage.requestFocus();

            }
        });

        room.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                appendChatConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                appendChatConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    String chatMessage, chatUsername;

    private void appendChatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){
            chatMessage = (String) ((DataSnapshot) i.next()).getValue();
            chatUsername = (String) ((DataSnapshot) i.next()).getValue();

            mDisplayMessage.append(chatUsername + " : " + chatMessage + "\n \n");
        }
    }
}
