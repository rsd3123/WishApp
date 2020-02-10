package com.example.firebasetestreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final Map<String, Object> user = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        guess();
    }

    public void guess() {
        final Intent intent = getIntent();

        final EditText guessNumber = (EditText) findViewById(R.id.number);
        final Button button = (Button) findViewById(R.id.guessButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String num = guessNumber.getText().toString();

                user.put("Score", num);

                db.collection("user")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        String username = intent.getStringExtra("username");
                                        String password = intent.getStringExtra("password");
                                        user.put("Username", username);
                                        user.put("Password", password);


                                        String name = document.getData().get("Username").toString();
                                        String pass = document.getData().get("Password").toString();

                                        if (name.equals(username) && pass.equals(password))
                                        {
                                            String idDoc = document.getId();
                                            db.collection("user").document(idDoc).update(user);
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
