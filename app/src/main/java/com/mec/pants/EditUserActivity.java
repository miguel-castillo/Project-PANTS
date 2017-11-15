package com.mec.pants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {

    private EditText mEditUsername;
    private EditText mEditEmail;
    private EditText mEditPhoneNumber;
    private ImageButton mEditProfilePic;
    private Button mSave;


    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;

    private String username;
    private String email;
    private String phoneNumber;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        mEditUsername = (EditText) findViewById(R.id.editNameText);
        mEditEmail = (EditText) findViewById(R.id.editEmailText);
        mEditPhoneNumber = (EditText) findViewById(R.id.editPhoneNumberText);
        mEditProfilePic = (ImageButton) findViewById(R.id.userImage);
        mSave = (Button) findViewById(R.id.saveButton);

        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference();

        userID = mAuth.getCurrentUser().getUid();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = mEditUsername.getText().toString();
                email = mEditEmail.getText().toString();
                phoneNumber = mEditPhoneNumber.getText().toString();

                Map<String, Object> update = new HashMap<>();

                update.put("Name", username);
                update.put("Email", email);
                update.put("PhoneNumber", phoneNumber);



                mUserRef.child("Users").child(userID).updateChildren(update);

                /*startActivity(new Intent(EditUserActivity.this, UserProfileActivity.class));
                finish();
                return;*/
            }
        });

    }

}
