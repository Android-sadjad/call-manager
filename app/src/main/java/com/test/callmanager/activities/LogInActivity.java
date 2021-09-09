package com.test.callmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.test.callmanager.R;
import com.test.callmanager.classes.MySharedPreferences;
import com.test.callmanager.classes.UseFullMethod;
import com.test.callmanager.models.LoginInfo;


public class LogInActivity extends AppCompatActivity {

    View viewHeader;
    TextView tvLogin;

    TextInputEditText tieUserName;
    TextInputEditText tiePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        configuration();
    }


    private void findViews() {

        viewHeader = findViewById(R.id.view_header);
        tvLogin = findViewById(R.id.tv_login);
        tiePassword = findViewById(R.id.tie_password);
        tieUserName = findViewById(R.id.tie_user_name);
    }

    private void configuration() {

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evaluate()) {


                    LoginInfo loginInfo = new LoginInfo(tieUserName.getText().toString().trim(), tiePassword.getText().toString().trim());
                    sendLoginRequest(loginInfo);
                    MySharedPreferences.getInstance(LogInActivity.this).putLoginInfo(loginInfo);

                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                }
            }
        });


    }

    private void sendLoginRequest(LoginInfo loginInfo) {


    }


    private boolean evaluate() {

        if (tieUserName.getText().length() == 0) {
            tieUserName.requestFocus();
            tieUserName.setError("این فیلد را وارد کنید");
            return false;
        }

        if (tiePassword.getText().length() == 0) {
            tiePassword.requestFocus();
            tiePassword.setError("این فیلد را وارد کنید");
            return false;
        }

        return true;
    }
}