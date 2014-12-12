package com.techsoc.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Translator {
	
	/*
	 * This is a helper class to translate text using Google Translate Web.
	 * Later if the translate api or method of translation changes, then only this class needs to 
	 * be changed and not the main activity
	 */
	
	String inputLanguage;
	String outputLanguage;
	
	public Translator(String inputLanguage, String outputLanguage){
		
		this.outputLanguage = outputLanguage;
		this.inputLanguage = inputLanguage;
	
	}
	
	
	public void setInputLanguage(String inputLanguage){
		
		this.inputLanguage = inputLanguage;
	}
	
	public void setOutputLanguage(String outputLanguage){
		
		this.outputLanguage = outputLanguage;
	}
	
	public void getInputLanguage(String inputLanguage){
		
		this.inputLanguage = inputLanguage;
	}
	
	public void getOutputLanguage(String outputLanguage){
		
		this.outputLanguage = outputLanguage;
	}
	
	public String Translate(String untranslatedText) throws IOException{
	
		/*
		 * Translation using the Google Web Translation Service
		 */
		
		String translatedText = "";
		
		URL url = new URL("http://translate.google.com.tw/translate_a/t?client=t&hl=en&sl=" +
				inputLanguage + "&tl=" + outputLanguage + "&ie=UTF-8&oe=UTF-8&multires=1&oc=1&otf=2&ssel=0&tsel=0&sc=1&q=" + 
                URLEncoder.encode(untranslatedText, "UTF-8"));
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Something Else");
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String result = br.readLine();
        br.close();
        
        System.out.println(result);
        result = result.substring(2, result.indexOf("]]") + 1);
        StringBuilder sb = new StringBuilder();
        String[] splits = result.split("(?<!\\\\)\"");
        for(int i = 1; i < splits.length; i += 8)
            sb.append(splits[i]);
        translatedText = sb.toString().replace("\\n", "\n").replaceAll("\\\\(.)", "$1");
		
		return translatedText;
				
	}	

	
}
