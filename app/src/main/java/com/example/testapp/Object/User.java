package com.example.testapp.Object;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String email;
    private String name;
    private List<String> gameList;
    private double balance;

    public User( String email, String name) {
        this.email = email;
        this.name = name;
        this.balance = 0;
        this.gameList = new ArrayList<>();
    }

    public User(String id, String email, String name, List<String> gameList, double balance) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gameList = gameList;
        this.balance = balance;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGameList() {
        return gameList;
    }

    public void setGameList(List<String> gameList) {
        this.gameList = gameList;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deductBalance(double amount) {
        if (balance > amount) {
            setBalance( (getBalance() - amount));
        }
    }

    public  void addFund(double amount) {
        setBalance( (getBalance() + amount));
    }

    public void addGame(Game game) {
        if (balance > game.getSalePrice()) {
            gameList.add(game.getId());
            setBalance( (getBalance() - game.getSalePrice()));
        }

    }


}
