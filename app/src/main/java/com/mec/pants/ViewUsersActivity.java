package com.mec.pants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ViewUsersActivity extends AppCompatActivity {

    private ListView mListView;

    private List<User> listofUsers;

    private FirebaseAuth mAuth;
    private DatabaseReference users;

    private String userUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        mListView = (ListView) findViewById(R.id.usersListView);
        listofUsers = new ArrayList<>();
        users = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        String userId = mAuth.getCurrentUser().getUid();
        users.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userUsername = dataSnapshot.child("Name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DatabaseReference mRef = users.child("Conversations").child(((TextView) view).getText().toString());
                mRef.setValue("");

                Intent messagesIntent = new Intent(ViewUsersActivity.this, MessageActivity.class);
                messagesIntent.putExtra("username", userUsername);
                messagesIntent.putExtra("chatroomName", ((TextView) view).getText().toString());
                startActivity(messagesIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listofUsers.clear();
                for (DataSnapshot userID : dataSnapshot.getChildren()){
                    String username = "";
                    String location = "";
                    for(DataSnapshot value : userID.getChildren()) {

                        switch (value.getKey()) {
                            case "Name":
                                username = value.getValue().toString();
                                break;
                            case "Location":
                                location = value.getValue().toString();
                                break;
                            default:
                                break;
                        }
                    }

                    User user = new User(userID.getKey(), username, location);
                    listofUsers.add(user);
                }
                UserList adapter = new UserList(ViewUsersActivity.this, listofUsers);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
