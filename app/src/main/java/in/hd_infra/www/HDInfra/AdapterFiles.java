package in.hd_infra.www.HDInfra;

import android.app.DownloadManager;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Himamsh K on 20-10-2017.
 */

public class AdapterFiles extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ScheduledExecutorService scheduledExecutorService;
    private Context context;
    private LayoutInflater inflater;
    List<DataFiles> data = Collections.emptyList();
    DataFiles current;
    int currentPos = 0;
    files2 files2;
    DownloadManager downloadManager;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterFiles(Context context, List<DataFiles> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }
    // Bind data
    @Override

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataFiles current=data.get(position);
        myHolder.textname.setText("Subject:"+current.bname);
        myHolder.textyear.setText("Year:"+current.year);
        myHolder.textreg.setText("Regulation:"+current.reg);

        //myHolder.texturl.setText(current.url );
       // myHolder.texturl.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        // load image into imageview using glide
        Glide.with(context).load("http://123.hdinfra.in/images/"+ current.img+".png")
                //.placeholder(R.drawable.ic_img_error)
                //.error(R.drawable.ic_img_error)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(myHolder.fileimg);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textname;
        ImageView fileimg;
        TextView textreg;
        TextView textyear;
        TextView texturl;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textname= (TextView) itemView.findViewById(R.id.textname11);
            fileimg= (ImageView) itemView.findViewById(R.id.fileimg);
            textreg=(TextView) itemView.findViewById(R.id.reg_txt);
            textyear=(TextView) itemView.findViewById(R.id.year);

            texturl = (TextView) itemView.findViewById(R.id.texturl);
        }

    }




}

