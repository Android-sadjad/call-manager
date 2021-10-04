package com.test.callmanager.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.test.callmanager.R;
import com.test.callmanager.classes.MyConstant;
import com.test.callmanager.classes.MySharedPreferences;
import com.test.callmanager.models.SessionInfo;

public class ResultActivity extends AppCompatActivity {

    TextView tvSubmitResult;
    TextView tvStatusMenu;
    TextView tvPriorityMenu;

    ConstraintLayout clPrice;

    TextInputEditText tieAgentName;
    TextInputEditText tieDurationMeet;
    TextInputEditText tieDescription;
    TextInputEditText tiePrice;

    EditText etYear;
    EditText etMonth;
    EditText etDay;

    SessionInfo sessionInfo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViews();
        init();
        Configuration();
        setUpMenu();

    }


    private void findViews() {

        tvSubmitResult = findViewById(R.id.tv_submit_result);
        tvStatusMenu = findViewById(R.id.tv_status_menu);
        tvPriorityMenu = findViewById(R.id.tv_priority_menu);

        tieAgentName = findViewById(R.id.tie_agent_name);
        tieDescription = findViewById(R.id.tie_description);
        tieDurationMeet = findViewById(R.id.tie_duration_meet);
        tiePrice = findViewById(R.id.tie_price);

        etDay = findViewById(R.id.et_day);
        etMonth = findViewById(R.id.et_month);
        etYear = findViewById(R.id.et_year);


        clPrice = findViewById(R.id.cl_price);
    }

    private void init() {

        sessionInfo = (SessionInfo) getIntent().getSerializableExtra(MyConstant.SESSION_INFO);
    }

    private void Configuration() {

        tvSubmitResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(evaluate()){
                    sentResultToServer();
                }

            }
        });


    }

    private void sentResultToServer() {


        progressDialog = new ProgressDialog(ResultActivity.this);
        progressDialog.setMessage(getString(R.string.getting_your_info));
        progressDialog.show();


        String sid=  MySharedPreferences.getInstance(ResultActivity.this).getUserInfo().getId();

        String id= sessionInfo.getId();
        String agentName = tieAgentName.getText().toString().trim();
        String status = tvStatusMenu.getText().toString().trim();
        String priority = tvPriorityMenu.getText().toString().trim();

        StringBuilder date=new StringBuilder();
        date.append(etYear.getText().toString().trim()).append("/")
                .append(etMonth.getText().toString().trim()).append("/")
                .append( etDay.getText().toString().trim());

        String price = tiePrice.getText().toString().trim();

        String meetDuration = tieDurationMeet.getText().toString().trim();
        String description = tieDescription.getText().toString().trim();


        switch (status){

            case  MyConstant.PHONE_OFF:
                    status=MyConstant.OP;
                break;

            case MyConstant.NO_RESPONSE:
                status=MyConstant.NR;
                break;

            case MyConstant.FAILURE_TRANSACTION:
                status=MyConstant.FT;
                break;

            case MyConstant.SUCCESSFULLY_TRANSACTION:
                status=MyConstant.SF;
                break;


        }


        AndroidNetworking.post(MyConstant.URL_SEND_RESULT)
                .addBodyParameter(MyConstant.SUPPORTER_ID, sid)
                .addBodyParameter(MyConstant.RL_ID, id)
                .addBodyParameter(MyConstant.REALESTATE_NAME, agentName)
                .addBodyParameter(MyConstant.STATUS, status)
                .addBodyParameter(MyConstant.PRIORITY, priority)
                .addBodyParameter(MyConstant.DATE, String.valueOf(date))
                .addBodyParameter(MyConstant.MEETING_DURATION, meetDuration)
                .addBodyParameter(MyConstant.MORE, description)
                .addBodyParameter(MyConstant.AMOUNT, price)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.cancel();
                        if(response.equals(getString(R.string.done))){
                            Toast.makeText(ResultActivity.this, getString(R.string.result_recorded), Toast.LENGTH_SHORT).show();
                            setResult(MyConstant.RESULT_CODE);
                            finish();
                        }
                        else
                            Toast.makeText(ResultActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(ResultActivity.this, getString(R.string.error)+anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }

                    });









    }

    private void setUpMenu() {

        PopupMenu statusMenu = new PopupMenu(this, tvStatusMenu);
        statusMenu.getMenu().add(getString(R.string.off_phone));
        statusMenu.getMenu().add(getString(R.string.no_response));
        statusMenu.getMenu().add(getString(R.string.failure_transaction));
        statusMenu.getMenu().add(getString(R.string.succsessfully_transaction));


        statusMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().toString().equals(getString(R.string.succsessfully_transaction))) {
                    clPrice.setVisibility(View.VISIBLE);

                } else {
                    clPrice.setVisibility(View.GONE);
                }

                tvStatusMenu.setText(item.getTitle());


                return false;
            }
        });

        tvStatusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusMenu.show();
            }
        });


        PopupMenu priorityMenu = new PopupMenu(this, tvPriorityMenu);
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

    private boolean evaluate(){

        if(tieAgentName.getText().length()==0){

            tieAgentName.setError(getString(R.string.enter_this_field));
            return false;
        }else if(tvStatusMenu.getText().equals(getString(R.string.meeting_status))){

            Toast.makeText(this, getString(R.string.please_select_situation), Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(etDay.getText().length()!=2||etMonth.getText().length()!=2||etYear.getText().length()!=4) {
            Toast.makeText(this, getString(R.string.please_enter_date), Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(tvStatusMenu.getText().equals(getString(R.string.succsessfully_transaction))&&
                Long.parseLong(tiePrice.getText().toString())<5000){
            tiePrice.setError(getString(R.string.enter_correct_price));
            return false;
        }

        else if(tieDurationMeet.getText().length()==0){
            tieDurationMeet.setError(getString(R.string.enter_this_field));
            return false;
        }

        else if(tieDescription.getText().length()==0){
            tieDescription.setError(getString(R.string.enter_this_field));
            return false;
        }


        return true;

    }
}