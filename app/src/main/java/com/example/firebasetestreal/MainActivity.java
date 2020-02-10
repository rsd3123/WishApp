package com.example.firebasetestreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register();
        signIn();
    }

    public void register(){
        final Button button = (Button) findViewById(R.id.registrationButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registration.class);
                startActivity(i);
            }
        });
    }


    public void signIn()
    {
        final EditText username = (EditText) findViewById(R.id.usernameEnteredSignIn);
        final EditText password = (EditText) findViewById(R.id.passwordEnteredSignIn);
        final Button button = (Button) findViewById(R.id.loginButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                db.collection("user")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        String name = document.getData().get("Username").toString();

                                        String pass = document.getData().get("Password").toString();

                                        if (name.equals(username.getText().toString()) && pass.equals(password.getText().toString()))
                                        {
                                            Intent a = new Intent(MainActivity.this, GameActivity.class);
                                            a.putExtra("username", document.getData().get("Username").toString());
                                            a.putExtra("password", document.getData().get("Password").toString());

                                            startActivity(a);
                                        }

                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });

            }
        });
    }
}