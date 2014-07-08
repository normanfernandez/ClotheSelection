package org.fernandez.clotheselection;

import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

public abstract class AbstractPosition extends ActionBarActivity{
	protected BluetoothAdapter btAdapter = null;
	protected int arrayPosition = 0;
	protected abstract void updateTextView();
	protected Button btSearch = null;
	protected TextView posText = null;
}
