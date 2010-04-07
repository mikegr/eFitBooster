package at.madxpert;
import android.R;
import android.app.ListActivity;
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
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Template extends ListActivity implements OnClickListener, OnCreateContextMenuListener {

	    String[] items = {"Sit-Ups", "Upper Back", "Lower Back", "Lat Pulldown"};
	  	private String TAG = Template.class.getName();
	    
		private SQLiteDatabase db;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			Log.v(TAG, "onCreate");
			super.onCreate(savedInstanceState);
			setContentView(at.madxpert.R.layout.template);
			
			
			db = new DatabaseHelper(this).getWritableDatabase();
			
			//setListAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_item_1, items));
			
			Button button = (Button) findViewById(at.madxpert.R.id.button);
			button.setOnClickListener(this);
			
			updateList();
			
			ListView list = (ListView) findViewById(R.id.list);
			registerForContextMenu(list); // or list.setOnCreateContextMenuListener(this);
		}
		
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
			super.onCreateContextMenu(menu, v, menuInfo);
			menu.add(ContextMenu.NONE, 0, 0, "Delete");
		}
		
		@Override
		public boolean onContextItemSelected(MenuItem item) {
			Log.v(TAG, "ContextMenu called");
			if (item.getItemId() == 0) {
				AdapterView.AdapterContextMenuInfo cmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
				Log.v(TAG, "Selected item: " + cmi.id);
				db.delete("activity", "_ID = ?", new String[]{Long.toString(cmi.id)});
				updateList();
			}
			return super.onContextItemSelected(item);
		}
		
		public void onClick(View v) {
			Intent intent = new Intent(this, AddActivity.class);
			startActivityForResult(intent, 0);
		}
		
		public void updateList() {
			
			Cursor cursor = db.rawQuery("SELECT _id, name FROM activity", null);
			Log.v(TAG, "Rows:" + cursor.getCount());
			
			setListAdapter(new SimpleCursorAdapter(this, R.layout.simple_list_item_1, cursor, 
					new String[] {"name"},
			 		new int[] {R.id.text1}));
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == 0 && resultCode == RESULT_OK) {
				String name = data.getStringExtra("name");
				Log.v(TAG, "Adding new name:" + name);
				ContentValues values = new ContentValues();
				values.put("name", name);
				db.insert("activity", null, values);
				updateList();
			}
		}
		
		@Override
		/**
		 * Called by clicking on list item
		 */
		protected void onListItemClick(ListView l, View v, int position, long id) {
			// 	TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			Log.v(TAG, "onListItemClick");
			if (getIntent().getBooleanExtra("select", false)) {
				Intent i = new Intent();
				i.putExtra("activity", (int)id);
				setResult(RESULT_OK, i);
				finish();
			}
			
		}
		
		@Override
		protected void onDestroy() {
		// TODO Auto-generated method stub
			super.onDestroy();
			db.close();
		}
		
}
