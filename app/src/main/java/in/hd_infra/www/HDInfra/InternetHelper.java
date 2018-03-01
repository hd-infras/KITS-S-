package in.hd_infra.www.HDInfra;

/**
 * Created by Himamsh K on 29-01-2018.
 */import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class InternetHelper {


public static boolean checkconnection(Context context){

    ConnectivityManager connectivityManager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager
            .getActiveNetworkInfo();

    // Check internet connection and accrding to state change the
    // text of activity by calling method
    if (networkInfo != null && networkInfo.isConnected()) {

        return true;
    } else {
        return false;
    }
}
}


