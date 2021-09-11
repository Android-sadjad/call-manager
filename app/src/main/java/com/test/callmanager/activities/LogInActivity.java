package com.test.callmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.test.callmanager.R;
import com.test.callmanager.classes.MyConstant;
import com.test.callmanager.classes.MySharedPreferences;
import com.test.callmanager.models.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;


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


                    if(sendLoginRequest()){

                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    }


                }
            }
        });


    }

    private boolean sendLoginRequest() {

        String userName=tieUserName.getText().toString();
        String password=tiePassword.getText().toString();

        JSONObject jsonObjectLogin=new JSONObject();

        try {
            jsonObjectLogin.put(MyConstant.USER_NAME,userName);
            jsonObjectLogin.put(MyConstant.PASSWORD,password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send json to server

        JSONObject jsonObjectUserInfo=new JSONObject();

        try {
            jsonObjectUserInfo.put(MyConstant.ID,"123");
            jsonObjectUserInfo.put(MyConstant.USER_NAME,"mohammad");
            jsonObjectUserInfo.put(MyConstant.PASSWORD,"654321");
            jsonObjectUserInfo.put(MyConstant.PERCENT,"45");
            jsonObjectUserInfo.put(MyConstant.NATIONAL_CODE,"0934359809");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UserInfo userInfo=new UserInfo();
        try {
            userInfo.setId(jsonObjectUserInfo.getString(MyConstant.ID));
            userInfo.setUserName(jsonObjectUserInfo.getString(MyConstant.USER_NAME));
            userInfo.setPassword(jsonObjectUserInfo.getString(MyConstant.PASSWORD));
            userInfo.setPercent(jsonObjectUserInfo.getString(MyConstant.PERCENT));
            userInfo.setNationalCode(jsonObjectUserInfo.getString(MyConstant.NATIONAL_CODE));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MySharedPreferences.getInstance(LogInActivity.this).putUserInfo(userInfo);

        return true;

    }


    private boolean evaluate() {

        if (tieUserName.getText().toString().trim().length() == 0) {
            tieUserName.requestFocus();
            tieUserName.setError(getString(R.string.enter_this_field));
            return false;
        }

        if (tiePassword.getText().toString().trim().length() == 0) {
            tiePassword.requestFocus();
            tiePassword.setError(getString(R.string.enter_this_field));
            return false;
        }

        return true;
    }
}