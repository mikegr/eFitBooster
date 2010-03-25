package at.madxpert;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

public class WorkoutContent extends ListActivity implements OnClickListener{

	private static final String TAG = WorkoutContent.class.getName();
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workoutcontent);
		((Button)findViewById(R.id.addButton)).setOnClickListener(this);
		
		db = new DatabaseHelper(this).getWritableDatabase();
		workout = getIntent().getIntExtra("workout", -1);
		updateList();
	}
	
	private int workout;
	
	public void updateList() {
		Log.v(TAG, "available sets: " + db.rawQuery("SELECT * from sets", null).getCount());
		Cursor c = db.rawQuery("SELECT * FROM sets AS s, activity AS a WHERE s.workout = ? AND s.activity = a._id", new String[] {Integer.toString(workout)});
		Log.v(TAG, "selected sets: " + c.getCount());
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, c, new String[] {"name"}, new int[] {android.R.id.text1});
		setListAdapter(sca);
	}
	
	
	public void onClick(View v) {
		Intent i = new Intent(this, Template.class);
		i.putExtra("select", true);
		startActivityForResult(i, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.v(TAG, "onActivityResult");
		if (resultCode == RESULT_OK) {
			Log.v(TAG, "Data is null? " + Boolean.toString(data == null));
			int activity = data.getIntExtra("activity", -1);
			//TODO: Show last repeats/what
			
			db.execSQL("INSERT INTO sets (workout, activity, repeats, what) VALUES (?,?,?,?)", 
					new Object[]{workout, activity, 0, 0 });
			updateList();
		}
	}
	
}
