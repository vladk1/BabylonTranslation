package com.techsoc.Language;

import java.util.ArrayList;
import java.util.Locale;

import android.util.Log;

public class Language {

	private String[] swipeCountries = new String[5];

	private ArrayList<String> countries;
	private ArrayList<String> languages;
	private ArrayList<String> codes;
	private ArrayList<String> names;

	public Language() {

		// swipeCountries[0] = "United Kingdom";
		// swipeCountries[1] = "Russia";

		swipeCountries[0] = "United Kingdom (English)";
		swipeCountries[1] = "Russia (Russian)";

		getLangCodes();
	}

	public void setSwipeLang(String[] swipeLang) {

		this.swipeCountries = swipeLang;
	}

	public String[] getSwipeCountries() {

		return swipeCountries;
	}

	public String getLanguageCode(String countryName) {

		Log.v("getLanguageCode ", "for " + countryName);

		if (names.contains(countryName)) {

			String newCode = codes.get(names.indexOf(countryName));

			Log.v("getLanguageCode ", "code for " + countryName + " is "
					+ newCode);
			return newCode;
		} else
			Log.e("getLanguageCode", "no country " + countryName);

		return "";
	}

	public String getLangCodeAfterWrong(String countryName, String wrongCode) {

		int wrongIndex = codes.indexOf(wrongCode);
		Log.e("getLangCodeAfterWrong ", "wrong code for " + countryName
				+ " is " + wrongCode);
		codes.remove(wrongIndex);
		countries.remove(wrongIndex);
		languages.remove(wrongIndex);
		names.remove(wrongIndex);
		return getLanguageCode(countryName);
	}

	public String getCountry(String countryName) {

		if (names.contains(countryName)) {
			int index = names.indexOf(countryName);
			return countries.get(index);
		} else {
			Log.e("getCountry", "no country name " + countryName);
			return "";
		}
	}

	public String getLanguage(String countryName) {

		if (names.contains(countryName)) {

			int index = names.indexOf(countryName);
			return languages.get(index);
		} else {

			Log.e("getLanguage", "no country name " + countryName);
			return "";
		}
	}

	public boolean doWeHaveThisCountryName(String inputCountryName) {
		ArrayList<String> arrayCountryNames = this.names;
		for (int counter = 0; counter < arrayCountryNames.size(); counter++) {
			if (inputCountryName.equals(arrayCountryNames.get(counter)))
				return true;
		}
		return false;
	}

	public ArrayList<String> getCountryNames() {

		return this.names;
	}

	private void getLangCodes() {

		Locale[] locales = Locale.getAvailableLocales();
		countries = new ArrayList<String>();
		codes = new ArrayList<String>();
		languages = new ArrayList<String>();
		names = new ArrayList<String>();

		for (Locale locale : locales) {

			String country = locale.getDisplayCountry();
			String code = locale.getLanguage();
			String language = locale.getDisplayName();

			Log.e("Language", "get language codes");
			if ((country.trim().length() > 0) && (language.trim().length() > 0)) {

				String bracketedLanguage = bracket(deleteCountryFromLang(language));
				String name = country.concat(bracketedLanguage);

				if (!names.contains(name)) {

					names.add(name);
					countries.add(country);
					languages.add(language);
					codes.add(code);
				}
			}
		}

	}

	private String deleteCountryFromLang(String language) {
		int indexStartDelete = language.indexOf("(");
		return language.substring(0, indexStartDelete - 1);
	}

	private String bracket(String inputString) {

		return " (" + inputString + ')';
	}
}
