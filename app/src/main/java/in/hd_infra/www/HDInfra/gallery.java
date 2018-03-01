package in.hd_infra.www.HDInfra;

/**
 * Created by Himamsh K on 17-09-2017.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;


public class gallery extends Fragment {
    int mutedColor = R.attr.colorPrimary;
    CollapsingToolbarLayout collapsingToolbar;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){


        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        super.onViewCreated(view,savedInstanceState);
        getActivity().setTitle("GAlLLERY");




    }
}
