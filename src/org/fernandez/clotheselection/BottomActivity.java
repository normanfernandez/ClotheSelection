package org.fernandez.clotheselection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.fernandez.clothetype.BottomClothe;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BottomActivity extends AbstractPosition implements SwipeInterface{
	
	protected void initialize(){
		for(int i = 0; i < 10; i++){
			this.clothe[i] = new BottomClothe();
			this.clothe[i].setLabel((char)(i + 65));
		}
		this.clotheImages[0] = R.drawable.ic_bottom01;
		this.clotheImages[1] = R.drawable.ic_bottom02;
		this.clotheImages[2] = R.drawable.ic_bottom03;
		this.clotheImages[3] = R.drawable.ic_bottom04;
		this.clotheImages[4] = R.drawable.ic_bottom05;
		this.clotheImages[5] = R.drawable.ic_bottom06;
		this.clotheImages[6] = R.drawable.ic_bottom07;
		this.clotheImages[7] = R.drawable.ic_bottom08;
		this.clotheImages[8] = R.drawable.ic_bottom09;
		this.clotheImages[9] = R.drawable.ic_bottom10;
		for(int i = 0; i < 10; i++)
			clotheImagesCopy[i] = clotheImages[i];
	}
	
	protected void updateTextView() {
		posText.setText( (arrayPosition + 1) + "/10 pantalon");
		this.imgView.setImageResource(clotheImages[arrayPosition]);
		this.btSearch.setEnabled(!clothe[arrayPosition].isSelected());
		this.btPut.setEnabled(clothe[arrayPosition].isSelected());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bottom);
		this.btAdapter = BluetoothAdapter.getDefaultAdapter();
		btSearch = (Button)this.findViewById(R.id.btSearch);
		btPut = (Button)this.findViewById(R.id.btPut);
		posText = (TextView)this.findViewById(R.id.botPosText);
		imgView = (ImageView)this.findViewById(R.id.imageView1);
		btSearch.setEnabled(this.btAdapter.isEnabled());
		btPut.setEnabled(!this.btAdapter.isEnabled());
		this.arrayPosition = 0;
		initialize();
		
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
