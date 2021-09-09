package com.test.callmanager.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.callmanager.R;
import com.test.callmanager.classes.MainAdapter;
import com.test.callmanager.classes.MySharedPreferences;
import com.test.callmanager.models.LoginInfo;
import com.test.callmanager.models.SessionInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvUserName;
    TextView tvCodes;

    RecyclerView rvSession;
    MainAdapter mainAdapter;

    LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViews();
        init();
        setUpList();
    }

    private void findViews() {

        tvUserName = findViewById(R.id.tv_username);
        tvCodes = findViewById(R.id.tv_codes);
        rvSession = findViewById(R.id.rv_session);
    }

    private void init() {

        loginInfo = MySharedPreferences.getInstance(MainActivity.this).getLoginInfo();

        tvUserName.setText(getString(R.string.username) + ":" + loginInfo.getUserName());


    }

    private void setUpList() {

        ArrayList<SessionInfo> sessionList = new ArrayList<>();
        {
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));
            sessionList.add(new SessionInfo("عنوان جلسه", "09151438729"));


        }

        mainAdapter = new MainAdapter(MainActivity.this, sessionList);
        rvSession.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        rvSession.setAdapter(mainAdapter);

    }

}