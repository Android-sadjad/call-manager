package com.test.callmanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.callmanager.R;

public class ResultActivity extends AppCompatActivity {

    TextView tvSubmitResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViews();
        Configuration();

    }


    private void findViews(){

        tvSubmitResult=findViewById(R.id.tv_submit_meet);
    }

    private void Configuration() {
        tvSubmitResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ResultActivity.this, "نتیجه ثبت شد", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}