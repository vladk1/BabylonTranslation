package com.techsoc.babylon.singletalk;

import java.util.Calendar;

public class ChatMessage {

	private String author;
	private String message;
	private String messageLanguage;
	private Calendar messageTime;
	private String boxColour;
	private boolean leftPosition;
	private int pageNumber;

	public ChatMessage(boolean left, String message, String messageLanguage,
			String author, Calendar messageTime, String boxColour, int pageNumber) {

		this.leftPosition = left;
		this.message = message;
		this.messageLanguage = messageLanguage;
		this.author = author;
		this.messageTime = messageTime;
		this.boxColour = boxColour;
		this.pageNumber = pageNumber;
	}

	public void setNewAttr(boolean left, String message,
			String messageLanguage, String author, Calendar messageTime, String boxColour, int pageNumber) {

		this.leftPosition = left;
		this.message = message;
		this.messageLanguage = messageLanguage;
		this.author = author;
		this.messageTime = messageTime;
		this.boxColour = boxColour;
		this.pageNumber = pageNumber;
	}
	public void setPosition(boolean left){
		this.leftPosition = left;
	}

	public String getText() {
		return this.message;
	}

	public String getLanguage() {
		return this.messageLanguage;
	}

	public boolean getPosition() {
		return this.leftPosition;
	}

	public String getAuthor() {
		return this.author;
	}
	public void setAuthor(String newAuthor) {
		this.author = newAuthor;
	}

	public Calendar getCalendarTime() {
		return this.messageTime;
	}
	
	public String getBoxColour() {
		return this.boxColour;
	}
	
	public int getPageNumber(){
		return this.pageNumber;
	}
	
	public String getStringTime() {

		Integer seconds = messageTime.get(Calendar.SECOND);
		Integer minutes = messageTime.get(Calendar.MINUTE);
		Integer hours = messageTime.get(Calendar.HOUR_OF_DAY);
		
		String stringSeconds = seconds.toString();
		if (seconds < 10) {	
		   stringSeconds = "0" + stringSeconds;
		}
		
		String stringMinutes = minutes.toString();
		if (minutes < 10) {	
			stringMinutes = "0" + stringMinutes;
		}
		
		String stringHours = hours.toString();
		if (hours < 10) {	
			stringHours = "0" + stringHours;
		}
		return stringHours + ":" + stringMinutes + ":" + stringSeconds;
	}

	public int getIntTime() {

		String formatStringTime = getStringTime();
		String stringTime = "";
		
		for (int i = 0; i < formatStringTime.length(); i++) {
			if (formatStringTime.charAt(i)!=':') {
				stringTime += formatStringTime.charAt(i);
			}
		}
		
		return Integer.parseInt(stringTime);
	}

}