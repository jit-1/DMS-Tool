package com.jit.pdm.dmstool;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Jit on 25-06-2015.
 */
public class Structmessage extends Activity {

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
        Log.d("StructMessage",line);
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
    //    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    //    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    //    double longitude = location.getLongitude();
    //    double latitude = location.getLatitude();

        //
/*
        Random rand = new Random();
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
        double as[]=new double[2];
        as=getGPS();
        String latlong1=Double.toString(as[0])+"_"+Double.toString(as[1]);
//
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //  return (file.getAbsolutePath() + "/"+"AUD_"+ timeStamp+".3gp");
        return (file.getAbsolutePath() + "/"+"TXT_"+ttl+"_"+ source +"_"+ destination +"_"+latlong1+"_" + timeStamp+"_1.txt");
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




    protected void onResume(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

    }



    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structmessage);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ttl = extras.getString("ttl");
            destination = extras.getString("destination");
        }
      //  ETName= (EditText)findViewById(R.id.editText1);
      //  Send = (Button )findViewById(R.id.btnSend);

        //
        try {
            itemEntry1();
        }catch(Exception e) {

        }
            //
//





//

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

    public void itemEntry1() {
        String path = Environment.getExternalStorageDirectory().toString() + "/DMS/Settings";
        File f = new File(path);
        File file[] = f.listFiles();
        final List<String> option1=new ArrayList<String>();


        for (int i = 0; i < file.length; i++) {
            String temp = file[i].getName();
            String[] parts = temp.split("\\.");
            //System.out.println(parts[0]);
            option1.add(i, parts[0]);
        }

        final Spinner spinner1 = (Spinner) findViewById(R.id.item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,option1);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        //final int[] iCurrentSelection = {spinner1.getSelectedItemPosition()};
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if (initialDisplay1 = false){
                String pass = parent.getItemAtPosition(position).toString();
                itemEntry2(pass);
                //}
                //initialDisplay1 = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void itemEntry2(final String pass) {

        String entry2 = pass + ".txt";
        final List<String> option2 = new ArrayList<String>();
        File directory = Environment.getExternalStorageDirectory();
        File fileip = new File(directory.getAbsolutePath() + "/DMS/Settings", entry2);
        try {
            FileInputStream fIn = new FileInputStream(fileip);
            InputStreamReader file = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(file);
            String line = br.readLine();
            String text = "";
            int i = 0;
            while (line != null) {

                option2.add(i, line);
                i = i + 1;
                line = br.readLine();

            }

            br.close();
            file.close();

        } catch (IOException e) {

        }

        final Spinner spinner2 = (Spinner) findViewById(R.id.item2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, option2);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        //final int[] iCurrentSelection = {spinner2.getSelectedItemPosition()};
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if (initialDisplay2 = false){
                String pass2 = parent.getItemAtPosition(position).toString();
                String fpass = pass + ": " + pass2 + ": ";
                itemEntry3(fpass);
                // }
                // initialDisplay2 = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void itemEntry3(final String fpass) {


        final List<String> option3 = new ArrayList<String>();
        option3.add(0,"1");
        option3.add(1,"2");
        option3.add(2,"3");
        option3.add(3,"5");
        option3.add(3,"10  ");
        option3.add(4, "-Custom-");
        /*option3.add(4,"21-25");
        option3.add(5,"26-30");
        option3.add(6,"31-35");
        option3.add(7,"36-40");
        option3.add(8,"41-45");
        //option3.add(3,"46-50");*/


        final Spinner spinner3 = (Spinner) findViewById(R.id.item3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, option3);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        //final int[] iCurrentSelection = {spinner3.getSelectedItemPosition()};
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if (initialDisplay3 = false) {
                String pass3 = parent.getItemAtPosition(position).toString();
                String entry3;

                if (pass3.equals("-Custom-")) {
                    citemEntry4(fpass);
                    //
                } else {
                    entry3 = pass3;
                    String finalpass = fpass + entry3;
                    itemEntry4(finalpass + "\n");
                    //System.out.println(entry3+"++++++++++++++++++++++++++++++++++++++++");
                }


                //}
                //initialDisplay3 = false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

    }

    public void itemEntry4(final String opass) {


        final TextView tt = (TextView) findViewById(R.id.op);

        Button addbtn = (Button) findViewById(R.id.add);
        addbtn.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          String tempo = (String) tt.getText();
                                          tempo = tempo + opass;
                                          tt.setText(tempo);
                                          fentry(tempo);
                                      }
                                  }

        );

    }

    public void citemEntry4(final String opass) {

        final TextView tt = (TextView) findViewById(R.id.op);
        Button addbtn = (Button) findViewById(R.id.add);
        addbtn.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          String c_entry;
                                          EditText ed = (EditText)findViewById(R.id.item4);
                                          c_entry = ed.getText().toString();
                                          String copass = opass + c_entry + "\n";
                                          String tempo = (String) tt.getText();
                                          tempo = tempo + copass;
                                          tt.setText(tempo);
                                          fentry(tempo);

                                      }
                                  }
        );

    }

    public void fentry(final String tempo){
        Button svbtn = (Button) findViewById(R.id.save);
        svbtn.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View v) {


                                         try {
                                             System.out.println(tempo);
                                             File directory = Environment.getExternalStorageDirectory();
                                             outputFile=getFilename();
                                             File file = new File(outputFile);
                                             // If file does not exists, then create it
                                             if (!file.exists()) {
                                                 file.createNewFile();
                                             }
                                             FileWriter fw = new FileWriter(file. getAbsoluteFile());
                                             BufferedWriter bw = new BufferedWriter(fw);
                                             bw.write(tempo);
                                             bw.close();
                                             Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                                             Log.d("Success", "Success");


                                         } catch (IOException e) {
                                             e.printStackTrace();

                                         }
                                     }
                                 }

        );
    }

//
}
