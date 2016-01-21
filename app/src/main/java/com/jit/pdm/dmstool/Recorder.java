package com.jit.pdm.dmstool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import im.delight.android.location.SimpleLocation;

public class Recorder extends Activity {

    private SimpleLocation location;

    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start, stop, play;
    private String ctime = null;
    private String timeStamp = null;
    private String ttl = AttachAudioVideo.ttl;
    private String source = null;
    private String destination = AttachAudioVideo.destination;
    String root = Environment.getExternalStorageDirectory().toString();
    private String DirectoryPath = root + "/DMS/source.txt";
    File f = new File(DirectoryPath);
    private boolean p;
    String line;

    private String source1() {
        try {
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(DirectoryPath));
                Log.d("Recorder", DirectoryPath);
                if (br.ready()) {
                    line = br.readLine().substring(3, 13);
                }
                br.close();
            }
        } catch (Exception e) {
            Log.d("Error in Recorder", e.toString());
        }
        Log.d("Source", line);
        return line;
    }
    private double[] getGPS() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

/* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
        Location l = null;

        for (int i=providers.size()-1;i>=0;i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }

        double[] gps = new double[2];
        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        return gps;
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(root + "/DMS/Working");
        if (!file.exists()) {
            file.mkdirs();
        }
    //    final double latitude = location.getLatitude();
    //    final double longitude = location.getLongitude();

    //    Log.v("Latitude",Double.toString(latitude));
    //    Log.v("Longitude",Double.toString(longitude));

        source = source1();

        if (source.isEmpty()) {
            source = "defaultMCS";
        }
//Latitude
     //   LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
     //   Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
     //   double longitude = location.getLongitude();
     //   double latitude = location.getLatitude();

        Random rand = new Random();
        int i=10;
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
    /*    String latlong1 = "";


        while(i!=0){
            int randomNum = rand.nextInt((9999 - 0) + 1) + 0;
            int randomNum2=rand.nextInt((9999-0)+1)+0;
            latlong1="23.5488"+randomNum+"_87.2926"+randomNum2;
            i-=1;
        }
    */
//
        double as[]=new double[2];
        as=getGPS();
        String latlong1=Double.toString(as[0])+"_"+Double.toString(as[1]);
//
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //  return (file.getAbsolutePath() + "/"+"AUD_"+ timeStamp+".3gp");
        return (file.getAbsolutePath() + "/" + "SVS_" + ttl + "_" + source + "_" + destination + "_"+latlong1+"_" + timeStamp + "_1.3gp.tmp");
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);


        start = (Button) findViewById(R.id.btnStart);
        stop = (Button) findViewById(R.id.btnStop);


        //    play = (Button)findViewById(R.id.button3);

        stop.setEnabled(false);
        // play.setEnabled(false);
    /*  outputFile = getFilename();
     ctime=""+System.currentTimeMillis();

      myAudioRecorder = new MediaRecorder();
      myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
      myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
    //  myAudioRecorder.setOutputFile(outputFile);
      myAudioRecorder.setOutputFile(getFilename());    */
    }

    public void start(View view) {
	/*   
	   
		AttachAudioVideo.ttl1=(String)editTtl.getText().toString();
		
		if(AttachAudioVideo.ttl1==null)
			ttl=5;					// default 5 hours
		else
			AttachAudioVideo.ttl=Integer.parseInt(ttl1);
		AttachAudioVideo.destination=(String)editDestination.getText().toString();
		if(AttachAudioVideo.destination==null)
			AttachAudioVideo.destination="default";		// default destination is default
		
	 */


        ///
        outputFile = getFilename();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
        // myAudioRecorder.setOutputFile(getFilename());


        ///
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

    }

    public void stop(View view) {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        stop.setEnabled(false);
        start.setEnabled(true);
        // play.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                Toast.LENGTH_SHORT).show();

       //Start
        File from=new File(outputFile);
        String outputFile_omit_tmp=outputFile.substring(0,outputFile.length()-4);
        Log.v("Output init", outputFile);
        Log.v("OutputFileName", outputFile_omit_tmp);

        File to=new File(outputFile_omit_tmp);
        try {
            if (from.exists())
                if (from.renameTo(to))
                    Log.v("Rename", "Success");
                else
                    Log.v("Rename","Failed");
        }
        catch(Exception e){
            Log.e("UniqException", "Exception: "+Log.getStackTraceString(e));
        }
        //End


        //log
   /*   try {
    	  
         //  appendLog();
     // outputFile = getFilename();
    //  ctime=""+System.currentTimeMillis();
      }catch(Exception e){}
   
   */


        //
    }

    //Logging
   /*
   public void appendLog()
   {       
      File logFile = new File(CreateDms.Path+"/log.txt");
      if (!logFile.exists())
      {
         try
         {
        	// logFile.mkdirs();
           logFile.createNewFile();
          
         } 
         catch (IOException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      try
      {
         //BufferedWriter for performance, true to set append to file flag
         BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
         buf.append(outputFile +",Source"+",Destination"+ "," + ctime);
         buf.newLine();
         buf.close();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   */
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.main_menu:
                exit();
                return true;
            case R.id.exit:
                System.exit(1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void exit()
    {
        Intent i = new Intent(this, CreateDms.class);
        startActivity(i);

    }
}








