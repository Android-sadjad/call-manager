package com.test.callmanager.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.test.callmanager.R;
import com.test.callmanager.classes.MyConstant;
import com.test.callmanager.classes.SessionAdapter;
import com.test.callmanager.models.SessionHistory;
import com.test.callmanager.models.SessionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SessionActiviy extends AppCompatActivity {

    TextView tvTitle;
    TextView tvCity;
    TextView tvArea;
    TextView tvPhone;

    TextView tvCall;
    TextView tvResult;

    RecyclerView rvSession;
    SessionAdapter sessionAdapter;

    boolean callSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_activiy);

        findViews();
        init();
        setUpList();
        configuration();
    }

    private void findViews() {

        tvTitle = findViewById(R.id.tv_session_title);
        tvCity = findViewById(R.id.tv_city);
        tvArea = findViewById(R.id.tv_area);
        tvPhone = findViewById(R.id.tv_phone);

        tvCall = findViewById(R.id.tv_call);
        tvResult = findViewById(R.id.tv_result);

        rvSession = findViewById(R.id.rv_session);

    }

    private void init(){


        SessionInfo sessionInfo= (SessionInfo) getIntent().getSerializableExtra(MyConstant.SESSION_INFO);

        tvTitle.setText(sessionInfo.getTitle());
        tvPhone.setText(sessionInfo.getPhoneNumber());
        tvCity.setText(sessionInfo.getCity());
        tvArea.setText(sessionInfo.getArea());


    }

    private void setUpList() {

        ArrayList<SessionHistory> sessionHistories = new ArrayList<>();
        //
        JSONArray jsonArrayHistoryList=new JSONArray();

        JSONObject jsonObjectHistory=new JSONObject();
        try {
            jsonObjectHistory.put(MyConstant.SUPPORT_NAME,"پشتیبان:مهدی");
            jsonObjectHistory.put(MyConstant.AGENT_NAME,"نماینده:محمد");
            jsonObjectHistory.put(MyConstant.SITUATION,"وضعیت:انجام شده");
            jsonObjectHistory.put(MyConstant.PRICE,"مبلغ:120000");
            jsonObjectHistory.put(MyConstant.PRIORITY,"اولیت : 4");
            jsonObjectHistory.put(MyConstant.DATE,"تاریخ :1400/06/20");
            jsonObjectHistory.put(MyConstant.DURATION,"مدت جلسه:20 دقیقه");
            jsonObjectHistory.put(MyConstant.DESCRIPTION,"توضیحات :\nجلسه بین دانشجویان سجاد و فردوسی");


        jsonArrayHistoryList.put(jsonObjectHistory);
        jsonArrayHistoryList.put(jsonObjectHistory);
        jsonArrayHistoryList.put(jsonObjectHistory);
        jsonArrayHistoryList.put(jsonObjectHistory);
        jsonArrayHistoryList.put(jsonObjectHistory);
        jsonArrayHistoryList.put(jsonObjectHistory);
        jsonArrayHistoryList.put(jsonObjectHistory);
        jsonArrayHistoryList.put(jsonObjectHistory);
        jsonArrayHistoryList.put(jsonObjectHistory);


        for (int i=0;i<jsonArrayHistoryList.length();i++){

            JSONObject jsonObject =jsonArrayHistoryList.getJSONObject(i);
            SessionHistory sessionHistory=new SessionHistory();

            sessionHistory.setSupportName(jsonObject.getString(MyConstant.SUPPORT_NAME));
            sessionHistory.setAgentName(jsonObject.getString(MyConstant.AGENT_NAME));
            sessionHistory.setSituation(jsonObject.getString(MyConstant.SITUATION));
            sessionHistory.setPrice(jsonObject.getString(MyConstant.PRICE));
            sessionHistory.setPriority(jsonObject.getString(MyConstant.PRIORITY));
            sessionHistory.setDate(jsonObject.getString(MyConstant.DATE));
            sessionHistory.setDuration(jsonObject.getString(MyConstant.DURATION));
            sessionHistory.setDescription(jsonObject.getString(MyConstant.DESCRIPTION));

            sessionHistories.add(sessionHistory);
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//
        sessionAdapter = new SessionAdapter(SessionActiviy.this, sessionHistories);
        rvSession.setLayoutManager(new LinearLayoutManager(SessionActiviy.this));

        rvSession.setAdapter(sessionAdapter);

    }

    private void configuration() {

        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callSelected = true;
                Uri uri = Uri.parse("tel:" + tvPhone.getText().toString().trim());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);


            }
        });

        tvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSelected = false;
                startActivity(new Intent(SessionActiviy.this, ResultActivity.class));

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (callSelected) {
            Toast.makeText(this, "لطفا ابتدا نتیجه جلسه را وارد کنید.", Toast.LENGTH_SHORT).show();

        } else
            finish();
    }
}