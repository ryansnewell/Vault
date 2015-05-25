package com.example.kingdoof.vault;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by kingdoof on 5/23/15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private int layoutResourceId;
    private File galleryDir;
    private ArrayList<Uri> uriArrayList;

    public ImageAdapter(Context c, int layoutResourceId, File file)
    {
        mContext = c;
        this.layoutResourceId = layoutResourceId;
        galleryDir = file;
        uriArrayList = new ArrayList<Uri>();
        for(File f : galleryDir.listFiles())
        {
            Uri u = Uri.fromFile(f);
            uriArrayList.add(u);
        }
    }

    public int getCount(){ return uriArrayList.size(); }

    public Object getItem(int pos) { return uriArrayList.get(pos); }

    public long getItemId(final int pos) { return pos; }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ImageView imageView = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            imageView = (ImageView) row.findViewById(R.id.imageGridView);
            row.setTag(imageView);
        } else {
            imageView = (ImageView) row.getTag();
        }

        imageView.setImageURI(uriArrayList.get(position));

        return row;

    }

    public Integer[] mThumbIds ={
            R.drawable.horse1, R.drawable.horse2,
            R.drawable.horse3, R.drawable.horse4,
            R.drawable.horse5, R.drawable.horse6
    };

}
