package com.example.kingdoof.vault;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
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

    private File galleryDir;
    private int numOfPhotos;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        galleryDir = new File(getFilesDir(), "photos");
        Log.d("dirPath", getFilesDir().toString());
        Log.d("galleryDir", galleryDir.toString());
        if(!galleryDir.exists())
            galleryDir.mkdir();
        numOfPhotos = 0;
        for(File f : galleryDir.listFiles())
        {
            numOfPhotos++;
            Log.d("FILE", f.toURI().toString());
        }
        ImageAdapter ia = new ImageAdapter(this, R.layout.grid_item_layout, galleryDir);
        gridView = (GridView) findViewById(R.id.gridView);
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
            //Log.d("Ext", chosenImageUri.get)
            try {
                //File copiedPhoto = new File(galleryDir + File.separator + ++numOfPhotos);
                //copiedPhoto.createNewFile();

                InputStream imageStream = getContentResolver().openInputStream(chosenImageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                String scheme = chosenImageUri.getScheme();
                Log.d("URI Scheme", scheme);
                String filename = "";
                if (scheme.equals("content")){
                    String[] proj = { MediaStore.Images.Media.TITLE };
                    Cursor cursor = getApplicationContext().getContentResolver().query(chosenImageUri, proj, null, null, null);
                    if(cursor != null && cursor.getCount() != 0)
                    {
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
                        //int typeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.CONTENT_TYPE);
                        cursor.moveToFirst();
                        filename = cursor.getString(columnIndex);
                        Log.d("Filename", filename);
                        //Log.d("Content-Type", cursor.getString(typeIndex));
                        Log.d("MIME", getContentResolver().getType(chosenImageUri));
                    }
                    if(cursor != null) {
                        cursor.close();
                    }
                } else {
                    filename = chosenImageUri.getLastPathSegment();
                    Log.d("Filename", filename);
                }
                File destFile = new File(galleryDir, filename);

                copyFile(selectedImage, destFile);
                gridView.invalidateViews();
            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    private void copyFile(Bitmap bitmap, File destFile) throws IOException {

        FileOutputStream fos = new FileOutputStream(destFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
        Log.d("copyFile to", destFile.toString());
    }

}