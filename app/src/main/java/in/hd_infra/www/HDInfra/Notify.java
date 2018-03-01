package in.hd_infra.www.HDInfra;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v7.graphics.Palette;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class Notify extends AppCompatActivity {
    int mutedColor = R.attr.colorPrimary;
    CollapsingToolbarLayout collapsingToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    TextView date,body;
    ImageView bann;
    private Context context;
    public String titless,bodyys,subjects,imagess,enddates,startdates;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_layout);
      /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbarLayout.setTitle("WELCOME");

       // dynamicToolbarColor();
       // toolbarTextAppernce();
        date=(TextView)findViewById(R.id.dates);
        body=(TextView) findViewById(R.id.body);
       collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
         bann = (ImageView) findViewById(R.id.header);

        final ImageView image = (ImageView) findViewById(R.id.header);

      final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.head);

       Bundle extras = getIntent().getExtras();
        titless = extras.getString("titles");
        bodyys = extras.getString("body");
        imagess = extras.getString("images");
        subjects = extras.getString("subject");
      met(titless,subjects,bodyys,imagess);




       /* Glide.with(Notify.this).load(R.drawable.head)
                //.placeholder(R.drawable.ic_img_error)
                //.error(R.drawable.ic_img_error)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
               .into(image );*/

      Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                image.setImageBitmap(bitmap);
                int defaultColor = getResources().getColor(R.color.black_trans80);
                collapsingToolbar.setContentScrimColor(defaultColor);
                collapsingToolbar.setStatusBarScrimColor(defaultColor);
            }
        });


    }


    /*public void onBackPressed(){
        super.onBackPressed();
        finish();
    }*/

          @Override
          public boolean onOptionsItemSelected(MenuItem item) {
              switch (item.getItemId()) {
                  case android.R.id.home:
                      // todo: goto back activity from here

                      Intent intent = new Intent(Notify.this, files.class);
                      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                      finish();



                      return true;

                  default:
                      return super.onOptionsItemSelected(item);
              }
          }
   /* public void not(Context context,String title,String body,String subject,String images,String startdate){
        this.context = context;
        this.titles=title;
        this.subjects=subject;
        this.bodyys=body;
        this.imagess=images;
        this.startdates=startdate;
        met(titles,subjects,bodyys,imagess);

    }*/

    public void met(String s,String h,String b,String im){
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(s);
        date.setText(h);
        body.setText(b);
        Glide.with(Notify.this).load(im)
                //.placeholder(R.drawable.ic_img_error)
                //.error(R.drawable.ic_img_error)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(bann );


    }

        }


