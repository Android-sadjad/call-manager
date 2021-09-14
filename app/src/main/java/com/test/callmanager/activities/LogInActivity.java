package com.test.callmanager.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.test.callmanager.R;
import com.test.callmanager.classes.MyConstant;
import com.test.callmanager.classes.MySharedPreferences;
import com.test.callmanager.classes.UseFullMethod;
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

                if (!UseFullMethod.isNetworkAvailable(getApplicationContext()))
                    return;

                if (evaluate()) {
                    sendLoginRequest();
                }
            }
        });


    }

    private void sendLoginRequest() {

        UserInfo userInfo = new UserInfo();

        String userName = tieUserName.getText().toString();
        String password = tiePassword.getText().toString();

        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();


        AndroidNetworking.post("https://prtn.ir/dataprobot/slogin.php")
                .addBodyParameter(MyConstant.USER_NAME, userName)
                .addBodyParameter(MyConstant.PASSWORD, password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            userInfo.setId(response.getString(MyConstant.ID));
                            userInfo.setUserName(response.getString(MyConstant.USER_NAME));
                            progressDialog.cancel();

                            if(userInfo.getId().equals("")){
                                Toast.makeText(LogInActivity.this, getString(R.string.wrong_username_or_password), Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(LogInActivity.this,getString(R.string.login_succsessfully),Toast.LENGTH_SHORT).show();
                                MySharedPreferences.getInstance(LogInActivity.this).putUserInfo(userInfo);
                                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                            }



                        } catch (JSONException e) {

                            Toast.makeText(LogInActivity.this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressDialog.cancel();
                        Toast.makeText(LogInActivity.this,getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    }
                });


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