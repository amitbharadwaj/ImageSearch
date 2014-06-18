package com.amit.imagesearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * 
 * GsearchResultClass: "GimageSearch",
 * width: "6597",
 * height: "6001",
 * imageId: "ANd9GcTwhzScppfjpmdnTpjnDnO6zv1IvjVcA3QuZ_ZOcT_St78xXOST7FHzWrcd",
 * tbWidth: "150",
 * tbHeight: "136",
 * unescapedUrl: "http://sweetclipart.com/multisite/sweetclipart/files/monkey_with_banana.png",
 * url: "http://sweetclipart.com/multisite/sweetclipart/files/monkey_with_banana.png",
 * visibleUrl: "sweetclipart.com",
 * title: "Cute <b>Monkey</b> With Banana - Free Clip Art",
 * titleNoFormatting: "Cute Monkey With Banana - Free Clip Art",
 * originalContextUrl: "http://sweetclipart.com/cute-monkey-banana-635",
 * content: "Cute <b>Monkey</b> Clip Art",
 * contentNoFormatting: "Cute Monkey Clip Art",
 * tbUrl: "http://t3.gstatic.com/im
 */

public class ImageResult {
  private String url;
  private String tbUrl;
  private int width;
  private int height;
  private int tbWidth;
  private int tbHeight;

  public ImageResult(JSONObject jsonObject) {
    try {
      this.url = jsonObject.getString("url");
      this.tbUrl = jsonObject.getString("tbUrl");
      this.width = jsonObject.getInt("width");
      this.height = jsonObject.getInt("height");
      this.tbWidth = jsonObject.getInt("tbWidth");
      this.tbHeight = jsonObject.getInt("tbHeight");

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

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getTbWidth() {
    return tbWidth;
  }

  public void setTbWidth(int tbWidth) {
    this.tbWidth = tbWidth;
  }

  public int getTbHeight() {
    return tbHeight;
  }

  public void setTbHeight(int tbHeight) {
    this.tbHeight = tbHeight;
  }

  @Override
  public String toString() {
    return "ImageResult [url=" + url + ", tbUrl=" + tbUrl + "]";
  }

  public static Collection<ImageResult> fromJsonArray(JSONArray array) {
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
