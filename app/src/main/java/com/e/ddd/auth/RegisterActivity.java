package com.e.ddd.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.e.ddd.R;
import com.e.ddd.UserViewModel;
import com.e.ddd.WorkActivity;
import com.e.ddd.repositories.UserRepository;
import com.e.ddd.repositories.injection.Injection;
import com.e.ddd.repositories.injection.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.et_password_register)
    EditText mEditTextPass;
    @BindView(R.id.et_email_register)
    EditText mEditTextMail;
    @BindView(R.id.progressBarRegister)
    ProgressBar mProgressBar;
    @BindView(R.id.button_register)
    Button mButton;

    private UserViewModel mUserViewModel;
    private UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        configureViewModel();
        LoginWorkmate();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
    }

    private void startWorkActivity() {
        Intent intent = new Intent(this, WorkActivity.class);
        startActivity(intent);
    }
    // -------------------------------------
    // ----- authenticate the workmate -----
    // -------------------------------------
    private void LogIn() {
        mProgressBar.setVisibility(View.VISIBLE);
        String email, password;
        email = mEditTextMail.getText().toString();
        password = mEditTextPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Please_enter_email), Toast.LENGTH_LONG).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEditTextMail.setError(getString(R.string.input_error_email_invalid));
            mEditTextMail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_Successfully), Toast.LENGTH_LONG).show();
        }
        mUserViewModel.login(email, password);

    }

    // ---------------------------------
    // ----- Configuring ViewModel -----
    // ---------------------------------
    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mUserViewModel = new ViewModelProvider(this, mViewModelFactory).get(UserViewModel.class);

    }

    // ---------------------------------
    // ----- Configuring Observers -----
    // ---------------------------------
    private void LoginWorkmate() {
        mUserViewModel.getUserLiveData().observe(this, fireBaseUser -> {
            if (fireBaseUser != null) {
                startWorkActivity();
            }
        });
    }


}
