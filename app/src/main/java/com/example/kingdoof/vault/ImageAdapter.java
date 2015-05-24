package com.example.kingdoof.vault;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kingdoof on 5/23/15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private File galleryDir;
    private ArrayList<Uri> uriArrayList;

    public ImageAdapter(Context c)
    {
        mContext = c;
    }

    public void setGalleryDir(File f)
    {
        galleryDir = f;
        uriArrayList = new ArrayList<Uri>();
        for(File file : galleryDir.listFiles())
        {
            Uri u = Uri.fromFile(file);
            Log.d("Filename", file.getName());
            uriArrayList.add(u);
        }
    }

    public int getCount(){
        return uriArrayList.size();
    }

    public Object getItem(int pos)
    {
        return uriArrayList.get(pos);
    }

    public long getItemId(final int pos)
    {
        return pos;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //Uri uri = uriArrayList.get(position);
        //Log.d("URI", uri.getPath());
        //Bitmap bitmap;
        //try {
        //    bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
        //} catch (Exception e) {
        //    bitmap =  null;
        //}
        imageView.setImageURI(uriArrayList.get(position));
        //imageView.setImageResource(mThumbIds[position]);
        return imageView;

    }

    public Integer[] mThumbIds ={
            R.drawable.horse1, R.drawable.horse2,
            R.drawable.horse3, R.drawable.horse4,
            R.drawable.horse5, R.drawable.horse6
    };

}
