package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PlaceholderActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private Boolean isLoginSuccess;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Button returnButton = (Button) findViewById(R.id.buttonBack);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaceholderActivity.this,MainActivity.class);
                intent.putExtra("message","FAQ");
                Toast.makeText(PlaceholderActivity.this,"DEBUG2", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK,intent);
                finish();
            }


        });

    }



    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}