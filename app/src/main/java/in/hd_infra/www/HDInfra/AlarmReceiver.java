package in.hd_infra.www.HDInfra;

/**
 * Created by Himamsh K on 24-11-2017.
 */

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by sonu on 09/04/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    AudioManager audioManager;
    String msg1,ms2;
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "ALARM!! ALARM!!", Toast.LENGTH_SHORT).show();

        //Stop sound service to play sound for alarm
        String value= String.valueOf(intent);
        if (value=="133") {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
             intent.putExtra("tt",2);
        }
        else{
            AudioManager audiooManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audiooManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            msg1="n";
            intent.putExtra("tt",1);
        }

        //This will send a notification message and show notification in notification tray
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmNotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

    }


}