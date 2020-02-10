package com.example.firebasetestreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final Map<String, Object> user = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getInfo();
        goBack();
    }

    public void getInfo() {
        final EditText username = (EditText) findViewById(R.id.usernameEntered);
        final EditText password = (EditText) findViewById(R.id.passwordEntered);
        final Button button = (Button) findViewById(R.id.createAccountButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user.put("Username", username.getText().toString());
                user.put("Password", password.getText().toString());
                user.put("Score", "0");

                db.collection("user")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });


                Intent i2 = new Intent(Registration.this, MainActivity.class);
                startActivity(i2);
            }
        });

    }

    public void goBack() {
        final Button button = (Button) findViewById(R.id.backButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Registration.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

}