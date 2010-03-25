package at.madxpert;

import java.net.URI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addactivity);
		Button ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener(this);
		
	}
	
	public void onClick(View v) {
		Log.v(this.getLocalClassName(), "onClickView() called");
		EditText et = (EditText) findViewById(R.id.edit);
		String result = et.getText().toString();
		
		Intent back = new Intent(this, Template.class);
		back.putExtra("name", result);
		
		setResult(RESULT_OK, back);
		finish();
	}
}
