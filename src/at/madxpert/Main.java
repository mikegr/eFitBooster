package at.madxpert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(this);        
    }
    
    
    public void onClick(View v) {
    	Intent intent = new Intent(this, at.madxpert.Template.class);
    	startActivity(intent);
    }
}