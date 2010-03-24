package at.madxpert;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context ctx) {
		super(ctx, "efitbooster", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE activity (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
		db.execSQL("CREATE TABLE workout (_id INTEGER PRIMARY KEY AUTOINCREMENT, done DATE)");
		db.execSQL("CREATE TABLE sets (_id INTEGER PRIMARY KEY AUTOINCREMENT, workout INTEGER, activity INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
