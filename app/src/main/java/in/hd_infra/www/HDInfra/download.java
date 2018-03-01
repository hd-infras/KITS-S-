package in.hd_infra.www.HDInfra;

/**
 * Created by Himamsh K on 14-11-2017.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.PopupWindow;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class download  {
    ProgressBar progressBar;

    private ProgressDialog pDialog;
    private static final String TAG = "Download Task";
    private Context context;
    private String downloadUrl = "", downloadFileName = "";
    Intent intent;



    public download(Context context,  String downloadUrl) {

        this.context = context;

        this.downloadUrl = downloadUrl;

        downloadFileName = downloadUrl.replace("http://123.hdinfra.in/download.php?f=", "");//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);
        new DownloadingTask().execute();





}
    private class DownloadingTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdialog=new ProgressDialog(context);
        PopupWindow popupWindow=new PopupWindow(context);
        File apkStorage = null;
        File outputFile = null;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
Log.e(TAG,"Download started : "+downloadUrl);
                //displaying the progress bar while user registers on the server

            pdialog.setMessage("Downloading File:"+downloadFileName+"\n Please Wait..");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                 //downlaod completed

                    pdialog.dismiss();
                    Log.e(TAG,"Download completed");

                   // Toast.makeText(context, "File Downloaded. Storage/HDInfra/Notes/"+downloadFileName, Toast.LENGTH_LONG).show();
                    pdialog.dismiss();
                    AlertDialog.Builder aldialog= new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                    aldialog.setView(R.layout.dialog);
                    Typeface face =Typeface.create("sans-serif-medium",Typeface.NORMAL);
                    aldialog.setTitle("FILE DOWNLOADED");
                    aldialog.setMessage("Path:"+apkStorage+"\nFile Name:"+downloadFileName+"");
                    //aldialog.setTypeface(face);

                    aldialog.show();

                } else {


                    Log.e(TAG, "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();



            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }


                //Get File if SD card is present
               // if (new CheckForSDCard().isSDCardPresent()) {
                apkStorage  = new File(Environment.getExternalStorageDirectory(), "HDInfra/Notes");

                    /*apkStorage = new File(
                            Environment.getExternalStorageDirectory() + "/"
                                    +"HDInfra/Notes");*/
               // } else
                 //   Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();





                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdirs();
                    Log.e(TAG, "Directory Created.");
                }



                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File



                //Create New File if not present
                if (!outputFile.exists()) {
                outputFile.createNewFile();

                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }

            return null;
        }
    }


}


