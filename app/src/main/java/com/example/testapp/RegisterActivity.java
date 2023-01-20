package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapp.Object.GlobalSingleton;
import com.example.testapp.Object.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
    private Boolean isRegisterSuccess = false;
    private String email;
    private String password;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button registerButton = (Button) findViewById(R.id.buttonRegister);
        Button back = (Button) findViewById(R.id.regisBack);
        EditText textEmail = (EditText) findViewById(R.id.editTextRegisEmail);
        EditText textName = (EditText) findViewById(R.id.editTextRegisName);
        EditText textPass = (EditText) findViewById(R.id.editTextRegisPass);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(RegisterActivity.this,"DEBUG2", Toast.LENGTH_SHORT).show();
                if (textEmail.getText() != null) {
                    email = textEmail.getText().toString();
                } if (textPass.getText() != null ) {
                    password = textPass.getText().toString();
                } if (textName.getText() != null ) {
                    name = textName.getText().toString();
                }
                registerUser(email,password,name);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void registerUser(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            globalSingleton.createNewUser(new User(email,name));
                            updateUI(user);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}