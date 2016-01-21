package com.jit.pdm.dmstool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewDms extends Activity {
	public static final int FLAG_ACTIVITY_CLEAR_TOP=67108864;  
private Button create;
private EditText dmsName,editSource;
String disasterName;
static String source;
static String fileName;//file where data is stored
static String DirName;//Directory where data is stored
Editable source1;
static String DisasterName,disasterFileName;
static String dirPath;
public static int Flag=0;
String root = Environment.getExternalStorageDirectory().toString();




public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.newdms);
	
	
	
	editSource=(EditText)findViewById(R.id.editText1);
	create=(Button)findViewById(R.id.create);
	String txt1=null;
	
	File tfile=new File(CreateDms.Path,"source.txt");
	if(tfile.exists())
		{
		BufferedReader buf1;
		try {
			buf1 = new BufferedReader(new FileReader(tfile));
		    txt1=buf1.readLine();
		    buf1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
      //  txt1=txt1.substring(3, 12);
      //  editSource.setText(txt1);
		}
		
	create.setOnClickListener(new OnClickListener() {
								  public void onClick(View v) {
									  //File existense=new File(CreateDms.Path1,"1.txt");

									  try {
										  source = (String) editSource.getText().toString();
										  if (source.length() != 10) {
											  Toast.makeText(getApplicationContext(), "Enter a proper 10 Digit Phone Number", Toast.LENGTH_SHORT).show();

										  } else {

											  //if(!existense.exists()){

											  File sfile = new File(root + "/DMS", "source.txt");
											  if (sfile.createNewFile()) ;
											  BufferedWriter buf = new BufferedWriter(new FileWriter(sfile, true));
											  buf.append("me:" + source);
											  buf.newLine();
											  buf.close();
											  //	}
											  //	File flag=new File(CreateDms.Path1,"1.txt");
											  //	flag.createNewFile();
											  newdms();
										  }

									  } catch (IOException e) {
										  // TODO Auto-generated catch block
										  e.printStackTrace();
									  }


								  }
							  }
	);
	



	//setting the spinner for disaster type.
	Spinner spin = (Spinner)findViewById(R.id.disaster_type);
	ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this, R.array.disaster_cont, android.R.layout.simple_spinner_item);
	adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spin.setAdapter(adap);
	spin.setOnItemSelectedListener(new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			// TODO Auto-generated method stub
            ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(0,0,0));
			disasterName=parent.getItemAtPosition(pos).toString();
			System.out.println(disasterName);
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}
	});
	Toast.makeText(this, "done",Toast.LENGTH_SHORT);
	
	Button cancel=(Button)findViewById(R.id.cancel);
	cancel.setOnClickListener(new OnClickListener() {
								  public void onClick(View v) {
									  createdms();//back to the main page

								  }
							  }
	);
}
public void newdms() throws IOException
{
	File myDir,Disaster;

	
	//Editable dms_Name=dmsName.getText();
	source1=editSource.getText();
	source=(String)source1.toString();



	
    String disastername="Disaster Name: "+disasterName+"\n";
    File sfile=new File(root+"/DMS","source.txt");
    if(sfile.createNewFile());
    BufferedWriter buf = new BufferedWriter(new FileWriter(sfile, true));
    buf.append(disastername);
    buf.newLine();
    buf.close();
    boolean mExternalStorageAvailable = false;
    boolean mExternalStorageWriteable = false;
    String state = Environment.getExternalStorageState();

    if (Environment.MEDIA_MOUNTED.equals(state)) {

        String root = Environment.getExternalStorageDirectory().toString();

      myDir =new File(CreateDms.Path);
        boolean tin=myDir.mkdir();
        
        if (tin)
        	
        {	
        	
        	
        	

        	disasterFileName= disasterName+".txt";
        	Disaster=new File(dirPath,disasterFileName);
        	if(Disaster.createNewFile())
        	{
        		Toast.makeText(getApplicationContext(),"File Created" , Toast.LENGTH_SHORT).show();



			}
        	
        else
        	Toast.makeText(getApplicationContext(),"Not Created" , Toast.LENGTH_SHORT).show();
        }    
    
    
    }
    else
    {
    	//Internal Storage
    File dir=getDir(DirName, MODE_WORLD_READABLE);
    fileName=DirName+"/"+disasterName;
    Disaster=new File(fileName);
    Disaster.createNewFile();
	
    }
	
	
	Intent i = new Intent(this,ListPage.class);
    startActivity(i);
}
public void createdms()
{
	
	
	Intent i = new Intent(this,CreateDms.class);
	i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(i);
}
}

