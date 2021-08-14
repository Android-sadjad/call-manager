package com.test.callmanager.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.test.callmanager.fragments.LogInFragment;
import com.test.callmanager.R;

public class MainActivity extends AppCompatActivity {

    LogInFragment logInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadFragment(logInFragment);


    }

    private void init() {

        logInFragment =new LogInFragment();
    }


    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_container,fragment).commit();

    }

}