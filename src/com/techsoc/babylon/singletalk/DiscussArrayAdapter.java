package com.techsoc.babylon.singletalk;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techsoc.babylon.R;

public class DiscussArrayAdapter extends ArrayAdapter<ChatMessage> {

	private TextView textContainer;
	private TextView nameContainer;
	private TextView timeContainer;
	private List<ChatMessage> messageContainer = new ArrayList<ChatMessage>();
	private LinearLayout messageBody;
	private Context context; 
	

	@Override
	public void add(ChatMessage object) {

		messageContainer.add(object);
		super.add(object);
	}

	public DiscussArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.context = context;
	}
	
	public void clearChat() {
		messageContainer.clear();
	}

	@Override
	public int getCount() {

		return this.messageContainer.size();
	}

	@Override
	public ChatMessage getItem(int index) {

		return this.messageContainer.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_discuss, parent, false);
		}

		messageBody = (LinearLayout) row.findViewById(R.id.message_body);
		

		ChatMessage currentMessage = getItem(position);

		textContainer = (TextView) row.findViewById(R.id.message);
			
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
		  .getDefaultDisplay().getMetrics(displaymetrics);
		
		textContainer.setMaxWidth((int) (displaymetrics.widthPixels*0.7));
		
		textContainer.setText(currentMessage.getText());
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/Calibri.ttf");
		textContainer.setTypeface(type);
		
		
		nameContainer = (TextView) row.findViewById(R.id.name);
		nameContainer.setText(currentMessage.getAuthor());
		
		timeContainer = (TextView) row.findViewById(R.id.time);
		timeContainer.setText(currentMessage.getStringTime());
		
		String currentColour = currentMessage.getBoxColour();
		
		messageBody.setBackgroundResource(context.getResources().getIdentifier(currentColour+"_box", "drawable", context.getPackageName()));
				
		
		LinearLayout wrapper = (LinearLayout) row.findViewById(R.id.wrapper);
		wrapper.setGravity(currentMessage.getPosition() ? Gravity.LEFT : Gravity.RIGHT);

		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}