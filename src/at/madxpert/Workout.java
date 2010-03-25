package at.madxpert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Workout extends ListActivity implements OnClickListener, OnDateSetListener {
	
	private static String TAG = Workout.class.getName();
	
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db = new DatabaseHelper(this).getWritableDatabase();
		setContentView(R.layout.workout);
		
		ListView view = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(view);
		
		updateList();
		db.rawQuery("INSERT INTO activity (name) VALUES (?)", new String[] {"Exercise 1"});
	}
	
	public void updateList() {
		//
		Cursor c = db.rawQuery("SELECT _id, done FROM workout", null);
		setListAdapter(new OneColumnCursorAdapter(this, c, "done"));
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
	}
	
	@Override
	/**
	 * Creates the context menu
	 */
	public void onCreateContextMenu(ContextMenu menu, View v,
		ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(ContextMenu.NONE, 0, 0, "Delete");
	}
	
	/**
	 * Reacts on context menu clicks
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.v(TAG, "ContextMenu called");
		if (item.getItemId() == 0) {
			AdapterView.AdapterContextMenuInfo cmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			Log.v(TAG, "Selected item: " + cmi.id);
			db.delete("workout", "_id = ?", new String[]{Long.toString(cmi.id)});
			updateList();
		}
		return super.onContextItemSelected(item);
	}
	
	/**
	 * Reacts on add button
	 */
	public void onClick(android.view.View arg0) {
		
		Calendar cal = GregorianCalendar.getInstance();
		DatePickerDialog dlg = new DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		dlg.show();
		/*Intent intent = new Intent(this, AddWorkout.class);
		startActivityForResult(intent, 0);
		*/		
	};
	
	/**
	 * Reacts on DatePicker 
	 */
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar cal = GregorianCalendar.getInstance(); 
		cal.clear();
		cal.set(year, monthOfYear, dayOfMonth);
		Date date = cal.getTime();
		Log.v(TAG, "Adding new workout for:" + date);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		ContentValues values = new ContentValues();
		values.put("done", df.format(date));
		db.insert("workout", null, values);
		updateList();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, WorkoutContent.class);
		i.putExtra("workout", (int) id);
		startActivity(i);
	}
	
	
}
