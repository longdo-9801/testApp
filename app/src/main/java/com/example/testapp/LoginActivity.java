package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
    private String email;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Button returnButton = (Button) findViewById(R.id.buttonLogin);
        Button back = (Button) findViewById(R.id.loginBack);
        Button registerButton = (Button) findViewById(R.id.buttonGoToRegister);
        EditText textEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        EditText textPass = (EditText) findViewById(R.id.editTextLoginPass);

        Button cheat = findViewById(R.id.buttonForget);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(LoginActivity.this,MainActivity.class);
                resultIntent.putExtra("isLogin",true);
//                Toast.makeText(LoginActivity.this,"DEBUG2", Toast.LENGTH_SHORT).show();
                if (textEmail.getText() != null) {
                    email = textEmail.getText().toString();
                } if (textPass.getText() != null) {
                    password = textPass.getText().toString();
                }
                //Toast.makeText(LoginActivity.this,"DEBUG2", Toast.LENGTH_LONG).show();
                if (!email.equals("") && !password.equals("")) {
                    signIn(email,password);
                    setResult(RESULT_OK,resultIntent);
                }


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        cheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEmail.setText("a@test.com");
                textPass.setText("123456");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void signIn(String email, String password) {
        //Toast.makeText(LoginActivity.this,"DEBUG3", Toast.LENGTH_LONG).show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this,"LOGIN SUCCESS", Toast.LENGTH_LONG).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            globalSingleton.setCurrentUser(email, new CallbackUser() {
                                @Override
                                public void firestoreCallBack(User result) {
                                }
                            });
                            updateUI(user);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
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