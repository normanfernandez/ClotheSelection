package org.fernandez.clotheselection;

import org.fernandez.clothetype.BottomClothe;
import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BottomActivity extends AbstractPosition implements SwipeInterface{
	
	protected void initialize(){
		for(int i = 0; i < 20; i++)
			this.clothe[i] = new BottomClothe();
	}
	
	protected void updateTextView() {
		posText.setText( (arrayPosition + 1) + "/20 pantalon \"" + this.clothe[arrayPosition].name + "\" " + 
				(this.clothe[arrayPosition].isSelected() ? "OK" : "NOPE!") );
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bottom);
		this.btAdapter = BluetoothAdapter.getDefaultAdapter();
		initialize();
		btSearch = (Button)this.findViewById(R.id.btSearch);
		posText = (TextView)this.findViewById(R.id.botPosText);
		btSearch.setEnabled(this.btAdapter.isEnabled());
		this.arrayPosition = 0;
		
		ActivitySwipeDetector swipe = new ActivitySwipeDetector(this, BottomActivity.this);
		FrameLayout swipe_layout = (FrameLayout) findViewById(R.id.container);
		swipe_layout.setOnTouchListener(swipe);
		
		updateTextView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bottom, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_bottom,
					container, false);
			return rootView;
		}
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
