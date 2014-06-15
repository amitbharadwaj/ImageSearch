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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SearchResultsActivity extends Activity {
  
  SharedPreferences mPreferences;
  private GridView gvResults;

  List<ImageResult> imageResutls = new ArrayList<ImageResult>();
  ImageResultArrayAdaptor irArrayAdaptor;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_results);
    mPreferences = getSharedPreferences(SettingsActivity.SETTINGS_FILE, 0);
    
    gvResults = (GridView) findViewById(R.id.gvResults);
    irArrayAdaptor = new ImageResultArrayAdaptor(this, imageResutls);
    gvResults.setAdapter(irArrayAdaptor);
    
    gvResults.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
        ImageResult imageResult = imageResutls.get(position);
        intent.putExtra("url", imageResult.getUrl());
        startActivity(intent);
      }
    });
    
    handleIntent(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);
      //use the query to search your data somehow
      
      RequestParams rp = new RequestParams();
      rp.add("q", query);
      
      addParameters(rp);
      
      AsyncHttpClient asClient = new AsyncHttpClient();
      asClient.get("https://ajax.googleapis.com/ajax/services/search/images?v=1.0", rp, 
          new JsonHttpResponseHandler() {
        
        @Override
        public void onSuccess(JSONObject response) {
          JSONArray imageJsonArray = null;
          try {
            imageJsonArray = response.getJSONObject("responseData").getJSONArray("results");
            imageResutls.clear();
            irArrayAdaptor.addAll(ImageResult.fromJsonArray(imageJsonArray));
            Log.d("DEBUG", imageResutls.toString());
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
      });
    } else {
      Toast.makeText(this, "USe search button to Query", Toast.LENGTH_LONG).show();
    }
  }

  private void addParameters(RequestParams rp) {
    String imageType = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_TYPE, null);    
    if ( imageType != null && !imageType.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_TYPE, imageType.toLowerCase(Locale.US));
    }
    
    String imageSize = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SIZE, null);    
    if ( imageSize != null && !imageSize.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_SIZE, imageSize.toLowerCase(Locale.US));
    }

    String imageColor = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_COLOR, null);    
    if ( imageColor != null && !imageColor.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_COLOR, imageColor.toLowerCase(Locale.US));
    }

    String imageSafety = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SAFETY, null);    
    if ( imageSafety != null && !imageSafety.isEmpty()) {
      rp.add(SettingsActivity.SETTINGS_KEY_IMAGE_SAFETY, imageSafety.toLowerCase(Locale.US));
    }

    String imageSiteFilter = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SITE, null);    
    if ( imageSiteFilter != null && !imageSiteFilter.isEmpty()) {
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
    startActivity(intent);
  }
}
