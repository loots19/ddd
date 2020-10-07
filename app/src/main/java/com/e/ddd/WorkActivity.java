package com.e.ddd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkActivity extends AppCompatActivity {
    @BindView(R.id.tvNameWork)
    TextView mTextViewName;
    @BindView(R.id.tvDateWork)
    TextView mTextViewDate;
    @BindView(R.id.etPlaceWork)
    EditText mEditTextPlace;
    @BindView(R.id.etTimeWork)
    EditText mEditTextTime;
    @BindView(R.id.etTimeSuppWork)
    EditText mEditTextSupp;
    @BindView(R.id.btn_record)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        ButterKnife.bind(this);
    }
}
