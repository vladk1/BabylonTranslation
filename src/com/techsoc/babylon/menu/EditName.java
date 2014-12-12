package com.techsoc.babylon.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.techsoc.babylon.MainActivity;

public class EditName {

	public static void startDialog(final MainActivity context,
			final int currentPagePosition) {

		AlertDialog.Builder usernameDialog = new AlertDialog.Builder(context);

		usernameDialog.setTitle("Edit Name");
		usernameDialog.setMessage("New name:");

		final EditText editName_et = new EditText(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		editName_et.setLayoutParams(params);
		usernameDialog.setView(editName_et);

		usernameDialog.setPositiveButton("Change",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						String newUserName = editName_et.getText().toString();

						context.setNewTitleName(newUserName);

					}
				});

		usernameDialog.show();

	}

}
