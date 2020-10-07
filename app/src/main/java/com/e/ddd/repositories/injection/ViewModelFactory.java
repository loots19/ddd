package com.e.ddd.repositories.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.e.ddd.UserViewModel;
import com.e.ddd.repositories.UserRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final UserRepository mUserRepository;

    public ViewModelFactory(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(mUserRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
