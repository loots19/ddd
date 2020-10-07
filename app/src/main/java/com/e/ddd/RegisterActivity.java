package com.e.ddd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkActivity();
            }
        });
    }

    private void startWorkActivity() {
        Intent intent = new Intent(this,WorkActivity.class);
        startActivity(intent);
    }
}
