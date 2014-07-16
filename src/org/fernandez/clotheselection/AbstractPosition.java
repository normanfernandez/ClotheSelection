package org.fernandez.clotheselection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.fernandez.clothetype.AbstractClothe;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
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
	protected AbstractClothe [] clothe = new AbstractClothe[10];
	protected int [] clotheImages = new int[10];
	protected int arrayPosition = 0;
	protected Button btSearch = null;
	protected TextView posText = null;
	protected ImageView imgView = null;
	protected static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	protected abstract void initialize();
	protected abstract void selectClothe();
	protected abstract void updateTextView();
	
	public void sendSignal(View view){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to send the signal?");
		builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectClothe();
			}
		});
		builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(DialogInterface dialog, int which) {
				btAdapter = BluetoothAdapter.getDefaultAdapter();
				Set<BluetoothDevice> btSet = btAdapter.getBondedDevices();
				Iterator<BluetoothDevice> it = btSet.iterator();
				btDevice = it.next();
				try{
					btAdapter.cancelDiscovery();
					btSocket = btDevice.createRfcommSocketToServiceRecord(SPP_UUID);
					System.out.println("Comenzando a connectar");
					btSocket.connect();
					DataOutputStream out = new DataOutputStream(btSocket.getOutputStream());
					System.out.println("Comenzando a escribir");
					out.writeByte('Z');
					out.close();
					btSocket.close();
					System.out.println("Terminando de escribir");
				}catch(IOException ioe){
					System.out.println("Error con el socket de la z: "  + ioe.getLocalizedMessage());
				}
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
	
	@SuppressLint("NewApi") public void selectClothe(View view){
		if(this.btAdapter.isEnabled() && !this.btAdapter.isDiscovering() && !clothe[arrayPosition].isSelected()){
			try{
				this.btSearch.setEnabled(false);
				Set<BluetoothDevice> bd = btAdapter.getBondedDevices();
				for(BluetoothDevice b : bd){
					this.btSearch.setEnabled(false);
					Toast.makeText(this,"Establishing connection with: " + b.getName() + "\t" + b.getAddress() + "...", Toast.LENGTH_SHORT).show();
					btSocket = b.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
					btSocket.connect();
					DataOutputStream w = new DataOutputStream(btSocket.getOutputStream());
					w.writeByte(clothe[arrayPosition].getLabel());
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
				this.btSearch.setEnabled(true);
			}
		}
	}

}
