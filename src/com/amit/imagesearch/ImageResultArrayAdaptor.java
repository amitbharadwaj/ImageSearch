package com.amit.imagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdaptor extends ArrayAdapter<ImageResult> {
  public ImageResultArrayAdaptor(Context context, List<ImageResult> images) {
    super(context, R.layout.item_image_results, images);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ImageResult imageResult = this.getItem(position);
    SmartImageView ivImage;
    if (convertView == null) {
      LayoutInflater inflator = LayoutInflater.from(getContext());
      ivImage = (SmartImageView) inflator.inflate(R.layout.item_image_results, parent, false);
    } else {
      ivImage = (SmartImageView) convertView;
      ivImage.setImageResource(android.R.color.transparent);
    }
    
    ivImage.setImageUrl(imageResult.getTbUrl());
    return ivImage;
  }

  
}
