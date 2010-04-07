package at.madxpert;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getName();
	public DatabaseHelper(Context ctx) {
		super(ctx, "efitbooster", null, 3);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v(TAG, "Creating database");
		db.execSQL("CREATE TABLE activity (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
		db.execSQL("CREATE TABLE workout (_id INTEGER PRIMARY KEY AUTOINCREMENT, done DATE)");
		db.execSQL("CREATE TABLE sets (_id INTEGER PRIMARY KEY AUTOINCREMENT, workout INTEGER, activity INTEGER, repeats INTEGER, what INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v(TAG, "Upgrade from" + oldVersion + "to" + newVersion);
		db.execSQL("DROP TABLE sets");
		db.execSQL("DROP TABLE workout");
		db.execSQL("DROP TABLE activity");
		onCreate(db);
	}
	@Override
	public void onOpen(SQLiteDatabase db) {
		Log.v(TAG, "Open database");
		super.onOpen(db);
	}

	public static void debug(SQLiteDatabase db, String table) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + table, new String[0]);
		for(int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			Log.v(TAG, "Row " + i);
			for(int c = 0; c < cursor.getColumnCount(); c++) {
				Log.v(TAG, cursor.getColumnName(c)  + ":" + cursor.getString(c));
			}
		}
	}

}
