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

    ListView mListView;
    TextView mTextView;

    List<User> listofUsers;

    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        mListView = (ListView) findViewById(R.id.usersListView);
        mTextView = (TextView) findViewById(R.id.testView);
        listofUsers = new ArrayList<>();
        users = FirebaseDatabase.getInstance().getReference().child("Users");

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
                    //mTextView.setText(test);
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
