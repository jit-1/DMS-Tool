package com.jit.pdm.dmstool;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class message extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	//

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private  Location mLastLocation;

    // Google client to interact with Google API
    private static GoogleApiClient mGoogleApiClient;

    public double latitude;
    public double longitude;
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

    public  void displayLocation() {

        File file = new File(Environment.getExternalStorageDirectory().toString()+"/DMS/Latlong.txt");
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
       // Log.d("Last Location",mLastLocation.toString());

        if (mLastLocation != null) {
             latitude = mLastLocation.getLatitude();
             longitude = mLastLocation.getLongitude();

            String lnlg= latitude+"_"+longitude;
            //lblLocation.setText(latitude + ", " + longitude);
            try{

                if(file.exists()) {
                    file.delete();
                    file.createNewFile();
                    OutputStream fos = new FileOutputStream(file, true);

                    //     BufferedWriter bufferWritter = new BufferedWriter(fileWritter);


                    //         bufferWritter.append(etName);

                    fos.write(lnlg.getBytes());
                    Log.d("Error in Message Source", "IF");
                }
                else{
                    file.createNewFile();
                    OutputStream fos = new FileOutputStream(file, true);

                    //     BufferedWriter bufferWritter = new BufferedWriter(fileWritter);


                    //         bufferWritter.append(etName);

                    fos.write(lnlg.getBytes());
                    Log.d("Error in Message Source", "ELSE");
                }
            }catch(IOException e){
                Log.d("New F","Error");
            }
         //   return(latitude+"_"+longitude);
        } else {

            // lblLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");

            try {
                    if(file.exists()) {
                        BufferedReader br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().toString()+"/DMS/Latlong.txt"));
                        Log.d("Message", "DirectoryPath");
                        if (br.ready()) {
                            line=br.readLine().trim();
                            Log.d("Error in Message Source", line);
           //                 return(line);

                        }
                        br.close();

                    }
                    else
                    {
                        Log.d("Error in Message Source", "0.00");
          //              return "0.000000_0.000000";
                    }
                }catch(Exception e){
                    Log.d("Error in Message Source", e.toString());
                }

        }
  //  return "0.00000_0.00000";
    }
    protected  synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private  boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("LatLong", "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }
    //
    String outputFile = null;
	 EditText ETName;
	   private Button Send;
	   private String ctime= null;
	   private String timeStamp=null;
       private String source=null;
	   //private String ttl=AttachAudioVideo.ttl;
	   //private String destination=AttachAudioVideo.destination;
    String root = Environment.getExternalStorageDirectory().toString();
    private String DirectoryPath= root+"/DMS/source.txt";
    File f = new File(DirectoryPath);
    private boolean p;
    String line;
    private String ttl;
    private String destination;
    private String source1()
    {
        try {
            if(f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(DirectoryPath));
                Log.d("Message", DirectoryPath);
                if (br.ready()) {
                    line=br.readLine().substring(3,13);
                }
                br.close();
            }
        }catch(Exception e){
            Log.d("Error in Message Source", e.toString());
        }
        Log.d("Message",line);
        return line;
    }
	   
	   
	   
	   private String getFilename(){
	       String filepath = Environment.getExternalStorageDirectory().getPath();
	       //File file = new File(filepath,AUDIO_RECORDER_FOLDER);
	    //   File file = new File(AUDIO_RECORDER_FOLDER);
	       source=source1();

           File file = new File( root+"/DMS//Working");
	       try{

           //testing
            //   Toast.makeText(getApplicationContext(), com.jit.pdm.dmstool.AttachAudioVideo.ttl+"  "+com.jit.pdm.dmstool.AttachAudioVideo.destination+"Display", Toast.LENGTH_LONG).show();
	       
	       if(!file.exists()){
	               file.mkdirs();
	       }

	       
			
			if(ttl.isEmpty())
			{	
				ttl="50";					// default 50 mins
			Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_LONG).show();
			
			}

			if(destination.isEmpty())
			{
				destination= "defaultMCS";		// default destination is defaultMCS
			}
	      }catch(Exception e){}
//Latitude
    //       LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    //       Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    //       double longitude = location.getLongitude();
    //       double latitude = location.getLatitude();






           String latlong= "0.000_0.000";


//"23.548966_87.292813"
//
           try {
               if(file.exists()) {
                   BufferedReader br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().toString()+"/DMS/Latlong.txt"));
                   Log.d("Message", "DirectoryPath");
                   if (br.ready()) {
                       latlong=br.readLine().trim();
                       Log.d("Error in Message Source", line);
                       //                 return(line);

                   }
                   br.close();

               }
               else
               {
                   Log.d("Error in Message Source", "0.00");
                   latlong="0.000_0.0000";
               }
           }catch(Exception e){
               Log.d("Error in Message Source", e.toString());
           }

    /*       Random rand = new Random();
           int i=10;
           // nextInt is normally exclusive of the top value,
           // so add 1 to make it inclusive
           String latlong1 = "";

           while(i!=0){
               int randomNum = rand.nextInt((9999 - 0) + 1) + 0;
               int randomNum2=rand.nextInt((9999-0)+1)+0;
               latlong1="23.5488"+randomNum+"_87.2926"+randomNum2;
               i-=1;
           }
    */
//

          //         displayLocation();
        //   onConnected(Bundle arg0);
      //     Log.d("Lat Long",+latitude+" "+longitude);

           double as[]=new double[2];
           as=getGPS();
           String latlong1=Double.toString(as[0])+"_"+Double.toString(as[1]);
//
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
	     //  return (file.getAbsolutePath() + "/"+"AUD_"+ timeStamp+".3gp");
	      return (file.getAbsolutePath() + "/"+"SMS_"+ttl+"_"+ source +"_"+ destination +"_"+latlong1+"_" + timeStamp+"_1.txt");
	     // added latitude,logitude
	      }
	





	   public boolean onKeyDown(int keyCode, KeyEvent event)
	   {
	       if ((keyCode == KeyEvent.KEYCODE_BACK))
	       {
	           finish();
	       }
	       return super.onKeyDown(keyCode, event);
	   }

    //


       protected void onResume(Bundle savedInstanceState){
           super.onCreate(savedInstanceState);
           setContentView(R.layout.message);

           //



           //

       }



	   //
	    @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.message);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
               ttl = extras.getString("ttl");
               destination = extras.getString("destination");
            }
          ETName= (EditText)findViewById(R.id.editText1);
	      Send = (Button )findViewById(R.id.btnSend);
	
	      
	      Send.setOnClickListener(new OnClickListener(){

	      public void onClick(View v) {

	    	  try{	  outputFile = getFilename();
   		   String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());



   			    String etName =  (String)ETName.getText().toString();

   			                            File file =new File(outputFile);

   			                            //if file doesnt exists, then create it
   			                            if(!file.exists()){
   			                                file.createNewFile();
   			                            }


   			                       //     FileWriter fileWritter = new FileWriter(file.getName(),true);
   			                         OutputStream fos=new FileOutputStream(outputFile,true);

   			                       //     BufferedWriter bufferWritter = new BufferedWriter(fileWritter);


   			                       //         bufferWritter.append(etName);

   			                      fos.write(etName.getBytes());
   			                   Toast.makeText(message.this,"SMS saved",Toast.LENGTH_LONG).show();
   			                       //         bufferWritter.close();


   			                        }catch (IOException e) {

   			                            e.setStackTrace(null) ; }








			}
		});
	      
	      

	   }
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
                System.exit(0);
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
//


//

}
