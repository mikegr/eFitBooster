package at.madxpert;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdjustView extends Activity implements OnClickListener {

	private static final String TAG = AdjustView.class.getName();
	
	private SQLiteDatabase db;
	private int activity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		DatabaseHelper dh =  new DatabaseHelper(this);
		db = dh.getReadableDatabase();
		
		activity = getIntent().getIntExtra("activity", -1);
		int repeats = getIntent().getIntExtra("repeats", 0);
		int weight = getIntent().getIntExtra("weight", 0);
		Log.d(TAG, "Activity = " + activity);
		
		setContentView(R.layout.adjust);
		((Button)findViewById(R.id.okButton)).setOnClickListener(this);
		Cursor cursor = db.rawQuery("SELECT name FROM activity where _id = ?", new String[] {Integer.toString(activity)});
		
		DatabaseHelper.debug(db, "activity");
		
		Log.d(TAG, "Count: " + cursor.getCount());
		Log.d(TAG, "Columns: " + cursor.getColumnCount());
		cursor.moveToFirst();
		String name = cursor.getString(0);
		
		((TextView)findViewById(R.id.name)).setText(name);
		((EditText)findViewById(R.id.editRepeats)).setText(Integer.toString(repeats));
		((EditText)findViewById(R.id.editWeight)).setText(Integer.toString(weight));
						
	}
	
	
	public void onClick(View v) {
		String repeats = ((EditText)findViewById(R.id.editRepeats)).getText().toString();
		String weight = ((EditText)findViewById(R.id.editWeight)).getText().toString();
		
		Intent i = new Intent();
		i.putExtra("repeats", Integer.parseInt(repeats));
		i.putExtra("weight", Integer.parseInt(weight));
		i.putExtra("activity", activity);
		setResult(RESULT_OK, i);
		finish();
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}

}
