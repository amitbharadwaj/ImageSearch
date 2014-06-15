package com.amit.imagesearch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

  public static final String SETTINGS_FILE = "settings";
  public static final String SETTINGS_KEY_IMAGE_TYPE = "imgtype";
  public static final String SETTINGS_KEY_IMAGE_SIZE = "imgsz";
  public static final String SETTINGS_KEY_IMAGE_COLOR = "imagecolor";
  public static final String SETTINGS_KEY_IMAGE_SAFETY = "safe";
  public static final String SETTINGS_KEY_IMAGE_SITE = "as_sitesearch";


  SharedPreferences mPreferences;
  
  Spinner spinnerImageType;
  Spinner spinnerImageSize;
  Spinner spinnerImageColor;
  Spinner spinnerImageSafety;
  EditText etSiteFilter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    mPreferences = getSharedPreferences(SETTINGS_FILE, 0);
    
    setupSettingsForm();


  }
  
  private void setupSettingsForm() {
    spinnerImageType = (Spinner) findViewById(R.id.spinnerImageType);
    ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
        R.array.arrayImageTypes, android.R.layout.simple_spinner_item);
    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerImageType.setAdapter(typeAdapter);
    
    String imageType = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_TYPE, null);
    if (imageType != null) {
      spinnerImageType.setSelection(getPosition(imageType, R.array.arrayImageTypes));
    }


    spinnerImageSize = (Spinner) findViewById(R.id.spinnerImageSize);
    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
        R.array.arrayImageSizes, android.R.layout.simple_spinner_item);
    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerImageSize.setAdapter(sizeAdapter);
    
    String imageSize = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SIZE, null);
    if (imageSize != null) {
      spinnerImageSize.setSelection(getPosition(imageSize, R.array.arrayImageSizes));
    }
    
    
    spinnerImageColor = (Spinner) findViewById(R.id.spinnerImageColor);
    ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
        R.array.arrayImageColors, android.R.layout.simple_spinner_item);
    colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerImageColor.setAdapter(colorAdapter);
    
    String imageColor = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_COLOR, null);
    if (imageColor != null ) {
      spinnerImageColor.setSelection(getPosition(imageColor, R.array.arrayImageColors));
    }

    
    spinnerImageSafety = (Spinner) findViewById(R.id.spinnerImageSafety);
    ArrayAdapter<CharSequence> safetyAdapter = ArrayAdapter.createFromResource(this,
        R.array.arrayImageSafety, android.R.layout.simple_spinner_item);
    safetyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerImageSafety.setAdapter(safetyAdapter);
    
    String imageSafety = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SAFETY, null);
    if (imageSafety != null) {
      spinnerImageType.setSelection(getPosition(imageSafety, R.array.arrayImageSafety));
    }
    
    etSiteFilter = (EditText) findViewById(R.id.etImageSite);
    String siteFilter = mPreferences.getString(SettingsActivity.SETTINGS_KEY_IMAGE_SITE, null);
    if (siteFilter != null && !siteFilter.isEmpty()) {
      etSiteFilter.setText(siteFilter);
    }

  }

  public void onSettingsDone( View v) {
    SharedPreferences.Editor editor = mPreferences.edit();
    editor.putString(SETTINGS_KEY_IMAGE_TYPE, (String) spinnerImageType.getSelectedItem());
    editor.putString(SETTINGS_KEY_IMAGE_SIZE, (String) spinnerImageSize.getSelectedItem());
    editor.putString(SETTINGS_KEY_IMAGE_COLOR, (String) spinnerImageColor.getSelectedItem());
    editor.putString(SETTINGS_KEY_IMAGE_SAFETY, (String) spinnerImageSafety.getSelectedItem());
    editor.putString(SETTINGS_KEY_IMAGE_SITE, etSiteFilter.getText().toString());
    editor.commit();
    finish();
  }
  
  
  private int getPosition(String imageType, int arrayResourceId) {
    String [] arrayResource = getResources().getStringArray(arrayResourceId);
    
    for (int i =0; i <arrayResource.length; i++) {
      if (arrayResource[i].equals(imageType)) {
        return i;
      }
    }
    return 0;
  }

}
