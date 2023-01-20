package com.example.testapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.Controller.storeGameViewAdapter;
import com.example.testapp.Object.GlobalSingleton;
import com.example.testapp.Object.User;

public class MainActivity extends AppCompatActivity {
    GlobalSingleton globalSingleton =  GlobalSingleton.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView header = (TextView) findViewById(R.id.appStart);
        Button loginButton = (Button) findViewById(R.id.buttonGotoLogin);


        globalSingleton.getMasterGameList();
        //header.setText("Welcome To STEAMY, please login to access the store");


        ListView gameList = (ListView) findViewById(R.id.storeGameList);

        gameList.setAdapter(
                new storeGameViewAdapter(globalSingleton.getGameList(),this));



        //isLogin = globalSingleton.getLogin();
        if (globalSingleton.getLogin()) {
            loginButton.setText("SignOut");
        } else {
            loginButton.setText("Login");
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!globalSingleton.getLogin()) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    //Intent intent = new Intent(MainActivity.this,PlaceholderActivity.class);
                    //Toast.makeText(MainActivity.this,"DEBUG", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent,100);
                } else {
                    loginButton.setText("Login");
                    header.setText("Welcome To STEAMY, please login to access the store");
                }
            }
        });

    }
    protected void onResume() {
        super.onResume();
        ListView gameList = (ListView) findViewById(R.id.storeGameList);

        gameList.setAdapter(
                new storeGameViewAdapter(globalSingleton.getGameList(),this));
        TextView header = (TextView) findViewById(R.id.appStart);
        if (globalSingleton.getCurrentUser() == null) {
            //header.setText("Welcome User");
        } else {
            header.setText("Welcome " + globalSingleton.getCurrentUser().getName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Button myButton = (Button) findViewById(R.id.buttonGotoLogin);

        switch (requestCode) {
            case 100 : {
                if (resultCode == RESULT_OK) {
                    myButton.setText("Profile");

                    ListView gameList = (ListView) findViewById(R.id.storeGameList);


                    gameList.setAdapter(
                            new storeGameViewAdapter(globalSingleton.getGameList(),this));
                    TextView header = (TextView) findViewById(R.id.appStart);
                    if (globalSingleton.getCurrentUser() == null) {
                        header.setText("Welcome User");
                    } else {
                        header.setText("Welcome " + globalSingleton.getCurrentUser().getName());
                    }

                    Toast.makeText(MainActivity.this,"DEBUG LOGIN", Toast.LENGTH_SHORT).show();
                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this,UserProfileActivity.class);
                            startActivityForResult(intent,200);
                        }
                    });
                }
                break;
            }
            case 200 : {
                if (resultCode == RESULT_OK) {
                    myButton.setText("Login");

                    ListView gameList = (ListView) findViewById(R.id.storeGameList);
                    TextView header = (TextView) findViewById(R.id.appStart);
                    header.setText("Welcome To STEAMY, please login to access the store");
                    Toast.makeText(MainActivity.this,"DEBUG LOGOUT", Toast.LENGTH_SHORT).show();

                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (!globalSingleton.getLogin()) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                //Intent intent = new Intent(MainActivity.this,PlaceholderActivity.class);
                                intent.putExtra("message", "test Message");
                                //Toast.makeText(MainActivity.this,"DEBUG", Toast.LENGTH_SHORT).show();
                                startActivityForResult(intent, 100);
                            } else {
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