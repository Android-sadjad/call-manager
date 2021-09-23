package com.test.callmanager.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.test.callmanager.R;
import com.test.callmanager.classes.MainAdapter;
import com.test.callmanager.classes.MyConstant;
import com.test.callmanager.classes.MySharedPreferences;
import com.test.callmanager.models.SessionInfo;
import com.test.callmanager.models.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvUserName;
    TextView tvCodes;

    RecyclerView rvSession;
    MainAdapter mainAdapter;

    UserInfo loginInfo;
    String sId;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViews();
        init();



    }

    private void findViews() {

        tvUserName = findViewById(R.id.tv_username);
        tvCodes = findViewById(R.id.tv_codes);
        rvSession = findViewById(R.id.rv_session);
    }

    private void init() {
        loginInfo = MySharedPreferences.getInstance(MainActivity.this).getUserInfo();
        tvUserName.setText(getString(R.string.username) + ":" + loginInfo.getUserName());
        sId = loginInfo.getId();

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.getting_your_info));


        getUserCodeFromServer();
        getSessionList();
    }


    private void getUserCodeFromServer() {

        progressDialog.show();


        AndroidNetworking.post("https://prtn.ir/dataprobot/getscodes.php")
                .addBodyParameter(MyConstant.SID, sId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        StringBuilder codes = new StringBuilder("کد : ");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                codes.append(response.getJSONObject(i).getString(MyConstant.USERS_CODED));
                                codes.append("  درصد : ").append(response.getJSONObject(i).getString(MyConstant.PERCENT))
                                        .append("%").append("\n");
                            }
                            tvCodes.setText(codes);


                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.cancel();
                        Toast.makeText(MainActivity.this, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    }


                });

    }


    private void getSessionList() {

        ArrayList<SessionInfo> sessionInfoList = new ArrayList<>();

            AndroidNetworking.post("https://prtn.ir/dataprobot/getsessions.php")
                    .addBodyParameter(MyConstant.SID, sId)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {

                        @Override
                        public void onResponse(JSONArray response) {


                            try {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject jsonObjectSessionInfo = response.getJSONObject(i);
                                    SessionInfo sessionInfo = new SessionInfo();

                                    sessionInfo.setId(jsonObjectSessionInfo.getString(MyConstant.ID));
                                    sessionInfo.setTitle(jsonObjectSessionInfo.getString(MyConstant.TITLE));
                                    sessionInfo.setCity(jsonObjectSessionInfo.getString(MyConstant.CITY));
                                    sessionInfo.setArea(jsonObjectSessionInfo.getString(MyConstant.AREA));
                                    sessionInfo.setPhoneNumber(jsonObjectSessionInfo.getString(MyConstant.PHONE_NUMBER));

                                    sessionInfoList.add(sessionInfo);

                                }
                                progressDialog.cancel();
                                setUpList(sessionInfoList);


                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.cancel();
                            Toast.makeText(MainActivity.this, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        }


                    });






    }


    private void setUpList(ArrayList<SessionInfo> sessionInfoList) {

        mainAdapter = new MainAdapter(MainActivity.this, sessionInfoList);
        rvSession.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        rvSession.setAdapter(mainAdapter);

    }
}
