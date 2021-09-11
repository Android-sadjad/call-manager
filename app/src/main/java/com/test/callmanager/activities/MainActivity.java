package com.test.callmanager.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
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

        getUserCodeFromServer();
        getSessionList();
    }

    private void getUserCodeFromServer() {

        try {

            JSONObject jsonObjectId = new JSONObject();
            jsonObjectId.put(MyConstant.ID, loginInfo.getId());


            //send to server

            JSONArray jsonArrayUserCodes = new JSONArray();

            JSONObject jsonObject = new JSONObject();


            jsonObject.put(MyConstant.USERS_CODED, "1234");


            jsonArrayUserCodes.put(jsonObject);
            jsonArrayUserCodes.put(jsonObject);
            jsonArrayUserCodes.put(jsonObject);
            ////

            StringBuilder codes = new StringBuilder("کدهای کاربرد:");

            for (int i = 0; i < jsonArrayUserCodes.length(); i++) {
                JSONObject jsonObjectCode = jsonArrayUserCodes.getJSONObject(i);
                codes.append("\n").append(jsonObjectCode.getString(MyConstant.USERS_CODED));
            }

            tvCodes.setText(codes);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void getSessionList(){
        try {

            JSONObject jsonObjectId = new JSONObject();
            jsonObjectId.put(MyConstant.ID, loginInfo.getId());

            //send to server

            JSONArray jsonArraySessionList = new JSONArray();

            JSONObject jsonObject = new JSONObject();


            jsonObject.put(MyConstant.TITLE, "دانشگاه سجاد");
            jsonObject.put(MyConstant.PHONE_NUMBER, "0517678798");
            jsonObject.put(MyConstant.CITY, "مشهد");
            jsonObject.put(MyConstant.AREA, "منطقه ۲");

            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            jsonArraySessionList.put(jsonObject);
            ////


            ArrayList<SessionInfo> sessionInfoList=new ArrayList<>();

            for (int i=0;i<jsonArraySessionList.length();i++){

                JSONObject jsonObjectSessionInfo=jsonArraySessionList.getJSONObject(i);
                SessionInfo sessionInfo=new SessionInfo();

                sessionInfo.setTitle(jsonObjectSessionInfo.getString(MyConstant.TITLE));
                sessionInfo.setPhoneNumber(jsonObjectSessionInfo.getString(MyConstant.PHONE_NUMBER));
                sessionInfo.setCity(jsonObjectSessionInfo.getString(MyConstant.CITY));
                sessionInfo.setArea(jsonObjectSessionInfo.getString(MyConstant.AREA));

                sessionInfoList.add(sessionInfo);
                setUpList(sessionInfoList);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    private void setUpList(ArrayList<SessionInfo> sessionInfoList) {


        mainAdapter = new MainAdapter(MainActivity.this, sessionInfoList);
        rvSession.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        rvSession.setAdapter(mainAdapter);

    }

}