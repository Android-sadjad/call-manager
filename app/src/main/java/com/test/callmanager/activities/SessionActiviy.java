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
        getHistoryList();
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

    private void init() {
        progressDialog = new ProgressDialog(SessionActiviy.this);
        progressDialog.setMessage(getString(R.string.getting_your_info));
        progressDialog.show();


        sessionInfo = (SessionInfo) getIntent().getSerializableExtra(MyConstant.SESSION_INFO);

        tvTitle.setText(sessionInfo.getTitle());
        tvPhone.setText(sessionInfo.getPhoneNumber());
        tvCity.setText(sessionInfo.getCity());
        tvArea.setText(sessionInfo.getArea());


    }

    private void getHistoryList() {


        ArrayList<SessionHistory> sessionHistoriesList = new ArrayList<>();
        String rlId = sessionInfo.getId().toString().trim();


        AndroidNetworking.post(MyConstant.URL_HISTORY_LIST)
                .addBodyParameter(MyConstant.RL_ID, rlId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObjectSessionHistory = response.getJSONObject(i);
                                SessionHistory sessionHistory = new SessionHistory();
                                sessionHistory.setAgentName(jsonObjectSessionHistory.getString(MyConstant.RL_NAME));
                                sessionHistory.setStatus(jsonObjectSessionHistory.getString(MyConstant.M_status));
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

                Intent intent = new Intent(SessionActiviy.this, ResultActivity.class);
                intent.putExtra(MyConstant.SESSION_INFO, sessionInfo);
                startActivityForResult(intent, MyConstant.REQUEST_CODE);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == MyConstant.REQUEST_CODE && resultCode == MyConstant.RESULT_CODE) {

            getHistoryList();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (callSelected) {
            Toast.makeText(this, getString(R.string.please_enter_result), Toast.LENGTH_SHORT).show();

        } else
            finish();
    }
}