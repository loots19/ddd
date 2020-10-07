package com.e.ddd.repositories;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.e.ddd.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class UserRepository {
    private User mUser;
    private MutableLiveData<FirebaseUser> userLiveData;


    public UserRepository(){
        this.userLiveData = new MutableLiveData<>();
    }
    // --------------------------------
    // ----- COLLECTION REFERENCE -----
    // --------------------------------
    private static CollectionReference getUserCollection() {
        return FirebaseFirestore.getInstance().collection("user");
    }

    // ------------------
    // ----- CREATE -----
    // ------------------
    public Task<Void> createUsers(String userName, String userMail) {
        User userToCreate = new User(userName, userMail);
        String uid = FirebaseAuth.getInstance().getUid();
        return getUserCollection().document(Objects.requireNonNull(uid)).set(userToCreate);

    }
    // ---------------
    // ----- GET -----
    // ---------------

    public MutableLiveData<User> getUserName(){
        MutableLiveData<User> mutableLiveData = new MutableLiveData<>();
        String uid = FirebaseAuth.getInstance().getUid();
        getUserCollection().document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    User user = documentSnapshot.toObject(User.class);
                    mutableLiveData.setValue(user);
                }
            }
        });
        return mutableLiveData;
    }
    // -------------------------------------------
    // ----- Get workmateList from firesTore -----
    // -------------------------------------------
    public MutableLiveData<List<User>> getWorkmateList() {
        MutableLiveData<List<User>> mUserList = new MutableLiveData<>();
        getUserCollection().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null) {
                List<DocumentSnapshot> userList = queryDocumentSnapshots.getDocuments();
                List<User> users = new ArrayList<>();
                int size = userList.size();
                for (int i = 0; i < size; i++) {
                    User user = userList.get(i).toObject(User.class);
                    users.add(user);
                }
                mUserList.setValue(users);

            }
        });

        return mUserList;

    }

    // ------------------------------------------------------------------
    // ----- register Workmates with email and password in fireBase -----
    // ------------------------------------------------------------------
    @SuppressLint("RestrictedApi")
    public void register(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getApplicationContext().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        userLiveData.postValue(FirebaseAuth.getInstance().getCurrentUser());
                    } else {
                        Toast.makeText(getApplicationContext().getApplicationContext(), "Registration Failure: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // ------------------------------------------------------------------
    // ----- Login Workmates with email and password in fireBase -----
    // ------------------------------------------------------------------
    @SuppressLint("RestrictedApi")
    public void LogIn(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getApplicationContext().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        userLiveData.postValue(FirebaseAuth.getInstance().getCurrentUser());
                    } else {
                        Toast.makeText(getApplicationContext().getApplicationContext(), "Registration Failure: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    // -----------------
    // ----- UTILS -----
    // -----------------

    @Nullable
    private FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private Boolean isCurrentUserLogged() {
        return (this.getCurrentUser() != null);
    }

    @SuppressLint("RestrictedApi")
    private OnFailureListener onFailureListener() {
        return e -> Toast.makeText(getApplicationContext(), "unknown_error", Toast.LENGTH_LONG).show();
    }














}
