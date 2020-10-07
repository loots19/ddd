package com.e.ddd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mButton.setOnClickListener(v -> startWorkActivity());
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }


        });
    }
    private void startWorkActivity() {
        Intent intent = new Intent(this,WorkActivity.class);
        startActivity(intent);
    }
    private void startRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
