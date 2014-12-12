package com.techsoc.babylon.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;

import com.techsoc.Language.Language;
import com.techsoc.babylon.MainActivity;
import com.techsoc.babylon.R;

public class AddUser {
	
	static EditText input;
	static Spinner sprCoun;
	static LinearLayout.LayoutParams lp;
	static String userLang;
	
	
	static ArrayAdapter<String> countryListAdapter;
	static ArrayList<String> curCountries;

	static ArrayList<String> curSwipeCountries;
	static ArrayAdapter<String> swipeListAdapter;
	
	int MAX_LANG = 5;
	String ERROR_MESSAGE = "You can't have more than 5 countries in dialog :(";

	
	public static void startDialog(final MainActivity context, Language lang) {
		
		final AlertDialog.Builder newUserDialog = new AlertDialog.Builder(context);

		newUserDialog.setTitle("Add People");
		newUserDialog.setMessage("What's your Name?");

		final LinearLayout listView = new LinearLayout(context);
		listView.setOrientation(LinearLayout.VERTICAL);

		setUpNameEditText(context, listView);
		
		setUpColourSpinner(context, listView);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		final LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.menu_country_picker, null, false);
		
		
		listView.addView(setUpCountryPickerLayout(context, lang, layout));
		
		

		newUserDialog.setView(listView);
		
		final AlertDialog alertDialog = newUserDialog.create();
		
		Button doneButton =(Button) layout.findViewById(R.id.doneButton);
		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userName = input.getText().toString();
				String userColour = sprCoun.getSelectedItem()
						.toString();
				String userLangName = userLang;

				context.addNewUser(userName, userColour);
				
				alertDialog.dismiss();
			}	
		}); 

		alertDialog.show();
	}
	
	private static LinearLayout setUpCountryPickerLayout(final MainActivity context, Language lang, final LinearLayout layout) {
		
		CountryPicker countryPicker = new CountryPicker(context);
			
		
		
		final ListView countryList = countryPicker.setUpCountryList(context, layout, lang);
		
		curCountries = countryPicker.curCountries;
		countryListAdapter = countryPicker.countryListAdapter;
		
		 
		
		countryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// check if you can add more countries to swipable
				
				AutoCompleteTextView countryInputText = (AutoCompleteTextView) layout
						.findViewById(R.id.inputText);
				
			//	if (curSwipeCountries.size() < MAX_LANG) {

					String pickedCountry = (String) countryList
							.getItemAtPosition(position);
					// String pickedLanguage
					userLang = pickedCountry;

					curCountries.clear();
					curCountries.add(pickedCountry);
					countryInputText.setText(pickedCountry);

					
					countryListAdapter.notifyDataSetChanged();
					
			}
		});

		countryPicker.setUpAutoComplete(context, layout, curCountries);
		
		
		
		
		return layout;
	}
	
	private static void setUpNameEditText(MainActivity context, LinearLayout listView) {
		input = new EditText(context);
		lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		input.setLayoutParams(lp);
		listView.addView(input);
	}
	
	private static void setUpColourSpinner(MainActivity context, LinearLayout listView) {
		
		sprCoun = new Spinner(context);
		sprCoun.setLayoutParams(lp);
		ArrayList<String> colourList = new ArrayList<String>(Arrays.asList("blue", "red", "yellow", "green", "gray"));
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, colourList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sprCoun.setAdapter(dataAdapter);
		listView.addView(sprCoun);
	}

}
