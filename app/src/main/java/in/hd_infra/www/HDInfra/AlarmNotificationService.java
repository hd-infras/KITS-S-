package in.hd_infra.www.HDInfra;

/**
 * Created by Himamsh K on 24-11-2017.
 */

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import java.lang.String;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by sonu on 10/04/17.
 */

public class AlarmNotificationService extends IntentService {
    private NotificationManager alarmNotificationManager;
AlarmReceiver alarmReceiver;
    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 1;
    private Context mCtx;


    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
       String tt= String.valueOf(intent.getStringExtra("tt"));
        //Send notification

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            sendNotification(" Automatic Silent has been Activated/Deactivated");

        }
    }

    //handle notification
    private void sendNotification(String msg) {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        //get pending intent
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //Create notification
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("HD-Infra's").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg).setAutoCancel(true);
        alamNotificationBuilder.setContentIntent(contentIntent);

        //notiy notification manager about new notification
        alarmNotificationManager.notify(NOTIFICATION_ID, alamNotificationBuilder.build());
    }


}