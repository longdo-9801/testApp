package com.example.testapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testapp.Object.GlobalSingleton;

public class MainActivity extends AppCompatActivity {
    Boolean isLogin;
    GlobalSingleton globalSingleton =  GlobalSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myButton = (Button) findViewById(R.id.buttonGotoLogin);
        Button DBDemoButton = (Button) findViewById(R.id.buttonToDatabase);

        isLogin = globalSingleton.getLogin();
        if (isLogin) {
            myButton.setText("SignOut");
        } else {
            myButton.setText("Login");
        }
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLogin) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    //Intent intent = new Intent(MainActivity.this,PlaceholderActivity.class);
                    intent.putExtra("message","test Message");
                    //Toast.makeText(MainActivity.this,"DEBUG", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent,100);
                } else {
                    isLogin = false;
                    myButton.setText("Login");
                }
            }
        });

        DBDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDB = new Intent(MainActivity.this, DBDemo.class);
                startActivity(intentDB);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Button myButton = (Button) findViewById(R.id.buttonGotoLogin);

        switch (requestCode) {
            case 100 : {
                if (resultCode == RESULT_OK) {
                    isLogin = true;
                    myButton.setText("Profile");
                    Toast.makeText(MainActivity.this,"DEBUG LOGIN", Toast.LENGTH_SHORT).show();
                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this,PlaceholderActivity.class);
                            startActivityForResult(intent,200);
                        }
                    });
                }
                break;
            }
            case 200 : {
                if (resultCode == RESULT_OK) {
                    isLogin = false;
                    myButton.setText("Login");
                    Toast.makeText(MainActivity.this,"DEBUG LOGOUT", Toast.LENGTH_SHORT).show();

                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!isLogin) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                //Intent intent = new Intent(MainActivity.this,PlaceholderActivity.class);
                                intent.putExtra("message", "test Message");
                                //Toast.makeText(MainActivity.this,"DEBUG", Toast.LENGTH_SHORT).show();
                                startActivityForResult(intent, 100);
                            } else {
                                isLogin = false;
                                myButton.setText("Login");
                            }
                        }
                    });
                }
                break;
            }
        }
    }
}