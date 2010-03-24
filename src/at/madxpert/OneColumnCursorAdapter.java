package at.madxpert;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class OneColumnCursorAdapter extends SimpleCursorAdapter {


	public OneColumnCursorAdapter(Context context, Cursor c, String column) {
		super(context, android.R.layout.simple_list_item_1, c, new String[] {column}, new int[]{android.R.id.text1});
	}
	
}
