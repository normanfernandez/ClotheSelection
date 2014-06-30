package org.fernandez.clotheselection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

   private EditText  username=null;
   private EditText  password=null;
   private Button login = null;
   int counter = 3;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      username = (EditText)findViewById(R.id.editText1);
      password = (EditText)findViewById(R.id.editText2);
      login = (Button)findViewById(R.id.button1);
   }

   public void skip(View view){
	   Intent in = new Intent(MainActivity.this, TopBottomActivity.class);
	   MainActivity.this.startActivity(in);
   }
   public void login(View view){
      if(username.getText().toString().equalsIgnoreCase("fdolivares") && 
      password.getText().toString().equals("felixdob92"))
      {
    	  Intent in = new Intent(MainActivity.this, TopBottomActivity.class);
    	  MainActivity.this.startActivity(in);
   }	
   else{
      Toast.makeText(getApplicationContext(), "Wrong Credentials",
      Toast.LENGTH_SHORT).show();
   }

}
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

}