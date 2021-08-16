package com.test.callmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.test.callmanager.R;
import com.test.callmanager.classes.MainAdapter;
import com.test.callmanager.classes.MySharedPreferences;
import com.test.callmanager.classes.SessionAdapter;
import com.test.callmanager.models.LoginInfo;
import com.test.callmanager.models.SessionInfo;

import java.util.ArrayList;

public class SessionActiviy extends AppCompatActivity {


    RecyclerView rvSession;
    SessionAdapter sessionAdapter;

    LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_activiy);


        findViews();
        init();
        setUpList();
    }

    private void findViews() {

        rvSession=findViewById(R.id.rv_session);
    }

    private void init(){







    }

    private void setUpList() {

        ArrayList<SessionInfo> sessionList = new ArrayList<>();


        sessionAdapter=new SessionAdapter(SessionActiviy.this,sessionList);
        rvSession.setLayoutManager(new LinearLayoutManager(SessionActiviy.this));

        rvSession.setAdapter(sessionAdapter);

    }
}