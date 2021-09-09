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

import com.test.callmanager.R;
import com.test.callmanager.classes.SessionAdapter;
import com.test.callmanager.models.SessionInfo;

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

    private void setUpList() {

        ArrayList<SessionInfo> sessionList = new ArrayList<>();

        sessionAdapter = new SessionAdapter(SessionActiviy.this, sessionList);
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