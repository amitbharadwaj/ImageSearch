package com.amit.imagesearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.Toast;
import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SearchResultsActivity extends Activity {

  SharedPreferences mPreferences;
  private String query;

  //  private GridView gvResults;
  private StaggeredGridView sgvResults;
  List<ImageResult> imageResults = new ArrayList<ImageResult>();
  ImageResultArrayAdaptor irArrayAdaptor;

  public static final int SETTINGS_REQUEST_CODE = 10;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_results);
    mPreferences = getSharedPreferences(SettingsActivity.SETTINGS_FILE, 0);

    irArrayAdaptor = new ImageResultArrayAdaptor(this, R.layout.item_image_results, imageResults);
    sgvResults = (StaggeredGridView) findViewById(R.id.sgvResults);
    sgvResults.setAdapter(irArrayAdaptor);

    //    gvResults = (GridView) findViewById(R.id.gvResults);
    //    gvResults.setAdapter(irArrayAdaptor);
    //
    //    gvResults.setOnItemClickListener(new OnItemClickListener() {
    //
    //      @Override
    //      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //        Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
    //        ImageResult imageResult = imageResults.get(position);
    //        intent.putExtra("url", imageResult.getUrl());
    //        startActivity(intent);
    //      }
    //    });
    //
    //    gvResults.setOnScrollListener(new EndlessScrollListener() {
    //      @Override
    //      public void onLoadMore(int page, int totalItemsCount) {
    //        // Triggered only when new data needs to be appended to the list
    //        // Add whatever code is needed to append new items to your AdapterView
    //        loadDataFromApi(page);
    //        // or customLoadMoreDataFromApi(totalItemsCount); 
    //      }
    //    });

    sgvResults.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
        ImageResult imageResult = imageResults.get(position);
        intent.putExtra("url", imageResult.getUrl());
        startActivity(intent);
      }

    });

    sgvResults.setOnScrollListener(new EndlessScrollListener() {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        // Triggered only when new data needs to be appended to the list
        // Add whatever code is needed to append new items to your AdapterView
        loadDataFromApi(page);
        // or customLoadMoreDataFromApi(totalItemsCount); 
      }
    });

    handleIntent(getIntent());
  }

  private void loadDataFromApi(int page) {
    RequestParams rp = new RequestParams();
    rp.add("q", query);
    rp.add("start", String.valueOf(4 * page));

    addParameters(rp);
    if (!NetworkHelper.isNetworkAvailable(getApplicationContext())) {
      Toast.makeText(this, "Can't find internet. Please check internet and try again",
          Toast.LENGTH_LONG).show();
      return;
    }

    AsyncHttpClient asClient = new AsyncHttpClient();
    asClient.get("https://ajax.googleapis.com/ajax/services/search/images?v=1.0", rp,
        new JsonHttpResponseHandler() {

          @Override
          public void onSuccess(JSONObject response) {
            JSONArray imageJsonArray = null;
            try {
              imageJsonArray = response.getJSONObject("responseData").getJSONArray("results");
              // imageResutls.clear();
              irArrayAdaptor.addAll(ImageResult.fromJsonArray(imageJsonArray));
              // Log.d("DEBUG", imageResutls.toString());
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Toast.makeText(this, query, Toast.LENGTH_LONG).show();
    // REQUEST_CODE is defined above
    if (resultCode == RESULT_OK && requestCode == SETTINGS_REQUEST_CODE) {
      irArrayAdaptor.clear();
      loadDataFromApi(0);
    }
  }

  @Override
  protected void onNewIntent(Intent intent) {
    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      query = intent.getStringExtra(SearchManager.QUERY);
      irArrayAdaptor.clear();
      loadDataFromApi(0);
    } else {
      Toast.makeText(this, "USe search button to Query", Toast.LENGTH_LONG).show();
    }
  }

  private void addParameters(RequestParams rp) {
    String imageType = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_TYPE, null);
    if (imageType != null && !imageType.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_TYPE, imageType.toLowerCase(Locale.US));
    }

    String imageSize = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SIZE, null);
    if (imageSize != null && !imageSize.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_SIZE, imageSize.toLowerCase(Locale.US));
    }

    String imageColor = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_COLOR, null);
    if (imageColor != null && !imageColor.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_COLOR, imageColor.toLowerCase(Locale.US));
    }

    String imageSafety = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SAFETY, null);
    if (imageSafety != null && !imageSafety.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_SAFETY, imageSafety.toLowerCase(Locale.US));
    }

    String imageSiteFilter = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SITE, null);
    if (imageSiteFilter != null && !imageSiteFilter.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_SITE, imageSiteFilter.toLowerCase(Locale.US));
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_action_bar, menu);

    // Associate searchable configuration with the SearchView
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.miSearch).getActionView();
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    // searchView.setIconifiedByDefault(false);
    return true;
  }

  public void onSettings(MenuItem mi) {
    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
    startActivityForResult(intent, SETTINGS_REQUEST_CODE);
  }
}
