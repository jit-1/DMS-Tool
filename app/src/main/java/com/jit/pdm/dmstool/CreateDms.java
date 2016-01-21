package com.jit.pdm.dmstool;

import java.io.File;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CreateDms extends Activity implements OnClickListener{

	 boolean mExternalStorageAvailable = false;
	 boolean mExternalStorageWriteable = false;
	 static String Path;
	 static String  Path1;   
	 private File myDir;
	 static int Flag=0;
	 public boolean onKeyDown(int keyCode, KeyEvent event)
	   {
	       if ((keyCode == KeyEvent.KEYCODE_BACK))
	       {
	           finish();
	       }
	       return super.onKeyDown(keyCode, event);
	   }
	
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		File f = new File(Path1+"/source.txt");
		   if(f.exists())
		   { 
		    Flag=1;
		    Toast.makeText(this, "Source Present",Toast.LENGTH_SHORT).show();
		   }
		   else
		   {   Flag=0;
		   Toast.makeText(this, "No Source:Registering",Toast.LENGTH_SHORT).show();
		   }
		
		
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_dms);
		
		
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
	         mExternalStorageAvailable = mExternalStorageWriteable = true;
	        String root = Environment.getExternalStorageDirectory().toString();
	        Path1=root +"/"+"DMS";
	        Path=root + "/"+"DMS"+"/"+"Working";
	        myDir = new File(Path);
	        boolean tin=myDir.mkdir();
	        if (tin)
	        	
	        {	
	        	Toast.makeText(getApplicationContext()," created in "+root , Toast.LENGTH_SHORT).show();
	      //Creating Disaster File in the above directory
	        	
	        }
	        
	        if(!myDir.exists()){
	        		myDir.mkdirs();
	         }
		
			}
		
		Button createDms=(Button) findViewById(R.id.createdms);
		createDms.setOnClickListener(this);
		

		Button exit1 = (Button)findViewById(R.id.exit1);
		exit1.setOnClickListener(this);
		File f = new File(Path1+"/source.txt");
		   if(f.exists())
		   { 
		    Flag=1;
		    Toast.makeText(this, "Source Present",Toast.LENGTH_SHORT).show();
		   }
		   else
		   {   Flag=0;
		   Toast.makeText(this, "No Source:Registering",Toast.LENGTH_SHORT).show();
		   }
		   
		   }
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.createdms && Flag==0){
			Log.d("CreateDms","Before");
            startActivity(new Intent(this,NewDms.class));
		    Log.d("CreateDms", "After");
        }
		else if(v.getId()==R.id.createdms && Flag==1){
			Intent i = new Intent(this,ListPage.class);
		    startActivity(i);
		}
		
			else if(v.getId()==R.id.exit1){
			finish();
		}
	}
	
	}


