package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.Controller.userGameViewAdapter;
import com.example.testapp.Object.Game;
import com.example.testapp.Object.GlobalSingleton;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    GlobalSingleton globalSingleton =  GlobalSingleton.getInstance();
    Button buttonLogOut;
    String userName;
    String balance;
    List<String> gameList = new ArrayList<>();
    List<String> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);


        TextView name = (TextView) findViewById(R.id.Username);
        TextView balance = (TextView) findViewById(R.id.accountBalance);
        Button logoutButton = (Button) findViewById(R.id.logout_Button);
        Button addFundButton = (Button) findViewById(R.id.addfundButton);
        Button backtoMain = (Button) findViewById(R.id.backButton);

        String balanceText = "$" + globalSingleton.getCurrentUser().getBalance();

        name.setText(globalSingleton.getCurrentUser().getName());
        balance.setText(balanceText);

        ListView list = (ListView) findViewById(R.id.gameListView);
        List<String> gameListId = globalSingleton.getCurrentUser().getGameList();
        List<Game> dict = globalSingleton.getGameList();
        for (int i = 0; i < gameListId.size(); i++) {
            for (int j = 0; j < dict.size(); j++) {
                if (gameListId.get(i).equals(dict.get(j).getId())) {
                    gameList.add(dict.get(j).getName());
                }
            }

        }

        list.setAdapter(
                new userGameViewAdapter(gameList,this)
        );

        addFundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSingleton.addFund();
                String updatebalanceText = "$" + globalSingleton.getCurrentUser().getBalance();
                balance.setText(updatebalanceText);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this,MainActivity.class);
                globalSingleton.logout();
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        backtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}

