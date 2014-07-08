package org.fernandez.clotheselection;

import org.fernandez.clothetype.AbstractClothe;
import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AbstractPosition extends ActionBarActivity{
	protected BluetoothAdapter btAdapter = null;
	protected AbstractClothe [] clothe = new AbstractClothe[20];
	protected int arrayPosition = 0;
	protected Button btSearch = null;
	protected TextView posText = null;

	protected abstract void initialize();
	protected abstract void updateTextView();
	
	public void selectClothe(View view){
		if(!clothe[arrayPosition].isSelected())
			Toast.makeText(this, "Clothe at position " + arrayPosition + " sent signal: " + (char)(arrayPosition + 65), Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "Couldn't send, clothe already selected!", Toast.LENGTH_SHORT).show();
		this.clothe[arrayPosition].setSelected(true);
		updateTextView();
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

}
