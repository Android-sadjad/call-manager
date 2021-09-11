package com.test.callmanager.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.test.callmanager.R;

import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    TextView tvSubmitResult;
    TextView tvSituationMenu;
    TextView tvPriorityMenu;

    ConstraintLayout clPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViews();
        Configuration();
        setUpMenu();

    }


    private void findViews(){

        tvSubmitResult=findViewById(R.id.tv_submit_meet);
        tvSituationMenu=findViewById(R.id.tv_situation_drop_down);
        tvPriorityMenu=findViewById(R.id.tv_priority_drop_down);

        clPrice=findViewById(R.id.cl_price);
    }

    private void Configuration() {
        tvSubmitResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sentResultToserver();

                Toast.makeText(ResultActivity.this, "نتیجه ثبت شد", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    private void sentResultToserver(){

        JSONObject jsonObjectResult=new JSONObject();




    }

    private void setUpMenu(){

        PopupMenu situationMenu=new PopupMenu(this,tvSituationMenu);
        situationMenu.getMenu().add("خاموش بودن تلفن");
        situationMenu.getMenu().add("برنداشتن تلفن");
        situationMenu.getMenu().add("عدم انجام تراکنش");
        situationMenu.getMenu().add("تراکنش موفق");


        situationMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().toString().equals("تراکنش موفق")){
                    clPrice.setVisibility(View.VISIBLE);

                }else{
                    clPrice.setVisibility(View.GONE);
                }

                tvSituationMenu.setText(item.getTitle());


                return false;
            }
        });

        tvSituationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                situationMenu.show();
            }
        });


        PopupMenu priorityMenu=new PopupMenu(this,tvPriorityMenu);
        priorityMenu.getMenu().add("1");
        priorityMenu.getMenu().add("2");
        priorityMenu.getMenu().add("3");
        priorityMenu.getMenu().add("4");
        priorityMenu.getMenu().add("5");
        priorityMenu.getMenu().add("6");
        priorityMenu.getMenu().add("7");
        priorityMenu.getMenu().add("8");
        priorityMenu.getMenu().add("9");
        priorityMenu.getMenu().add("10");


        priorityMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                tvPriorityMenu.setText(item.getTitle());

                return false;
            }
        });


        tvPriorityMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityMenu.show();
            }
        });








    }
}