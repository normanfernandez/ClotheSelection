package org.fernandez.clotheselection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import org.fernandez.clothetype.AbstractClothe;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AbstractPosition extends ActionBarActivity{
	protected BluetoothAdapter btAdapter = null;
	protected BluetoothSocket btSocket = null;
	protected BluetoothDevice btDevice = null;
	protected Set<BluetoothDevice> deviceSet = null;
	protected AbstractClothe [] clothe = new AbstractClothe[10];
	protected int [] clotheImages = new int[10];
	protected int [] clotheImagesCopy = new int[10];
	protected int arrayPosition = 0;
	protected Button btSearch = null;
	protected DataOutputStream out = null;
	protected Button btPut = null;
	protected TextView posText = null;
	protected ImageView imgView = null;
	protected static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

	protected abstract void initialize();
	protected abstract void updateTextView();
	
	
	
	protected void sendDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want this clothe?");
		builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendZ();
				clotheImages[arrayPosition] = R.drawable.ic_selected;
				clothe[arrayPosition].setSelected(true);
				updateTextView();
			}
		});
		builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				sendZ();
			}
		});
		builder.show();
	}
	
	public void scrollLeft(){
		if(arrayPosition <= 0)
			return;
		arrayPosition--;
		updateTextView();
	}
	
	public void scrollRight(){
		if(arrayPosition >= 9)
			return;
		arrayPosition++;
		updateTextView();
	}
	
	public void putClothe(View view){
		this.clothe[arrayPosition].setSelected(false);
		this.clotheImages[arrayPosition] = this.clotheImagesCopy[arrayPosition];
		updateTextView();
	}
	
	@SuppressLint("NewApi") public void selectClothe(View view){
		if(this.btAdapter.isEnabled() && !clothe[arrayPosition].isSelected()){
			try{
				this.btSearch.setEnabled(false);
				if(deviceSet == null)
					this.deviceSet = btAdapter.getBondedDevices();
				if(deviceSet.isEmpty())
					throw new IOException("There are no bonded devices!");
				else
					this.btDevice = (BluetoothDevice) deviceSet.toArray()[0];
				this.btSearch.setEnabled(false);
				Toast.makeText(this,"Establishing connection with: " + btDevice.getName() + "...", Toast.LENGTH_SHORT).show();
				btAdapter.cancelDiscovery();
				btSocket = null;
				btSocket = btDevice.createRfcommSocketToServiceRecord(SPP_UUID);
				Toast.makeText(this,"Conectando con el Socket... ", Toast.LENGTH_SHORT).show();
				if(!btSocket.isConnected())
					btSocket.connect();
				out = new DataOutputStream(btSocket.getOutputStream());
				out.writeByte(clothe[arrayPosition].getLabel());
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
				Toast.makeText(this, "Succesfull connection and writing done!", Toast.LENGTH_SHORT).show();
				this.sendDialog();
			}
			catch(IOException ioe){
				Toast.makeText(this, ioe.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			}
			catch(Exception e){
				Toast.makeText(this, "Fatal connection error!", Toast.LENGTH_SHORT).show();
			}
			finally{
				updateTextView();
			}
		}
	}

	@SuppressLint("NewApi") protected void sendZ(){
		try{
			this.btSearch.setEnabled(false);
			if(deviceSet.isEmpty())
				throw new IOException("There are no bonded devices!");
			Toast.makeText(this,"Sending Z to: " + btDevice.getName() + "\t" + btDevice.getAddress() + "...", Toast.LENGTH_SHORT).show();
			btAdapter.cancelDiscovery();
			out.writeByte(clothe[arrayPosition].getLabel());
			Toast.makeText(this, "Z sent succesfully!!", Toast.LENGTH_SHORT).show();
		}
		catch(IOException ioe){
			Toast.makeText(this, "Couldn't send Z: " + ioe.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){
			Toast.makeText(this, "Fatal connection error!", Toast.LENGTH_SHORT).show();
		}
		finally{
			try{
			out.close();
			btSocket.close();}
			catch(Exception e){
				
			}
		}
	}
}
