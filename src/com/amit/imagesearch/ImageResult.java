package com.amit.imagesearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
  private String url;
  private String tbUrl;
  
  public ImageResult(JSONObject jsonObject) {
    try {
      this.url = jsonObject.getString("url");
      this.tbUrl = jsonObject.getString("tbUrl");
    } catch (JSONException e) {
      e.printStackTrace();
    }    
  }
  
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getTbUrl() {
    return tbUrl;
  }
  public void setTbUrl(String tbUrl) {
    this.tbUrl = tbUrl;
  }
  
  
  @Override
  public String toString() {
    return "ImageResult [url=" + url + ", tbUrl=" + tbUrl + "]";
  }

  public static Collection< ImageResult> fromJsonArray(JSONArray array) {
    List<ImageResult> imageResults = new ArrayList<ImageResult>();
    for (int i = 0; i < array.length(); i++) {
      try {
        imageResults.add(new ImageResult(array.getJSONObject(i)));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return imageResults;
  }
  
}
