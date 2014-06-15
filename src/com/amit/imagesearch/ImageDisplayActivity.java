package com.amit.imagesearch;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ImageDisplayActivity extends Activity {
  
  SmartImageView ivImage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_display);
    String url = getIntent().getStringExtra("url");
    ivImage = (SmartImageView) findViewById(R.id.ivResult);
    ivImage.setImageUrl(url);
  }
}
