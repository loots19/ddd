package com.e.ddd.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.ddd.R;
import com.e.ddd.UserViewModel;
import com.e.ddd.WorkActivity;
import com.e.ddd.model.User;
import com.e.ddd.repositories.UserRepository;
import com.e.ddd.repositories.injection.Injection;
import com.e.ddd.repositories.injection.ViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.et_name_login)
    EditText mEditTextName;
    @BindView(R.id.et_email_login)
    EditText mEditTextMail;
    @BindView(R.id.et_password_login)
    EditText mEditTextPass;
    @BindView(R.id.button_login)
    Button mButton;
    @BindView(R.id.textViewLogin)
    TextView mTextViewLogin;

    private UserRepository mUserRepository;
    private UserViewModel mUserViewModel;
    private List<User> mUserList;
    private boolean userExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        configureViewModel();
        registerWorkmate();
        getAllUsers();

        mButton.setOnClickListener(v -> registerNewWorkmate());
        mTextViewLogin.setOnClickListener(v -> startRegisterActivity());
    }

    // --------------------------------------------------------------------
    // --- get all input text check if is good and register in fireBase ---
    // --------------------------------------------------------------------
    private void registerNewWorkmate() {
        mProgressBar.setVisibility(View.VISIBLE);

        String name, email, password;
        name = mEditTextName.getText().toString().trim();
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
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Please_enter_name), Toast.LENGTH_LONG).show();
            return;
        }

        mUserViewModel.register(email, password);


    }
    // -------------------------------------------------------
    // ----------------- Launch activity -----------------
    // -------------------------------------------------------
    private void startWorkActivity() {
        Intent intent = new Intent(this, WorkActivity.class);
        startActivity(intent);
        finish();
    }
    private void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    // ---------------------------------
    // ----- Configuring ViewModel -----
    // ---------------------------------
    private void configureViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.mUserViewModel = new ViewModelProvider(this,viewModelFactory).get(UserViewModel.class);
    }

    // ---------------------------------
    // ----- Configuring Observers -----
    // ---------------------------------
    private void registerWorkmate() {
        mUserViewModel.getUserLiveData().observe(this, fireBaseUser -> {
            if (fireBaseUser != null) {
                createWorkmates();
                startWorkActivity();
            }
        });
    }
    private void getAllUsers(){
        mUserViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mUserList = users;
                checkUserExists();
            }
        });
    }

    // ---------------------------------------
    // ----- Create workmate in fireBase -----
    // ---------------------------------------
    private void createWorkmates() {
        mUserViewModel.createUser(mEditTextName.getText().toString(), mEditTextMail.getText().toString().trim());
    }
    // -------------------------------------
    // ----- Check if workmates exists -----
    // -------------------------------------
    private void checkUserExists() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (mUserList != null) {
                int size = mUserList.size();
                for (int i = 0; i < size; i++) {
                    if (mUserList.get(i).getUserEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        userExist = true;
                        break;
                    }
                }
                if (userExist) {
                    startWorkActivity();
                }
            }
        }

    }
}
