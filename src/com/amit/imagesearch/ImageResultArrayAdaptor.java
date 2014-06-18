package com.amit.imagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdaptor extends ArrayAdapter<ImageResult> {

  static class ViewHolder {
    SmartImageView ivImage;
    //    TouchImageView ivImage;
  }

  //  private final ImageLoader imageLoader;
  private final int ivLayoutResource;

  private final Context context;

  public ImageResultArrayAdaptor(Context context, int resourceId, List<ImageResult> images) {
    super(context, resourceId, images);
    this.context = context;
    ivLayoutResource = resourceId;
    //    imageLoader = ImageLoader.getInstance();
    //    imageLoader.init(ImageLoaderConfiguration.createDefault(context));
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ImageResult imageResult = this.getItem(position);
    ViewHolder viewHolder;

    if (convertView == null) {
      LayoutInflater inflator = LayoutInflater.from(getContext());
      convertView = (SmartImageView) inflator.inflate(ivLayoutResource, parent, false);
      //  convertView = (TouchImageView) inflator.inflate(ivLayoutResource, parent, false);

      viewHolder = new ViewHolder();
      viewHolder.ivImage = (SmartImageView) convertView;
      //      viewHolder.ivImage = (TouchImageView) convertView;
      convertView.setTag(viewHolder);
    } else {
      //      ivImage = (SmartImageView) convertView;
      //      ivImage.setImageResource(android.R.color.transparent);
      viewHolder = (ViewHolder) convertView.getTag();
    }

    //    LayoutParams params = new LayoutParams(imageResult.getTbWidth(), imageResult.getTbHeight());
    //    viewHolder.ivImage.setLayoutParams(params);
    if (NetworkHelper.isNetworkAvailable(context)) {
      viewHolder.ivImage.setImageUrl(imageResult.getTbUrl());
    } else {
      viewHolder.ivImage.setImageResource(R.drawable.ic_launcher);
      Toast.makeText(context, "No Internet", Toast.LENGTH_LONG).show();
    }

    return convertView;
  }
}
