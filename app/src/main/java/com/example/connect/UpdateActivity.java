package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    EditText  password, name;
    Button update;
    FirebaseAuth auth;
    FirebaseFirestore data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        auth = FirebaseAuth.getInstance();
        data = FirebaseFirestore.getInstance();

        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_str, password_str, userID,email_str;
                name_str=name.getText().toString();
                password_str=password.getText().toString();
                userID = auth.getCurrentUser().getUid();
                email_str = auth.getCurrentUser().getEmail();
                Map<String,String> user = new HashMap<>();
                user.put("Name",name_str);
                user.put("Email",email_str);
                user.put("Password",password_str);
                data.collection("Users").document(userID)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateActivity.this,"Account is updated",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}