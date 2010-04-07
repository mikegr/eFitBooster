package at.madxpert;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class WorkoutContent extends ListActivity implements OnClickListener {

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
		Log.v(TAG, "Workout:" + workout);
		
		ListView view = (ListView) findViewById(android.R.id.list);
		
		
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
		if (requestCode == 0 && resultCode == RESULT_OK) {
			Log.v(TAG, "Data is null? " + Boolean.toString(data == null));
			int activity = data.getIntExtra("activity", -1);
			Intent i = new Intent(this, AdjustView.class);
			i.putExtra("activity", activity);
			startActivityForResult(i, 1);
		}
		
		if (requestCode == 1 && resultCode == RESULT_OK) {
			Log.v(TAG, "requestCode 1");
			int activity = data.getIntExtra("activity", -1);
			int repeats = data.getIntExtra("repeats", -1);
			int weight = data.getIntExtra("weight", -1);
			db.execSQL("INSERT INTO sets (workout, activity, repeats, what) VALUES (?,?,?,?)", 
					new Object[]{workout, activity, repeats, weight });
		}
		if (requestCode == 2 && resultCode == RESULT_OK) {
			Log.v(TAG, "requestCode 2, update");
			int repeats = data.getIntExtra("repeats", -1);
			int weight = data.getIntExtra("weight", -1);
			db.execSQL("UPDATE sets SET repeats = ?, what = ? WHERE set = ?", 				
					new Object[]{repeats, weight, currentSet});
		}
		updateList();
	}
	
	private long currentSet;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, AdjustView.class);
		Log.v(TAG, "Adjust set: " + id);
		currentSet = id;
		DatabaseHelper.debug(db, "sets");
		Cursor c = db.rawQuery("SELECT activity, repeats, what FROM sets AS s WHERE s._id = ?", new String[] {Long.toString(id)});
		c.moveToFirst();
		i.putExtra("activity", c.getInt(0));
		i.putExtra("repeats", c.getInt(1));
		i.putExtra("weight", c.getInt(2));
		startActivityForResult(i, 2);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
	}
	
}
