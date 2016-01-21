package com.jit.pdm.dmstool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AttachAudioVideo extends BaseActivity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private String timeStamp=null;
	static String ttl1=null;
	static String ttl;
	private File mediaFile;
	private Uri fileUri;
	static String destination=null;
	private static String imageDir,videoDir,audioDir;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private File createImages,createVideos,createAudios;
	EditText editTtl,editDestination;
    static String root = Environment.getExternalStorageDirectory().toString();
    private static String DirectoryPath= root+"/DMS/source.txt";
    static File f = new File(DirectoryPath);
    static File f1=new File(root+"/DMS/Working");
    private boolean p;
    static String line;
    private static String source=null;
    static Timer timer;
    static TimerTask timerTask;
	public static String lat_long;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();



    public boolean deleteDirectory(File path) {

        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {

                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {

                    files[i].delete();

                    Toast.makeText(getApplicationContext(), "deleting files", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //  Log.d("File Deletion",path.toString());
        return (path.delete());
    }


    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp
                        try {
                            boolean p = listNames(f1);
                        } catch (Exception e) {
                            Log.d("Initialize timer", "error");
                        }

                    }
                });
            }
        };
    }


    public boolean listNames(File path) throws ParseException {

        Log.d("Entering Listnames","entered");
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {

                return true;
            }
            for (int i = 0; i < files.length; i++) {

                try {
                    String s1 = files[i].getName().toString();
                    String delims = "[_.]+";
                    String[] tokens = s1.split(delims);
                    DateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date dt = format1.parse(tokens[4]);

                    // for calculating ttl expiry
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MINUTE, -Integer.parseInt(tokens[1]));
                    //   Log.d("ans",format1.format(cal.getTime()));



                    Date d1 = format1.parse(tokens[4]);
                    Date d2 = cal.getTime();

                    if (d2.after(d1)) {
                        if (files[i].exists()) {
                            files[i].delete();
                            Log.d("Deletion", files[i].getName().toString());
                            Toast.makeText(getApplicationContext(), "Deleted:"+files[i].getName().toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("not Deleted", files[i].getName().toString());
                    }
                } catch (Exception e) {
                    Log.d("List Names", "Some Error");
                }

            }
        }
        //  Log.d("File Deletion",path.toString());
        return (true);
    }



    private static String source1()
    {
        try {
            if(f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(DirectoryPath));
                Log.d("AttachAud/Vid",DirectoryPath);
                if (br.ready()) {
                    line=br.readLine().substring(3,13);
                }
                br.close();
            }
        }catch(Exception e){
            Log.d("Error in Aud/Vid Source", e.toString());
        }
        Log.d("AttachAud/Vid",line);
        return line;
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






//
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.attachaudiovideo_item1);




     source=source1();
	 editTtl=(EditText)findViewById(R.id.editText1);
	 editDestination=(EditText)findViewById(R.id.editText2);
	Button imageCapture=(Button)findViewById(R.id.image);
        ToggleButton togglebtn=(ToggleButton)findViewById(R.id.tbtn);
	imageCapture.setOnClickListener(new OnClickListener(){
		

		public void onClick(View v) {
			
			ttl1=(String)editTtl.getText().toString();
			//

		//	lat_long=latitude_longitude();
			//
			if(ttl1.isEmpty())
				ttl="50";					// default 50 mins
			else
				ttl=(ttl1);
			destination=(String)editDestination.getText().toString();
			if(destination.isEmpty())
				destination="defaultMCS";		// default destination is default
			if(source.isEmpty())
				source="defaultMCS";
				
			captureImage();
			
		}
	});
	
	Button videoCapture=(Button)findViewById(R.id.video);
	videoCapture.setOnClickListener(new OnClickListener(){
		

		public void onClick(View v) {
			
			ttl1=(String)editTtl.getText().toString();

			//
			//lat_long=latitude_longitude();

			//
			if(ttl1.isEmpty())
				ttl="50";					// default 50 mins
			else
				ttl=(ttl1);
			destination=(String)editDestination.getText().toString();
			if(destination.isEmpty())
				destination="defaultMCS";		// default destination is default
			if(source.isEmpty())
				source="defaultMCS";
			
			captureVideo();
		}
	});
	Button audio =(Button)findViewById(R.id.audio);
	audio.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			ttl1=(String)editTtl.getText().toString();
			
			if(ttl1.isEmpty())
				ttl="50";					// default 50 min
			else
				ttl=(ttl1);
			destination=(String)editDestination.getText().toString();
			if(destination.isEmpty())
				destination="defaultMCS";		// default destination is default
		//	if(source.isEmpty())
		// 		source="defaultMCS";
			startActivity(new Intent("com.test.RECORD"));
		}
	});
	Button sms =(Button)findViewById(R.id.sms);
	sms.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
	/*		
			ttl1=(String)editTtl.getText().toString();
			
			if(ttl1==null||ttl1=="")
				ttl="5";					// default 5 hours
			else
				ttl=(ttl1);
			destination=(String)editDestination.getText().toString();
			if(destination==null||destination=="")
				destination="default";		// default destination is default
			if(source==null||source=="")
				source="default"; */

            Intent i = new Intent(getApplicationContext(), message.class);
            i.putExtra("ttl",(String)editTtl.getText().toString());
            i.putExtra("destination",(String)editDestination.getText().toString());
            startActivity(i);
		//	startActivity(new Intent("com.test.MESSAGE"));
		}
	});
		Button structsms =(Button)findViewById(R.id.structsms);
		structsms.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
	/*
			ttl1=(String)editTtl.getText().toString();

			if(ttl1==null||ttl1=="")
				ttl="5";					// default 5 hours
			else
				ttl=(ttl1);
			destination=(String)editDestination.getText().toString();
			if(destination==null||destination=="")
				destination="default";		// default destination is default
			if(source==null||source=="")
				source="default"; */

				Intent i = new Intent(getApplicationContext(), Structmessage.class);
				i.putExtra("ttl",(String)editTtl.getText().toString());
				i.putExtra("destination",(String)editDestination.getText().toString());
				startActivity(i);
				//	startActivity(new Intent("com.test.MESSAGE"));
			}
		});

        togglebtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean on=((ToggleButton)v).isChecked();
                if ( on ) {
                    timer = new Timer();

                    //initialize the TimerTask's job
                    initializeTimerTask();
                    Log.d("Initialize timer","initializing");
                    Toast.makeText(getApplicationContext(),"Started to check for TTl Expiry",Toast.LENGTH_SHORT).show();
                    //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
                    timer.schedule(timerTask, 0, 10000);//


                } else  {

                    //stop the timer, if it's not already null
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                        Log.d("Timer Stop","Stopping");
                        Toast.makeText(getApplicationContext(),"TTL Checking Stopped",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }




	public void captureVideo()
	{
	//	videoDir=ListPage.mediaDir;
	//	createVideos=new File(videoDir);
		videoDir=CreateDms.Path;
		createVideos=new File( CreateDms.Path);
		if (!createVideos.exists())
			createVideos.mkdir();
		
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

	   intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // set the video image quality to high
	 
	    // start the Video Capture Intent
	    startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
		
		
	}
	public void captureImage()
	
	{
		
	//	imageDir=ListPage.mediaDir;
	
		imageDir=CreateDms.Path;
		
		//	createImages=new File(imageDir);
		createImages=new File( CreateDms.Path);
		if (!createImages.exists())
			createImages.mkdir();
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);// create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

	    // start the image capture Intent
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        try{
	        	if (resultCode == RESULT_OK) {
	        		
	            // Image captured and saved to fileUri specified in the Intent
//	            Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
					//Start Image
					//String outputFile=mediaFile.getAbsolutePath().toString();

					String outputFile=fileUri.getPath();
					Log.v("fileUri",outputFile);
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





	        } else if (resultCode == RESULT_CANCELED) {
	        	
	            // User cancelled the image capture
	        	Toast.makeText(this, "Saving of Image is Cancelled",Toast.LENGTH_LONG).show();
	        	
	        	
	        } else {
	            // Image capture failed, advise user
	        	Toast.makeText(this, "Image Saving Failed",Toast.LENGTH_LONG).show();
	        }
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	    }
	    if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	    	try{
	        if (resultCode == RESULT_OK) {
	            // Video captured and saved to fileUri specified in the Intent

	        		Toast.makeText(this, "Video saved to:\n" +data.getData(), Toast.LENGTH_LONG).show();

				//Start Video
				//String outputFile=mediaFile.getAbsolutePath().toString();
				String outputFile=fileUri.getPath();
				Log.d("fileUri",outputFile);
				//File from=new File(outputFile.substring(7,outputFile.length()));
				File from=new File(outputFile);
				String outputFile_omit_tmp=outputFile.substring(0, outputFile.length() - 4);
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



	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the video capture
	        } else {
	            // Video capture failed, advise user
	        }
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	    }


	  
	    }

	//public  String latitude_longitude(){

	//	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	//	Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	//	double longitude = location.getLongitude();
	//	double latitude = location.getLatitude();
	//	return (latitude+"_"+longitude);
//	}
	
	/** Create a file Uri for saving an image or video */
	private Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
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
          buf.append(mediaFile +",Source"+",Destination"+ "," + timeStamp);
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
	/** Create a File for saving an image or video */
	private File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.
		
	  
	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){

			//Latitude

			Random rand = new Random();
			int i=10;
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
		/*	String latlong1 = "";

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

	    	  mediaFile = new File(imageDir,"IMG_"+ttl+"_"+source+"_"+destination+"_"+latlong1+"_"+timeStamp+"_1.jpg.tmp");
		    // Imp:
	    	  //This location works best if you want the created images to be shared
		    // between applications and persist after your app has been uninstalled.

		    // Create the storage directory if it does not exist
		    /*if (! mediaFile.exists()){
		        try {
					if (! mediaFile.reateNewFile()){
					    Log.d("MyCameraApp", "failed to save Image");
					    return null;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }*/

	        
	    } else if(type == MEDIA_TYPE_VIDEO) {

			Random rand = new Random();
			int i=10;
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
		/*	String latlong1 = "";

			while(i!=0){
				int randomNum = rand.nextInt((9999 - 0) + 1) + 0;
				int randomNum2=rand.nextInt((9999-0)+1)+0;
				latlong1="23.5488"+randomNum+"_87.2926"+randomNum2;
				i-=1;
			}
		*/
			double as[]=new double[2];
			as=getGPS();
			String latlong1=Double.toString(as[0])+"_"+Double.toString(as[1]);

	        mediaFile = new File(videoDir,"VID_"+ttl+"_"+source+"_"+destination+"_"+latlong1+"_"+ timeStamp + "_1.3gp.tmp");
	    } else {
	        return null;
	    }
	    //
	/*    File logFile = new File(CreateDms.Path+"/log.txt");
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
	          buf.append(mediaFile +",Source"+",Destination"+ "," + timeStamp);
	          buf.newLine();
	          buf.close();
	       }
	       catch (IOException e)
	       {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       }
	       
	       */
	       //
	    
	    
	    
	    return mediaFile;
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


//23.548822, 87.292620