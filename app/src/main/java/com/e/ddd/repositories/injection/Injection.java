package com.e.ddd.repositories.injection;

import android.content.Context;

import com.e.ddd.repositories.UserRepository;

public class Injection {

    private static UserRepository provideUserRepository(Context context){
        return new UserRepository();
    }
    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserRepository mUserRepository = provideUserRepository(context);
        return new ViewModelFactory(mUserRepository);


    }
}
