package in.hd_infra.www.HDInfra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Himamsh K on 30-09-2017.
 */

public class SharedPrefManager {
    MainActivity mainActivity;

    private static final String SHARED_PREF_NAME = "HD_INFRA'S : HIMAMSH.K";
    private static final String SHARED_PREF_NAME1 = "HD";

    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_ID = "keyid";
    public static final String IS_LOGIN = "IsLoggedIn";
    private static final String TAG_TOKEN = "tagtoken";

    public static SharedPrefManager mInstance;
    public static Context mCtx;

    SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(user user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_ID, user.getClgid());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());


        editor.apply();

    }

    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferencesc = mCtx.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesc.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }
    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferencesc = mCtx.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return  sharedPreferencesc.getString(TAG_TOKEN, null);
    }



    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID, null) != null;
    }

    public user getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new user(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null)
        );
    }


    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        mCtx.startActivity(new Intent(mCtx, login.class));

    }



}
