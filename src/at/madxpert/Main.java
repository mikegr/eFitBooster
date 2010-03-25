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
        
        Button b = (Button) findViewById(R.id.exercisesButton);
        b.setOnClickListener(this);        
        b = (Button) findViewById(R.id.workoutButton);
        b.setOnClickListener(this);
        
    }
    
    public void onClick(View v) {
    	if (v.getId() == R.id.exercisesButton) {
    		Intent intent = new Intent(this, at.madxpert.Template.class);
        	startActivity(intent);	
    	}
    	if (v.getId() == R.id.workoutButton) {
    		Intent intent = new Intent(this, at.madxpert.Workout.class);
        	startActivity(intent);
    	}
    }
}