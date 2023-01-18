package com.example.testapp.Object;

import com.example.testapp.Object.Game;

import java.util.Collections;
import java.util.List;

public class User {
    private String id;
    private String email;
    private List<String> gameList;
    private double accountBalance;

    public User(String id, String email) {
        this.id = id;
        this.email = email;

        this.accountBalance = 0;
    }

    public User(String id, String email,List<String> gameList) {
        this.id = id;
        this.email = email;
        this.gameList = gameList;
        this.accountBalance = 0;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getGameList() {
        return gameList;
    }

    public void setGameList(List<String> gameList) {
        this.gameList = gameList;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void deductBalance(double amount) {
        if (accountBalance > amount) {
            setAccountBalance( (getAccountBalance() - amount));
        }
    }

    public  void addFund(double amount) {
        setAccountBalance( (getAccountBalance() + amount));
    }

    public void addGame(Game game) {
        if (accountBalance > game.getSalePrice()) {
            gameList.add(game.getId());
            setAccountBalance( (getAccountBalance() - game.getSalePrice()));
        }

    }


}
