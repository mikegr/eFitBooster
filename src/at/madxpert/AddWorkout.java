package at.madxpert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.DatePicker.OnDateChangedListener;

public class AddWorkout extends Activity implements OnDateChangedListener, OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addworkout);
		
		DatePicker dp = (DatePicker) findViewById(R.id.edit);
		Calendar cal = GregorianCalendar.getInstance();
		
		dp.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
		
	}
	
	private Date date;
	
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar cal = GregorianCalendar.getInstance(); 
		cal.clear();
		cal.set(year, monthOfYear, dayOfMonth);
		date = cal.getTime();
		
	}
	
	public void onClick(View v) {
		Log.v(this.getLocalClassName(), "onClickView() called");
		
		Intent back = new Intent(this, Template.class);
		back.putExtra("date", date.getTime());
		
		setResult(RESULT_OK, back);
		finish();		
	}
}
