package com.example.kingdoof.vault;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;


public class Gallery extends ActionBarActivity {

    private String dirPath;
    private File galleryDir;
    private int numOfPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        dirPath = getFilesDir().getAbsolutePath() + File.separator + "photos";
        galleryDir = new File(dirPath);
        if(!galleryDir.exists())
            galleryDir.mkdir();
        numOfPhotos = 0;
        for(File f : galleryDir.listFiles())
        {
            numOfPhotos++;
            Log.d("FILE", f.toURI().toString());
        }

        ImageAdapter ia = new ImageAdapter(this);
        ia.setGalleryDir(galleryDir);
        GridView gridView = (GridView) findViewById(R.id.imageGridView);
        gridView.setAdapter(ia);

        //TODO onclick

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickPhotoPicker(MenuItem mi)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Uri chosenImageUri = data.getData();
            Log.d("Extension", chosenImageUri.getPath());
            //Log.d("Ext", chosenImageUri.get)
            try {
                File copiedPhoto = new File(galleryDir + File.separator + ++numOfPhotos);
                copiedPhoto.createNewFile();

                copyFile(chosenImageUri), copiedPhoto);
            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    private void copyFile(Uri sourceUri, File destFile) throws IOException {
        if (sourceUri == null) {
            return;
        }

        InputStream source = null;
        FileChannel destination = null;

        source = getContentResolver().openInputStream(sourceUri);
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.);
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }


    }

}