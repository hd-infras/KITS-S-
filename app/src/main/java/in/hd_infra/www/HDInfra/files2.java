package in.hd_infra.www.HDInfra;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.support.design.widget.Snackbar;

import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class files2 extends Activity  {
    download download;
    EditText years, regulation;
    private RecyclerView mRVFishPrice;
    private AdapterFiles mAdapter;
    View ChildView,view;
    int RecyclerViewClickedItemPOS;
    ArrayList<String> SubjectNames;
    ArrayList<String> SubjectNames2;
    RadioGroup radioGroupBranch;
    RadioGroup radioGroupBranch2;
    RadioGroup radioGroupyear;
    download downloadManager;
    AdapterFiles cat;
    long reference;
    Context context;
    Intent intent;
    LinearLayout coordinatorLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.files);

        regulation = (EditText) findViewById(R.id.textregulation);

        radioGroupBranch = (RadioGroup) findViewById(R.id.radiobranch);

        radioGroupyear = (RadioGroup) findViewById(R.id.radioyear);

        coordinatorLayout = (LinearLayout) findViewById(R.id
                .coordinatorLayout);


        findViewById(R.id.Fetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server

// Check internet connection and accrding to state change the
                // text of activity by calling method

                   conn();
                } });

    }
public void conn(){

    if (InternetHelper.checkconnection(files2.this)) {
        sort();
    } else {
        int color;
        color = getResources().getColor(R.color.red);

                /*   Snackbar snackbar = Snackbar
                   .make(view, "nn", 5);

                    snackbar.show();*/
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (InternetHelper.checkconnection(files2.this)) {
                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Connection restored!", Snackbar.LENGTH_LONG);
                            snackbar1.show();
                        } else {
                            conn();
                        }
                    }
                });

        snackbar.show();
    }
}

    protected void sort() {


        final String regu =regulation.getText().toString().trim();

        final String yearss = ((RadioButton) findViewById(radioGroupyear.getCheckedRadioButtonId())).getText().toString();
        final String branchs= ((RadioButton) findViewById(radioGroupBranch.getCheckedRadioButtonId())).getText().toString();

         class RegisterUser extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(files2.this);
           // private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                Request requestHandler = new Request();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("reg", regu);
                params.put("year", yearss);
                params.put("branch", branchs);



                    return requestHandler.sendPostRequest(urls.URL_FILES2, params);

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
               // progressBar = (ProgressBar) findViewById(R.id.progressBar);
               // progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String dataa) {

                //this method will be running on UI thread

               // pdLoading.dismiss();
              //  progressBar.setVisibility(View.GONE);
                List<DataFiles> data = new ArrayList<>();
                SubjectNames = new ArrayList<>();
                SubjectNames2 = new ArrayList<>();

               // pdLoading.dismiss();
                //progressBar.setVisibility(View.GONE);
                try {

                        JSONArray obj = new JSONArray(dataa);

                        // Extract data from json and store into ArrayList as class objects
                        for (int i = 0; i < obj.length(); i++) {
                            JSONObject json_data = obj.getJSONObject(i);
                            DataFiles fishData = new DataFiles();

                                fishData.img = json_data.getString("img");
                                fishData.bname = json_data.getString("name");
                                fishData.url = json_data.getString("url");
                                fishData.reg = json_data.getString("reg");
                                fishData.year = json_data.getString("year");


                                data.add(fishData);
                                SubjectNames.add(json_data.getString("name"));
                            SubjectNames2.add(json_data.getString("url"));


                            }




                    // Setup and Handover data to recyclerview
                    mRVFishPrice = (RecyclerView) findViewById(R.id.files);
                    mAdapter = new AdapterFiles(files2.this, data);
                    mRVFishPrice.setAdapter(mAdapter);
                    mRVFishPrice.setLayoutManager(new LinearLayoutManager(files2.this));
                   /* mRVFishPrice.addItemDecoration(new linedivider(
                            getApplicationContext()


                    )); */

                } catch (JSONException e) {


                    Toast.makeText(files2.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();



                }

//RecyclerView Item click listener code starts from here.
                mRVFishPrice.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

                    GestureDetector gestureDetector = new GestureDetector(files2.this, new GestureDetector.SimpleOnGestureListener() {

                        @Override
                        public boolean onSingleTapUp(MotionEvent motionEvent) {

                            return true;
                        }

                    });

                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                        ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                        if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                            //Getting RecyclerView Clicked item value.
                            RecyclerViewClickedItemPOS = Recyclerview.getChildAdapterPosition(ChildView);

                            //Printing RecyclerView Clicked item clicked value using Toast Message.

                            Uri uri1 = Uri.parse(SubjectNames2.get(RecyclerViewClickedItemPOS));
                            String urs=SubjectNames2.get(RecyclerViewClickedItemPOS);
                            String na=SubjectNames.get(RecyclerViewClickedItemPOS);
                            Toast.makeText(files2.this, SubjectNames.get(RecyclerViewClickedItemPOS), Toast.LENGTH_SHORT).show();
                           /* Intent intent =new Intent(files2.this,pdf.class);
                            intent.putExtra("imagePath", urs);
                            startActivity(intent);*/
                            String ns=SubjectNames.get(RecyclerViewClickedItemPOS);

                            Uri uri = Uri.parse(SubjectNames2.get(RecyclerViewClickedItemPOS));
                            Log.e("Calling URL","pAth : "+uri.toString());

                            new download(files2.this,uri.toString());
                           // new DownloadingTask(uri.toString()).execute();

                            String truncatedPath = uri.toString().replace("http://123.hdinfra.in/download.php?f=","");
                            Log.e("Trunnchated apath : ",truncatedPath);

                                }


                        return false;
                    }
                    @Override
                    public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                });



            }

        }

        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
    private static final String TAG = "Download Task";


    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;
        private String downloadUrl = "", downloadFileName = "";

        DownloadingTask(String downloadUrl){
            this.downloadUrl= downloadUrl;
            downloadFileName = downloadUrl.replace("http://123.hdinfra.in/download.php?f=", "");//Create file name by picking download file name from URL

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e(TAG,"Download started : "+downloadUrl);
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    //downlaod completed
                    Log.e(TAG,"Download completed");
                    Toast.makeText(files2.this, "File is donwloaed : SD CARD > NOTES.", Toast.LENGTH_SHORT).show();
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

                apkStorage = new File(
                        Environment.getExternalStorageDirectory() + "/"
                                +"HDInfra");
                // } else
                //   Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
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

