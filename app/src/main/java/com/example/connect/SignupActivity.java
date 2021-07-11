package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText email, password, name;
    Button login, signup;
    FirebaseAuth auth;
    FirebaseFirestore data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        name = findViewById(R.id.name);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_str, password_str, name_str;
                email_str= email.getText().toString();
                password_str = password.getText().toString();
                name_str = name.getText().toString();

                if(TextUtils.isEmpty(email_str)){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password_str)){
                    password.setError("Password is required");
                    return;
                }

//                if(password_str.length()<6) {
//                    password.setError("Password length must be greater than or equal to 6 characters");
//                    return;
//                }
                auth.createUserWithEmailAndPassword(email_str,password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userID = auth.getCurrentUser().getUid();
                            String email_str= email.getText().toString();
                            String password_str = password.getText().toString();
                            String name_str = name.getText().toString();

                            //System.out.println(userID);
                            Map<String,String>user = new HashMap<>();
                            user.put("Name",name_str);
                            user.put("Email",email_str);
                            user.put("Password",password_str);
                            FirebaseFirestore.getInstance().collection("Users").document(userID)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(SignupActivity.this,"Account is created",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                        }
                                    });
                            //Log.d("USERID",userID);

                        }
                        else{
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }


}