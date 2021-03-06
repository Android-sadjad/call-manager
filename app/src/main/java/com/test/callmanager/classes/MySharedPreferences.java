package com.test.callmanager.classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.test.callmanager.models.UserInfo;

public class MySharedPreferences {

    private static Gson gson;
    private static MySharedPreferences mySharedPreferences = null;
    Context context;
    SharedPreferences sharedPreferences;

    //////////////////////////////////////////////////////////////////////////////////
    SharedPreferences.Editor editor;

    private MySharedPreferences(Context context) {

        sharedPreferences = context.getSharedPreferences(MyConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        this.context = context;

    }

    public static MySharedPreferences getInstance(Context context) {


        if (mySharedPreferences == null)
            mySharedPreferences = new MySharedPreferences(context);
        gson = new Gson();

        return mySharedPreferences;
    }
    //////////////////////////////////////////////////////////////////////////////////

    public void putUserInfo(UserInfo loginInfo) {

        String logInString = gson.toJson(loginInfo, UserInfo.class);

        editor.putString(MyConstant.LOGIN_KEY, logInString).apply();

    }

    public UserInfo getUserInfo() {

        String logInString = sharedPreferences.getString(MyConstant.LOGIN_KEY, null);

        if (logInString == null)
            return null;
        UserInfo loginInfo = gson.fromJson(logInString, UserInfo.class);

        return loginInfo;
    }


}
