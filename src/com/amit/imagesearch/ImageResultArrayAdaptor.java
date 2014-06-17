package com.amit.imagesearch;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ortiz.touch.TouchImageView;

public class ImageResultArrayAdaptor extends ArrayAdapter<ImageResult> {

  static class ViewHolder {
    SmartImageView ivImage;
    //    TouchImageView ivImage;
  }

  //  private final ImageLoader imageLoader;
  private final int ivLayoutResource;

  public ImageResultArrayAdaptor(Context context, int resourceId, List<ImageResult> images) {
    super(context, resourceId, images);
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

    viewHolder.ivImage.setImageUrl(imageResult.getTbUrl());

    //    final ViewHolder holder = viewHolder; // just to conver to final
    //
    //    imageLoader.loadImage(imageResult.getTbUrl(), new ImageLoadingListener() {
    //
    //      @Override
    //      public void onLoadingStarted(String imageUri, View view) {
    //
    //      }
    //
    //      @Override
    //      public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
    //
    //      }
    //
    //      @Override
    //      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
    //        holder.ivImage.setImageBitmap(loadedImage);
    //      }
    //
    //      @Override
    //      public void onLoadingCancelled(String imageUri, View view) {
    //
    //      }
    //    });

    return convertView;
  }
}
