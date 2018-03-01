package in.hd_infra.www.HDInfra;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.List;

/**
 * Created by Himamsh K on 04-12-2017.
 */

public class N_AdapterFiles extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<DataFiles> datan = Collections.emptyList();
    DataFiles current;
    int currentPos = 0;


    // create constructor to innitilize context and data sent from MainActivity
    public N_AdapterFiles(Context context, List<DataFiles> datan) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.datan = datan;

    }
    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.ncontainer, parent,false);
        N_AdapterFiles.MyHolder holder=new N_AdapterFiles.MyHolder(view);
        return holder;
    }
    @Override

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        N_AdapterFiles.MyHolder myHolder= (N_AdapterFiles.MyHolder) holder;
        DataFiles current=datan.get(position);
        myHolder.textname.setText(""+current.titles);
        myHolder.textyear.setText("Date Added:"+current.start_date);
        myHolder.textreg.setText("Subject:"+current.subject);

       // myHolder.texturl.setText(current.url );
       // myHolder.texturl.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        // load image into imageview using glide
        Glide.with(context).load(current.imageurl)
                //.placeholder(R.drawable.ic_img_error)
                //.error(R.drawable.ic_img_error)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(myHolder.fileimg);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return datan.size();
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
