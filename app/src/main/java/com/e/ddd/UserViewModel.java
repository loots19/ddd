package com.e.ddd;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.ddd.model.User;
import com.e.ddd.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository mUserRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public UserViewModel(UserRepository userRepository){
        this.mUserRepository = userRepository;
        userLiveData = mUserRepository.getUserLiveData();
    }
    // -----------------------------------------------------------
    // ----- Create a user in fireBase login activity -----
    // -----------------------------------------------------------
    public void CreateUser(String userName,String userMail){
        mUserRepository.createUsers(userName,userMail);
    }
    public void login(String userName,String userEmail){
        mUserRepository.LogIn(userName,userEmail);
    }
    public void register(String userName, String password){
        mUserRepository.register(userName,password);
    }
    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
    public void createUser(String userName,String userMail){
        mUserRepository.createUsers(userName,userMail);
    }
    public LiveData<List<User>> getAllUsers(){
        return mUserRepository.getWorkmateList();
    }












}
