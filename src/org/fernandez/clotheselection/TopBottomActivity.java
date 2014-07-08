package org.fernandez.clotheselection;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.StatusUpdates;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class TopBottomActivity extends ActionBarActivity {

	static BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
	
	/*
	public TopClothe[] getTop() {
		return top;
	}

	public BottomClothe[] getBottom() {
		return bottom;
	}*/

	public void bluetoothInfo(View view){	
		if(btAdapter.isEnabled()){
			String name = btAdapter.getName();
			String address =  btAdapter.getAddress();
			String status = name + " : " + address;
			Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "Bluetooth is off", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void top(View view){
		Intent in = new Intent(TopBottomActivity.this, TopActivity.class);
		TopBottomActivity.this.startActivity(in);
	}
	
	public void bot(View view){
		Intent in = new Intent(TopBottomActivity.this, BottomActivity.class);
		TopBottomActivity.this.startActivity(in);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_top_bottom);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		//top[0].name = "pony";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_bottom, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_top_bottom,
					container, false);
			return rootView;
		}
	}

}
