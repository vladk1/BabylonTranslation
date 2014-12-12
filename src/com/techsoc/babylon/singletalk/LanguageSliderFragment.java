package com.techsoc.babylon.singletalk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.techsoc.babylon.R;

public class LanguageSliderFragment extends Fragment {
	
	public static final String ARG_LANG = "page";
	public static final String ALL_LANG = "all_lang";
	public String currentLanguage = "en";
	
	private static String PACKAGE_NAME;
	
	private int mPageNumber;
	
	private String[] swipeLang;
	TextView langView;

	public static LanguageSliderFragment create(int pageNumber, String[] swipeLang) {
		
		LanguageSliderFragment fragment = new LanguageSliderFragment();
		// creating Bundle to pass parameters to the fragment
		Bundle args = new Bundle();
		args.putInt(ARG_LANG, pageNumber);
		args.putStringArray(ALL_LANG, swipeLang);
		
		fragment.setArguments(args);
		return fragment;
	}
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mPageNumber = getArguments().getInt(ARG_LANG);
        swipeLang = getArguments().getStringArray(ALL_LANG);
        
        PACKAGE_NAME = this.getActivity().getApplicationContext().getPackageName();
        
       
    }
    
    
   /* public String returnLang(){
    	
    	Resources res = getResources();
    	String[] langImageSrc = res.getStringArray(R.array.lang_code);
    	
    	return langImageSrc[mPageNumber];
    }*/
    
    
    
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		 ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.language_slider_fragment, container, false);
        
        
        rootView.setLayoutParams(new LayoutParams (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        
        Resources res = getResources();
        
        int imageDrawableId = res.getIdentifier("header_" + "en", "drawable", PACKAGE_NAME);
           
        ImageView langImage = (ImageView) rootView.findViewById(R.id.language_image);
        
        langImage.setImageBitmap(resizeImage(imageDrawableId)); 
        
       //g
        //TODO: Refactor into Adapter for international code
        langView = (TextView) rootView.findViewById(R.id.country_textView);
        langView.setText(swipeLang[mPageNumber]);
        	
        return rootView;
    }
	
	public void setTitle(String newTitle) {
	   //TODO: Refactor into Adapter for international code
        
        langView.setText(newTitle);
	}
	
	public void setTitleFromArray(String[] namesArray) {
		
		langView.setText(namesArray[mPageNumber]);
	}
	
	
	private Bitmap resizeImage(int resourceId) {

		Bitmap scaledBitmap = BitmapFactory.decodeResource(getResources(),
				resourceId);

		int imageWidth = scaledBitmap.getWidth();
		int imageHeight = scaledBitmap.getHeight();

		DisplayMetrics metrics = this.getResources().getDisplayMetrics();

		int newWidth = metrics.widthPixels;
		float scaleFactor = (float) 0.75 *newWidth / imageWidth;  
		int newHeight = (int) (imageHeight * scaleFactor);
		
		
		scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, newWidth,
				newHeight, true);
		return scaledBitmap;
	}
	
}