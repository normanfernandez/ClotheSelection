package org.fernandez.clotheselection;

import org.fernandez.clothetype.TopClothe;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class TopActivity extends AbstractPosition {

	
	private TopClothe [] top = new TopClothe[20];
	
	
	private void initialize(){
		for(int i = 0; i < 20; i++)
			top[i] = new TopClothe();
	}
	
	public void scrollLeft(){
		if(arrayPosition <= 0)
			return;
		this.arrayPosition--;
		updateTextView();
	}
	
	public void scrollRight(){
		if(arrayPosition >= 19)
			return;
		this.arrayPosition++;
		updateTextView();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int action = MotionEventCompat.getActionMasked(event);
		switch(action){
			case MotionEvent.ACTION_DOWN:
				this.scrollRight();
				return true;
			default:
				return super.onTouchEvent(event);
		}
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
		updateTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_top, container, false);
            return rootView;
        }
    }

	
    @Override
	protected void updateTextView() {
    	posText.setText( (arrayPosition + 1) + "/20 camisa \""+ top[arrayPosition].name + "\" " + 
				(top[arrayPosition].isSelected() ? "OK" : "NOPE!") );
	}

}
