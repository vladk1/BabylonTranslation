package com.techsoc.babylon.singletalk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.techsoc.Language.Language;
import com.techsoc.babylon.R;


public class UserPagerFragment extends Fragment {

	protected static final String TAG = UserPagerFragment.class.getName();

	public static final String ARG_USER = "user";
	public static final String USER_NAME = "user_name";
	public static final String USER_COLOUR = "user_colour";
	public static final String ALL_LANG = "all_lang";

		
	private int NUMBER_OF_PAGES = 2;

	/* Discussion List */
	public DiscussArrayAdapter adapter;

	private ListView mDiscussionList;

	/* Pager for Languages */
	private ViewPager mPager;
	private LanguageSliderAdapter mPagerAdapter;

	public String fragmentLang = "";
	private static HashMap<String, ArrayList<ChatMessage>> chatSource;

	private int currentPosition = 0;
	private int pageNumber;

	private TextToSpeech textToSpeech;
	
	
	public static Language lang;
	
	
	public static UserPagerFragment create(int pageNumber,
			HashMap<String, ArrayList<ChatMessage>> inputChatSource,
			String userName, String userColour, Language language) {
		
		lang = language;
		
		UserPagerFragment fragment = new UserPagerFragment();
		
		Bundle args = new Bundle();
		args.putInt(ARG_USER, pageNumber);
		args.putString(USER_NAME, userName);
		args.putString(USER_COLOUR, userColour);
		
		String[] swipeLang = lang.getSwipeCountries();
		args.putStringArray(ALL_LANG, swipeLang);
				
		fragment.setArguments(args);

		chatSource = inputChatSource;

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		setupTextToSpeech();
	}
	
	

	private void setupTextToSpeech() {
		textToSpeech = new TextToSpeech(this.getActivity(),
				new TextToSpeech.OnInitListener() {
					@Override
					public void onInit(int status) {
						if (status == TextToSpeech.SUCCESS) {
							Log.d("setupTextToSpeech", "setuped Text to Speech");
						} else {
							Log.e(TAG, "Failed to intialize Text to Speech");
						}
					}
				});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.user_pager_fragment, container, false);

		/* Initialise the ListView with its adapter */
		mDiscussionList = (ListView) rootView
				.findViewById(R.id.main_chat_listview);
		adapter = new DiscussArrayAdapter(this.getActivity()
				.getApplicationContext(), R.layout.listitem_discuss);
		mDiscussionList.setAdapter(adapter);

		mDiscussionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				String currentLanguage = getLanguage();
				ArrayList<ChatMessage> currentChatSource = chatSource
						.get(currentLanguage);
				String text = currentChatSource.get(position).getText();
				
				convertToSpeech(text, currentLanguage);
			}

		});

		/* Initialise the language View Pager with its adapter */
		mPager = (ViewPager) rootView.findViewById(R.id.lang_pager);

		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				
				String curSwipeCountry = lang.getSwipeCountries()[position]; 
				
				
				currentPosition = position;
				String currentLanguage =  lang.getLanguageCode(curSwipeCountry);
				String currentUserName = getUserName();
				int currentPageNumber = getPageNumber();
				
				
				Log.d("New Language", currentLanguage);
				

				ArrayList<ChatMessage> curChatSource = chatSource
						.get(currentLanguage);

				adapter.clearChat();

				for (int i = 0; (curChatSource!=null) && (i < curChatSource.size()); i++) {

					ChatMessage curMessage = curChatSource.get(i);

					if (curMessage.getPageNumber() == currentPageNumber) {
						
						if (!curMessage.getAuthor().equals(currentUserName)) curMessage.setAuthor(currentUserName);
						curMessage.setPosition(false);
					}
					else
						curMessage.setPosition(true);
								
					adapter.add(curMessage);
				}
			}

		});

		mPagerAdapter = new LanguageSliderAdapter(
				this.getChildFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setPageTransformer(true, new DepthPageTransformer());

		return rootView;
	}
	
	
	
	public void updateLanguageFragment(){
		
		mPagerAdapter = new LanguageSliderAdapter(
				this.getChildFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setPageTransformer(true, new DepthPageTransformer());
		mPagerAdapter.notifyDataSetChanged();
	}
	

	
	
	
	
	public boolean checkTTSSupportLanguage(Locale inputTTS) { 
		
		return textToSpeech.isLanguageAvailable(inputTTS) == TextToSpeech.LANG_MISSING_DATA
				|| textToSpeech.isLanguageAvailable(inputTTS) == TextToSpeech.LANG_NOT_SUPPORTED ? false
				: true; 
	} 

	private void convertToSpeech(String text, String currentLanguage) {
		// download unknown language
		
		Log.d("Input Language", currentLanguage);
		
		Locale lanLocale = new Locale(currentLanguage);
        
		boolean checkThisOut;
		checkThisOut = checkTTSSupportLanguage(lanLocale);
		Log.v("convertToSpeech", "checkTTSSupportLanguage: " + checkThisOut);
		
		
		if ( checkTTSSupportLanguage(lanLocale) ) {
			
			textToSpeech.setLanguage(lanLocale);
			Log.v("Translate", "Language Available: " + currentLanguage);
			textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
			Log.v("convertToSpeech", "Speak");

		} else {
			Log.e("Translate", "Language Not Available: "
					+ currentLanguage);
			Intent installIntent = new Intent();
			installIntent
					.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
			this.getActivity().startActivity(installIntent);
		}
	}
	

	public void addChatMessage(ChatMessage cm) {
		adapter.add(cm);
	}

	public String getLanguage() {
		
		String curSwipeCountry = lang.getSwipeCountries()[currentPosition];
	   

		return lang.getLanguageCode(curSwipeCountry);
	}
	
	public String getUserColour() {
		
		return getArguments().getString(USER_COLOUR);
	}
	public void setUserColour(String userColour) {

		 getArguments().putString(USER_COLOUR,userColour);
	}
	
	public int getPageNumber() {
		
		return getArguments().getInt(ARG_USER);
	}
    public void setPageNumber() {
		
		getArguments().putInt(ARG_USER,pageNumber);
	}
	

	public String getUserName() {

		return getArguments().getString(USER_NAME);
	}
	public void setUserName(String userName) {

		 getArguments().putString(USER_NAME,userName);
	}
	
	public void updateFragment(HashMap<String, ArrayList<ChatMessage>> newChatSource) {
		
		UserPagerFragment.chatSource = newChatSource;
	}
	
	
	private class LanguageSliderAdapter extends FragmentStatePagerAdapter {

		private List<LanguageSliderFragment> languages = new ArrayList<LanguageSliderFragment>();

		public LanguageSliderAdapter(FragmentManager fm) {
			super(fm);
			String[] swipeLnag = lang.getSwipeCountries();

			int i = 0;
			for (int ii=0; ii<swipeLnag.length; ii++) {
				if (swipeLnag[ii]!=null)
				i++;
			}
			NUMBER_OF_PAGES = i;
		}
		
		
		
		
		@Override
		public Fragment getItem(int position) {

			LanguageSliderFragment newFrag = LanguageSliderFragment
					.create(position, lang.getSwipeCountries()); 
			languages.add(newFrag);

			return newFrag;
		}
		
		
		@Override
		public int getCount() {
			return NUMBER_OF_PAGES;
		}
	}
}