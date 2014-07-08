package org.fernandez.clotheselection;

import org.fernandez.clothetype.BottomClothe;
import org.fernandez.clothetype.TopClothe;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
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
import android.widget.TextView;
import android.os.Build;

public class BottomActivity extends AbstractPosition {

	public BottomClothe [] bottom = new BottomClothe[20];
	
	private void initialize(){
		for(int i = 0; i < 20; i++)
			bottom[i] = new BottomClothe();
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
	protected void updateTextView() {
		posText.setText( (arrayPosition + 1) + "/20 camisa \"" + bottom[arrayPosition].name + "\" " + 
				(bottom[arrayPosition].isSelected() ? "OK" : "NOPE!") );
		
	}

}
