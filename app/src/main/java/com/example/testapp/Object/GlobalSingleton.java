package com.example.testapp.Object;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.testapp.Callback;
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

import java.util.List;
import java.util.Set;

public class GlobalSingleton {

    private Boolean isLogin = false;
    private User currentUser;
    //private String userID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    CollectionReference dbUser = db.collection("users");
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

    public void setCurrentUser(String email, final Callback callback) {

        dbUser.whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document);
                        currentUser = new User((String) document.get("id"),(String) document.get("email"), (List<String>) document.get("order"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });
    }

    public void addUserDEBUG() {
        db.collection("DEBUGKore").document("DEBUGDoc")
                .set(
                        new User("555","Test@com")
                );
    }

    public void getUserDEBUG() {
        db.collection("DEBUGKore").document("DEBUGDoc")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        currentUser = new User((String) document.get("id"),(String) document.get("email"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}
