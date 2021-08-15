package com.test.callmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.test.callmanager.R;
import com.test.callmanager.classes.MySharedPreferences;
import com.test.callmanager.classes.MyUtilsMethod;
import com.test.callmanager.models.LoginInfo;


public class LogInActivity extends AppCompatActivity {


    TextView tvLogin;
    TextInputEditText tieUserName;
    TextInputEditText tiePassword;
    View viewHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        setViewSize();
        configuration();
    }


    private void findViews() {

        viewHeader =findViewById(R.id.view_header);
        tvLogin =findViewById(R.id.btn_login);
        tiePassword =findViewById(R.id.tie_password);
        tieUserName =findViewById(R.id.tie_user_name);

    }

    private void setViewSize(){

        viewHeader.setLayoutParams(new ConstraintLayout
                .LayoutParams(MyUtilsMethod.getScreenWidth(),MyUtilsMethod.getScreenHeight()/3));
    }
    private void configuration() {

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evaluate()){


                    LoginInfo loginInfo=new LoginInfo(tieUserName.getText().toString().trim(),tiePassword.getText().toString().trim());
                    sendLoginRequest(loginInfo);
                    MySharedPreferences.getInstance(LogInActivity.this).putLoginInfo(loginInfo);

                    startActivity(new Intent(LogInActivity.this,MainActivity.class));
                }
            }
        });



    }

    private void sendLoginRequest(LoginInfo loginInfo) {



    }


    private boolean evaluate() {

        if (tieUserName.getText().length() == 0) {
            tieUserName.setError("این فیلد را وارد کنید");
            return false;
        }

        if (tiePassword.getText().length() == 0) {
            tiePassword.setError("این فیلد را وارد کنید");
            return false;
        }

        return true;
    }
}