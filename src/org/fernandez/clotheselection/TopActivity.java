package org.fernandez.clotheselection;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.fernandez.clothetype.TopClothe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TopActivity extends AbstractPosition implements SwipeInterface{
		
	protected void initialize(){
		for(int i = 0; i < 10; i++){
			this.clothe[i] = new TopClothe();
			this.clothe[i].setLabel((char)(i + 65));
		}
		this.clotheImages[0] = R.drawable.ic_top01;
		this.clotheImages[1] = R.drawable.ic_top02;
		this.clotheImages[2] = R.drawable.ic_top03;
		this.clotheImages[3] = R.drawable.ic_top04;
		this.clotheImages[4] = R.drawable.ic_top05;
		this.clotheImages[5] = R.drawable.ic_top06;
		this.clotheImages[6] = R.drawable.ic_top07;
		this.clotheImages[7] = R.drawable.ic_top08;
		this.clotheImages[8] = R.drawable.ic_top09;
		this.clotheImages[9] = R.drawable.ic_top10;
	}

	protected void updateTextView() {
    	posText.setText( (arrayPosition + 1) + "/10 camisa \""+ clothe[arrayPosition].name + "\" " + 
				(clothe[arrayPosition].isSelected() ? "SELECTED" : "NOT SELECTED!") );
    	this.imgView.setImageResource(clotheImages[arrayPosition]);
	}
	
	@SuppressLint("NewApi") public void selectClothe(){
		if(!this.clothe[this.arrayPosition].isSelected()){
			if(this.btAdapter.isEnabled() && !this.btAdapter.isDiscovering() && !clothe[arrayPosition].isSelected()){
				try{
					Set<BluetoothDevice> bd = btAdapter.getBondedDevices();
					for(BluetoothDevice b : bd){
						Toast.makeText(this,"Signal: " + this.clothe[arrayPosition].getLabel() + "- Establishing connection with: " 
								+ b.getName() + "\t" + b.getAddress() + "...", Toast.LENGTH_SHORT).show();
						btSocket = b.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
						btSocket.connect();
						DataOutputStream w = new DataOutputStream(btSocket.getOutputStream());
						w.writeByte(this.clothe[this.arrayPosition].getLabel());
						w.close();
						btSocket.close();
						Toast.makeText(this, "succesfull connection done!", Toast.LENGTH_SHORT).show();
					}
				}
				catch(IOException ioe){
					Toast.makeText(this, ioe.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
				catch(Exception e){
					Toast.makeText(this, "Fatal connection error!", Toast.LENGTH_SHORT).show();
				}
				finally{
					this.clothe[arrayPosition].setSelected(true);
					updateTextView();
				}
			}
		}
		else
			Toast.makeText(this, "Couldn't send, clothe already selected!", Toast.LENGTH_SHORT).show();
		
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
		imgView = (ImageView)this.findViewById(R.id.imageView1);
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
