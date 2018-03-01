package in.hd_infra.www.HDInfra;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private View navHeader;
    files2 fil;
    private NavigationView navigationView;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private static final String urlNavHeaderBg = "https://www.hdinfra.in/123/images/bg.jpg";
    private static final String urlProfileImg = "https://www.hdinfra.in/123/images/Contact-icon.png";
    ScheduledExecutorService scheduledExecutorService;
    AlarmManager am;
    PendingIntent pendingIntent ,pendingItent,pedingItent;
    AudioManager mAudioManager;
    int d;
    String s, p;
    int h;

    private static final int ALARM_REQUEST_CODE = 133;
    private static final int Alarm =134;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AllowRunTimePermission();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAudioManager = (AudioManager) MainActivity.this.getSystemService(Context.AUDIO_SERVICE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent i = new Intent(getApplicationContext(), profile.class);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

            }
        });


      /*  final int abTitleId = getResources().getIdentifier("action_settings","id","android");
        findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Do something

                Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
                Intent iu = new Intent(getApplicationContext(), profile.class);
                startActivity(iu);
            }
        });
*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //  navigationView = (NavigationView) findViewById(R.id.nav_view);


        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        //loadNavHeader();
        men();
        load();



            Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, ALARM_REQUEST_CODE, alarmIntent, 0);
            // Set firing of the receiver in predefined time each day
            Calendar firingAt = Calendar.getInstance();
            firingAt.set(Calendar.HOUR_OF_DAY, 21);
            // At the hour you wanna fire
            firingAt.set(Calendar.MINUTE, 49);
            // Particular minute
            firingAt.set(Calendar.SECOND, 0);
            // particular second
            long millis = firingAt.getTimeInMillis();
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, millis, AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(MainActivity.this, "TIMER SET", Toast.LENGTH_LONG).show();
            //next alarm******************************************************************************************************************************************
            pendingItent = PendingIntent.getBroadcast(MainActivity.this, Alarm, alarmIntent, 0);
            Calendar firinAt = Calendar.getInstance();
            firinAt.set(Calendar.HOUR_OF_DAY, 23);
            // At the hour you wanna fire
            firinAt.set(Calendar.MINUTE, 13);
            // Particular minute
            firinAt.set(Calendar.SECOND, 0);
            // particular second
            long milis = firingAt.getTimeInMillis();
            AlarmManager maanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            maanager.setRepeating(AlarmManager.RTC_WAKEUP, milis, AlarmManager.INTERVAL_DAY, pendingItent);
            Toast.makeText(MainActivity.this, "TIMER SET/normal", Toast.LENGTH_LONG).show();
        //next alarm**9:00am****************************************************************************************************************************************
        pedingItent = PendingIntent.getBroadcast(MainActivity.this, ALARM_REQUEST_CODE, alarmIntent, 0);
        Calendar firiAt = Calendar.getInstance();
        firiAt.set(Calendar.HOUR, 9);
        // At the hour you wanna fire
        firiAt.set(Calendar.MINUTE, 20);
        // Particular minute
        firiAt.set(Calendar.SECOND, 0);
        // particular second
        long mlis = firingAt.getTimeInMillis();
        AlarmManager maaanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        maaanager.setRepeating(AlarmManager.RTC_WAKEUP, mlis, AlarmManager.INTERVAL_DAY, pedingItent);
        Toast.makeText(MainActivity.this, "TIMER SET", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            Intent profile=new Intent(getApplicationContext(),profile.class);
            startActivity(profile,ActivityOptions.makeCustomAnimation(MainActivity.this, Animation.ABSOLUTE,R.layout.fade_out).toBundle());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelected(item.getItemId());
        return true;
    }

    public void displaySelected(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_camera:
                fragment = new camera();
                break;
            case R.id.nav_gallery:
                //fragment = new gallery();
                Intent v=new Intent(getApplicationContext(),SilentScheduler.class);
                startActivity(v);
                break;
            case R.id.nav_send:
                 fragment=new manager();




                break;
            case R.id.action_settings:
                Intent profile=new Intent(getApplicationContext(),profile.class);
                startActivity(profile,ActivityOptions.makeCustomAnimation(MainActivity.this, Animation.ABSOLUTE,R.layout.fade_out).toBundle());

                break;
            case R.id.nav_cloud:
                Intent c = new Intent(getApplicationContext(), cloud.class);
                startActivity(c,ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                break;
            case R.id.nav_sen:
                Intent sen = new Intent(getApplicationContext(), files.class);
                startActivity(sen,ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack(null);
            ft.commit();
            ft.addToBackStack(null);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }





    public void men() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {


            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        loadNavHeader();

                                            }
                });

            }
        }, 1, 1, TimeUnit.SECONDS);

    }

    public void loadNavHeader() {
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {


            //    String mail = String.valueOf(user.getEmail());
            txtName.setText("Login ");
            txtWebsite.setText("*******");


        } else {

            user user = SharedPrefManager.getInstance(this).getUser();
            // name, website
            txtName.setText(String.valueOf(user.getClgid()));
            txtWebsite.setText(String.valueOf(user.getEmail()));

        }


    }

    public void load() {


        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                //.bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

    }


   /* public void AllowRunTimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Toast.makeText(MainActivity.this, "READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(MainActivity.this, "Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }*/

public void tt(){

   /* SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    if(dayOfTheWeek=="saturday"){
        String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
        String timeToCompare = "09:25";
        boolean x = currentTime.equals(timeToCompare);
        if(x){
            AudioManager audioManager=(AudioManager)MainActivity.this.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
    */


}



}