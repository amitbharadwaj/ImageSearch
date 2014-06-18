package com.amit.imagesearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ortiz.touch.TouchImageView;

public class ImageDisplayActivity extends Activity {

  // SmartImageView ivImage;
  TouchImageView ivImage;

  private ImageLoader imageLoader;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_display);
    String url = getIntent().getStringExtra("url");
    //    ivImage = (SmartImageView) findViewById(R.id.ivResult);
    //    ivImage.setImageUrl(url);

    ivImage = (TouchImageView) findViewById(R.id.ivResult);

    imageLoader = ImageLoader.getInstance();
    imageLoader.init(ImageLoaderConfiguration.createDefault(this));

    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_search) // resource or drawable
        .showImageForEmptyUri(R.drawable.ic_search) // resource or drawable
        .showImageOnFail(R.drawable.ic_search) // resource or drawable
        .resetViewBeforeLoading(false)  // default
        // .delayBeforeLoading(1000)
        .cacheInMemory(true) // default
        .cacheOnDisk(false) // default
        // .preProcessor(...)
        // .postProcessor(...)
        // .extraForDownloader(...)
        // .considerExifParams(false) // default
        // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
        .bitmapConfig(Bitmap.Config.ARGB_8888) // default
        // .decodingOptions(...)
        // .displayer(new SimpleBitmapDisplayer()) // default
        // .handler(new Handler()) // default
        .build();
    ;

    imageLoader.loadImage(url, options, new ImageLoadingListener() {

      @Override
      public void onLoadingStarted(String imageUri, View view) {

      }

      @Override
      public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        Log.e("Faield URL", imageUri);
        Toast.makeText(ImageDisplayActivity.this, failReason.getCause().getMessage(),
            Toast.LENGTH_LONG).show();
      }

      @Override
      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        ivImage.setImageBitmap(loadedImage);
      }

      @Override
      public void onLoadingCancelled(String imageUri, View view) {

      }
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_share, menu);
    return true;
  }

  public void onShare(MenuItem mi) {
    ImageView ivImage = (ImageView) findViewById(R.id.ivResult);
    // Get access to the URI for the bitmap
    Uri bmpUri = getLocalBitmapUri(ivImage);
    if (bmpUri != null) {
      // Construct a ShareIntent with link to image
      Intent shareIntent = new Intent();
      shareIntent.setAction(Intent.ACTION_SEND);
      shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
      shareIntent.setType("image/*");
      // Launch sharing dialog for image
      startActivity(Intent.createChooser(shareIntent, "Share Image"));
    } else {
      // ...sharing failed, handle error
    }
  }

  //Returns the URI path to the Bitmap displayed in specified ImageView
  public Uri getLocalBitmapUri(ImageView imageView) {
    // Extract Bitmap from ImageView drawable
    Drawable drawable = imageView.getDrawable();
    Bitmap bmp = null;
    if (drawable instanceof BitmapDrawable) {
      bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    } else {
      return null;
    }
    // Store image to default external storage directory
    Uri bmpUri = null;
    try {
      File file = new File(Environment.getExternalStoragePublicDirectory(
          Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
      file.getParentFile().mkdirs();
      FileOutputStream out = new FileOutputStream(file);
      bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
      out.close();
      bmpUri = Uri.fromFile(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bmpUri;
  }

}
