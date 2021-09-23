package com.test.callmanager.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
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

    ProgressDialog progressDialog;

    SessionInfo sessionInfo;

    boolean callSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_activiy);

        findViews();
        init();
        getList();
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
        progressDialog=new ProgressDialog(SessionActiviy.this);
        progressDialog.setMessage(getString(R.string.getting_your_info));
        progressDialog.show();


        sessionInfo= (SessionInfo) getIntent().getSerializableExtra(MyConstant.SESSION_INFO);

        tvTitle.setText(sessionInfo.getTitle());
        tvPhone.setText(sessionInfo.getPhoneNumber());
        tvCity.setText(sessionInfo.getCity());
        tvArea.setText(sessionInfo.getArea());


    }

    private void getList() {


        ArrayList<SessionHistory> sessionHistoriesList = new ArrayList<>();
        String rlId =sessionInfo.getId().toString().trim();


        AndroidNetworking.post("https://prtn.ir/dataprobot/gethistory.php")
                .addBodyParameter(MyConstant.RL_ID,rlId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                        for (int i = 0; i <response.length() ; i++) {

                                JSONObject jsonObjectSessionHistory=response.getJSONObject(i);
                                SessionHistory sessionHistory=new SessionHistory();
                                sessionHistory.setAgentName(jsonObjectSessionHistory.getString(MyConstant.RL_NAME));
                                sessionHistory.setSituation(jsonObjectSessionHistory.getString(MyConstant.M_status));
                                sessionHistory.setPriority(jsonObjectSessionHistory.getString(MyConstant.M_PRIORITY));
                                sessionHistory.setDate(jsonObjectSessionHistory.getString(MyConstant.M_DATE));
                                sessionHistory.setDuration(jsonObjectSessionHistory.getString(MyConstant.M_DURATION));
                                sessionHistory.setDescription(jsonObjectSessionHistory.getString(MyConstant.M_MORE));
                                sessionHistory.setPrice(jsonObjectSessionHistory.getString(MyConstant.M_AMOUNT));
                                sessionHistory.setSupportName(jsonObjectSessionHistory.getString(MyConstant.S_USERNAME));

                                sessionHistoriesList.add(sessionHistory);

                        }

                            progressDialog.cancel();
                            setUpList(sessionHistoriesList);

                    } catch (JSONException e) {
                            Toast.makeText(SessionActiviy.this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.cancel();
                        Toast.makeText(SessionActiviy.this, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    }
                });







        //
//        JSONArray jsonArrayHistoryList=new JSONArray();
//
//        JSONObject jsonObjectHistory=new JSONObject();
//        try {
//            jsonObjectHistory.put(MyConstant.SUPPORT_NAME,"پشتیبان:مهدی");
//            jsonObjectHistory.put(MyConstant.AGENT_NAME,"نماینده:محمد");
//            jsonObjectHistory.put(MyConstant.SITUATION,"وضعیت:انجام شده");
//            jsonObjectHistory.put(MyConstant.PRICE,"مبلغ:120000");
//            jsonObjectHistory.put(MyConstant.PRIORITY,"اولیت : 4");
//            jsonObjectHistory.put(MyConstant.DATE,"تاریخ :1400/06/20");
//            jsonObjectHistory.put(MyConstant.MEETING_DURATION,"مدت جلسه:20 دقیقه");
//            jsonObjectHistory.put(MyConstant.DESCRIPTION,"توضیحات :\nجلسه بین دانشجویان سجاد و فردوسی");
//
//
//        jsonArrayHistoryList.put(jsonObjectHistory);
//        jsonArrayHistoryList.put(jsonObjectHistory);
//        jsonArrayHistoryList.put(jsonObjectHistory);
//        jsonArrayHistoryList.put(jsonObjectHistory);
//        jsonArrayHistoryList.put(jsonObjectHistory);
//        jsonArrayHistoryList.put(jsonObjectHistory);
//        jsonArrayHistoryList.put(jsonObjectHistory);
//        jsonArrayHistoryList.put(jsonObjectHistory);
//        jsonArrayHistoryList.put(jsonObjectHistory);
//
//
//        for (int i=0;i<jsonArrayHistoryList.length();i++){
//
//            JSONObject jsonObject =jsonArrayHistoryList.getJSONObject(i);
//            SessionHistory sessionHistory=new SessionHistory();
//
//            sessionHistory.setSupportName(jsonObject.getString(MyConstant.SUPPORT_NAME));
//            sessionHistory.setAgentName(jsonObject.getString(MyConstant.AGENT_NAME));
//            sessionHistory.setSituation(jsonObject.getString(MyConstant.SITUATION));
//            sessionHistory.setPrice(jsonObject.getString(MyConstant.PRICE));
//            sessionHistory.setPriority(jsonObject.getString(MyConstant.PRIORITY));
//            sessionHistory.setDate(jsonObject.getString(MyConstant.DATE));
//            sessionHistory.setDuration(jsonObject.getString(MyConstant.MEETING_DURATION));
//            sessionHistory.setDescription(jsonObject.getString(MyConstant.DESCRIPTION));
//
//            sessionHistories.add(sessionHistory);
//        }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//


    }


    private void setUpList(ArrayList<SessionHistory> sessionHistoriesList) {
        sessionAdapter = new SessionAdapter(SessionActiviy.this, sessionHistoriesList);
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

                Intent intent=new Intent(SessionActiviy.this, ResultActivity.class);
                intent.putExtra(MyConstant.SESSION_INFO,sessionInfo);
                startActivityForResult(intent,MyConstant.REQUEST_CODE);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==MyConstant.REQUEST_CODE&&resultCode==MyConstant.RESULT_CODE){
            getList();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (callSelected) {
            Toast.makeText(this, "لطفا ابتدا نتیجه جلسه را وارد کنید.", Toast.LENGTH_SHORT).show();

        } else
            finish();
    }
}