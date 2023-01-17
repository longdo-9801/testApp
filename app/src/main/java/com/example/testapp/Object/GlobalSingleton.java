package com.example.testapp.Object;

public class GlobalSingleton {
    private Boolean isLogin = false;
    private User currentUser;

    private static  GlobalSingleton instance;

    public static GlobalSingleton getInstance() {
        if (instance == null) {
            instance = new GlobalSingleton();

        }
        return instance;
    }

    public void setLoginState(User user) {
        this.isLogin = true;
        this.currentUser = user;
    }

    public void setSignOut() {
        this.isLogin = false;
        this.currentUser = null;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public GlobalSingleton() {}
}
