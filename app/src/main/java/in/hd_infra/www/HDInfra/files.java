package in.hd_infra.www.HDInfra;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class files extends Activity {
    View ChildView, view;
    int RecyclerViewClickedItemPOS;
    ArrayList<String> titles;
    ArrayList<String> bodyy;
    ArrayList<String> imageurs;
    ArrayList<String> enddates;
    ArrayList<String> startdates;
    ArrayList<String> subjecct;
    private RecyclerView mRVFishPrice;
    private N_AdapterFiles mAdapter;
    List<DataFiles> datan;
    Notify notify;
    JSONArray jArray;
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final int READ_TIMEOUT = 1005000;
    SwipeRefreshLayout swipeLayout;
    LinearLayout coordinatorLayout;
    DataFiles fishDatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        coordinatorLayout = (LinearLayout) findViewById(R.id
                .layoutss);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.refreshview);
// set color schemes on refresh view
        swipeLayout.setColorSchemeResources(R.color.blue, R.color.red,
                R.color.green, R.color.colorAccent, R.color.yellow,
                R.color.cyan);
// implement refresh listener
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                // call this method for repopulating recycler view with new data
                refreshRecyclerView();




            }
        });

        tester();

    }
    public void tester(){

        if(InternetHelper.checkconnection(files.this)) {


             new AsyncFetch().execute();

        }
        else{
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (InternetHelper.checkconnection(files.this)) {
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Connection restored!", Snackbar.LENGTH_LONG);
                                snackbar1.show();
                            } else {
                               tester();
                            }
                        }
                    });

            snackbar.show();

        }


    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(files.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://123.hdinfra.in/notify/notification_pull.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }







        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread


             datan=new ArrayList<>();
           titles = new ArrayList<>();
            bodyy= new ArrayList<>();
            subjecct= new ArrayList<>();
            imageurs= new ArrayList<>();
            enddates= new ArrayList<>();
            startdates= new ArrayList<>();



            try {

                 jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                     fishDatan = new DataFiles();
                    fishDatan.titles= json_data.getString("title");
                    fishDatan.subject= json_data.getString("subject");
                    fishDatan.body= json_data.getString("body");
                    fishDatan.imageurl= json_data.getString("image");

                    fishDatan.end_date= json_data.getString("end_date");
                    fishDatan.start_date=json_data.getString("start_date");
                    datan.add(fishDatan);
                    titles.add(json_data.getString("title"));
                    subjecct.add(json_data.getString("subject"));
                    bodyy.add(json_data.getString("body"));
                    imageurs.add(json_data.getString("image"));
                    enddates.add(json_data.getString("end_date"));
                    startdates.add(json_data.getString("start_date"));
                }

                // Setup and Handover data to recyclerview
                mRVFishPrice = (RecyclerView)findViewById(R.id.list);
                mAdapter = new N_AdapterFiles(files.this, datan);
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(files.this));

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(files.this, e.toString(), Toast.LENGTH_LONG).show();
            }
//RecyclerView Item click listener code starts from here.
            mRVFishPrice.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

                GestureDetector gestureDetector = new GestureDetector(files.this, new GestureDetector.SimpleOnGestureListener() {

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

                        String titll=titles.get(RecyclerViewClickedItemPOS);
                        String body=bodyy.get(RecyclerViewClickedItemPOS);
                        String subject=subjecct.get(RecyclerViewClickedItemPOS);
                        String images=imageurs.get(RecyclerViewClickedItemPOS);
                        String enddate=enddates.get(RecyclerViewClickedItemPOS);
                        String startdate=startdates.get(RecyclerViewClickedItemPOS);
                           // notify.not(files.this,title,body,subject,images,startdate);

                        Intent i = new Intent(files.this, Notify.class);

                        i.putExtra("titles", titll);
                        i.putExtra("body", body);
                        i.putExtra("subject", subject);
                        i.putExtra("images", images);
                        i.putExtra("startdate", startdate);

                        startActivity(i);

                       //i.putExtra("titles", title);



                        //Toast.makeText(files.this, SubjectNames.get(RecyclerViewClickedItemPOS), Toast.LENGTH_SHORT).show();
                           /* Intent intent =new Intent(files2.this,pdf.class);
                            intent.putExtra("imagePath", urs);
                            startActivity(intent);*/
                       // String ns=SubjectNames.get(RecyclerViewClickedItemPOS);



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


    private void refreshRecyclerView() {

        // Handler to show refresh for a period of time you can use async task
        // while commnunicating serve
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

             // notify adapter
                if (InternetHelper.checkconnection(files.this)){

                }
                else{
                tester();}
               // new AsyncFetch().execute();
                swipeLayout.setRefreshing(false);// set swipe refreshing



                // Toast for task completion
                Toast.makeText(files.this, "Updated.",
                        Toast.LENGTH_SHORT).show();

            }
        }, 3000);
    }

    }




