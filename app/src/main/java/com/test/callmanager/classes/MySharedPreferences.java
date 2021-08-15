package com.test.callmanager.classes;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.test.callmanager.models.LoginInfo;

public class MySharedPreferences {

    Context context;

    private static Gson gson;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    //////////////////////////////////////////////////////////////////////////////////

    private static MySharedPreferences mySharedPreferences = null;

    public static MySharedPreferences getInstance(Context context) {


        if (mySharedPreferences == null)
            mySharedPreferences = new MySharedPreferences(context);
        gson = new Gson();

        return mySharedPreferences;
    }

    private MySharedPreferences(Context context) {

        sharedPreferences = context.getSharedPreferences(MyConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        this.context = context;

    }
    //////////////////////////////////////////////////////////////////////////////////


    public void putLoginInfo(LoginInfo loginInfo){


        String logInString=gson.toJson(loginInfo,LoginInfo.class);

        editor.putString(MyConstant.LOGIN_KEY,logInString).apply();

    }

    public LoginInfo getLoginInfo(){

        String logInString=sharedPreferences.getString(MyConstant.LOGIN_KEY,null);

        if(logInString==null)
            return null;
        LoginInfo loginInfo=gson.fromJson(logInString,LoginInfo.class);

        return loginInfo;
    }




}
