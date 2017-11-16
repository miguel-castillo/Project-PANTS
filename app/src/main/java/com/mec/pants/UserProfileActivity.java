package com.mec.pants;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private TextView mUsernameText;
    private TextView mEmailText;
    private TextView mPhoneNumberText;
    private ImageView mImageView;
    private Button mEditProfileButton;
    private Button mViewUsersButton;
    private Button mLogoutButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mUsernameText = (TextView) findViewById(R.id.usernameText);
        mEmailText = (TextView) findViewById(R.id.emailText);
        mPhoneNumberText = (TextView) findViewById(R.id.phoneNumberText);
        mImageView = (ImageView) findViewById(R.id.profileImageView);
        mEditProfileButton = (Button) findViewById(R.id.editProfileButton);
        mViewUsersButton = (Button) findViewById(R.id.upViewUsersButton);
        mLogoutButton = (Button) findViewById(R.id.upLogoutButton);

        mViewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this, ViewUsersActivity.class));
                finish();
                return;
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.vuLogoutButton) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, EditUserActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        String userID = mAuth.getCurrentUser().getUid();

        mRef.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = "";
                String email = "";
                String phoneNumber = "";
                Image profilePic;
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    switch (key.getKey()){
                        case "Name":
                            name = key.getValue().toString();
                            break;
                        case "Email":
                            email = key.getValue().toString();
                            break;
                        case "PhoneNumber":
                            phoneNumber = key.getValue().toString();
                            break;
                        case "ProfileImage":

                            break;
                        default:
                            break;
                    }
                }
                mUsernameText.setText(name);
                mEmailText.setText(email);
                mPhoneNumberText.setText(phoneNumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
