package com.example.testapp.Object;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.testapp.DBDemo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Set;

public class GlobalSingleton {

    private Boolean isLogin = false;
    private User currentUser;
    //private String userID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    CollectionReference dbUser = db.collection("user");
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

    public GlobalSingleton() {}


    private void updateUserToFirestore() {

        // creating a collection reference
        // for our Firebase Firestore database.


        // adding our data to our courses object class.

        // below method is use to add data to Firebase Firestore.
        //dbUser.whereEqualTo("email", currentUser.getEmail()).get().;
        dbUser.add(currentUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
            }
        });
    }

    public void setCurrentUser (String email) {

        dbUser..collection("users").whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        //userID = documentSnapshot.getId();
                        currentUser = new User(
                                documentSnapshot.getId(),
                                documentSnapshot.get("email").toString(),
                                (Set<String>) documentSnapshot.get("order")
                        );
                    }
                }

            }
        });
    }

}
