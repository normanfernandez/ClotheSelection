package org.fernandez.clotheselection;

import java.util.UUID;

import org.fernandez.clothetype.TopClothe;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TopActivity extends AbstractPosition implements SwipeInterface{
		
	protected void initialize(){
		for(int i = 0; i < 20; i++)
			this.clothe[i] = new TopClothe();
	}

	protected void updateTextView() {
    	posText.setText( (arrayPosition + 1) + "/20 camisa \""+ clothe[arrayPosition].name + "\" " + 
				(clothe[arrayPosition].isSelected() ? "OK" : "NOPE!") );
	}
	
	public void selectClothe(View view){
		super.selectClothe(view);
		if(!clothe[arrayPosition].isSelected()){
			Toast.makeText(this, "Clothe at position " + arrayPosition + " sent signal: " + (char)(arrayPosition + 65), Toast.LENGTH_SHORT).show();
			this.clothe[arrayPosition].setSelected(true);
		}
		else
			Toast.makeText(this, "Couldn't send, clothe already selected!", Toast.LENGTH_SHORT).show();
		updateTextView();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_top);
		this.btAdapter = BluetoothAdapter.getDefaultAdapter();
		btSearch = (Button)this.findViewById(R.id.btSearch);
		posText = (TextView)this.findViewById(R.id.topPosText);
		btSearch.setEnabled(this.btAdapter.isEnabled());
		initialize();
		this.arrayPosition = 0;
		
		ActivitySwipeDetector swipe = new ActivitySwipeDetector(this, TopActivity.this);
		FrameLayout swipe_layout = (FrameLayout) findViewById(R.id.container);
		swipe_layout.setOnTouchListener(swipe);
		
		updateTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onLeftToRight(View v) {
		this.scrollLeft();
	}

	@Override
	public void onRightToLeft(View v) {
		this.scrollRight();
	}

    
    
}
