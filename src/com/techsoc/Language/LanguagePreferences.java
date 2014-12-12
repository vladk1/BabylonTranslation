package com.techsoc.Language;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguagePreferences {

 Context context;
 SharedPreferences langaugePreferences;
 
 private static final String HOME_LANGUAGE_ID = "home_language";
 private static String defaultLanguage = "en";
 
 private static final String HOME_COUNTRY_ID = "home_country";
 private static String defaultCountry = "en";


 public LanguagePreferences(Context context){
  
  this.context = context;
  langaugePreferences = context.getSharedPreferences("USER_LANGUAGE_PREFERENCES", 0);

 }
 
 public String getHomeLanguage(){
  
  String homeLanguage = "";
  homeLanguage = langaugePreferences.getString(HOME_LANGUAGE_ID,defaultLanguage);
  return homeLanguage;
 }
 
 public void setHomeLanguage(String homeLanguage){
  
  SharedPreferences.Editor PrefEditor = langaugePreferences.edit();
  PrefEditor.putString(HOME_LANGUAGE_ID, homeLanguage);
  PrefEditor.commit();
 }
 
 
 
 
 public String getHomeCountry(){
	  
	  String homeCountry = "";
	  homeCountry = langaugePreferences.getString(HOME_COUNTRY_ID,defaultCountry);
	  return homeCountry;
	 }
	 
public void setHomeCountry(String homeCountry){
	  
	  SharedPreferences.Editor PrefEditor = langaugePreferences.edit();
	  PrefEditor.putString(HOME_COUNTRY_ID, homeCountry);
	  PrefEditor.commit();
	 }
  
}
