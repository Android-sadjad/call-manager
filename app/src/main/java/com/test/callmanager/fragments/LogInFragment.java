package com.test.callmanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.test.callmanager.R;
import com.test.callmanager.classes.MyUtilsMethod;

public class LogInFragment extends Fragment {


    Button btnLogin;
    TextInputEditText tieUserName, tiePassword;
    View viewHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        findViews(view);
        setViewSize();
        configuration();


    }




    private void findViews(View view) {

        viewHeader =view.findViewById(R.id.view_header);

        btnLogin =view.findViewById(R.id.btn_login);

        tiePassword =view.findViewById(R.id.tie_password);
        tieUserName =view.findViewById(R.id.tie_user_name);

    }

    private void setViewSize(){

        viewHeader.setLayoutParams(new ConstraintLayout
                .LayoutParams(MyUtilsMethod.getScreenWidth(),MyUtilsMethod.getScreenHeight()/3));
    }
    private void configuration() {



    }

}
