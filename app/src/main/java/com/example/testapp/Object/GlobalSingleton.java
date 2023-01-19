package com.example.testapp.Object;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GlobalSingleton {

    private Boolean isLogin = false;
    private User currentUser;
    private List<Game> gameList = new ArrayList<>();
    //private String userID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    CollectionReference dbUser = db.collection("users");
    CollectionReference dbGames = db.collection("games");

    private static final String TAG = "DocSnippets";

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

    public List<Game> getGameList() {return gameList;}

    public GlobalSingleton() {}


    public void setCurrentUser(String email) {

        dbUser.whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document);
                        currentUser =
                                new User(document.getId(),
                                        (String) document.get("email"),
                                        (String) document.get("name"),
                                        (List<String>) document.get("gameList"),
                                        Double.parseDouble(document.get("balance").toString()) );
                        isLogin = true;
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });
    }

    public void logout() {
        currentUser = null;
        isLogin = false;

    }

    public void updateBuyGame(Game game) {
        currentUser.deductBalance(game.getSalePrice());
        dbUser.document(currentUser.getId())
                .update("gameList", FieldValue.arrayUnion(game.getId()));
        dbUser.document(currentUser.getId())
                .update("balance",currentUser.getAccountBalance());

    }

    public void addFund() {
        currentUser.addFund(200);
        dbUser.document(currentUser.getId())
                .update("balance",currentUser.getAccountBalance());
    }

    public void getMasterGameList() {
        dbGames.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        gameList.add(
                                new Game(
                                        document.getId(),
                                        (String) document.get("name"),
                                        (List<String>) document.get("genre"),
                                        Double.parseDouble(document.get("basePrice").toString()),
                                        Double.parseDouble(document.get("discount").toString())

                                )
                        );
                    }
                    Log.d(TAG, gameList.get(0).getName());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void createNewUser(User user) {
        dbUser.document().set(user);
    }

//    public void addUserDEBUG() {
//        db.collection("DEBUGKore").document("DEBUGDoc")
//                .set(
//                        new User("555","Test@com")
//                );
//    }
//
//    public void getUserDEBUG() {
//        db.collection("DEBUGKore").document("DEBUGDoc")
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        currentUser = new User((String) document.get("id"),(String) document.get("email"));
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//    }

}
