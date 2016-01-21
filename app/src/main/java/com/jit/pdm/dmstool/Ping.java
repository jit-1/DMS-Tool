package com.jit.pdm.dmstool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Jit on 22-12-2014.
 */
public class Ping extends Activity {

    EditText edit;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ping);
        edit = (EditText)findViewById(R.id.editText1);
      //  edit.setText("192.168.1.1");
        text = (TextView)findViewById(R.id.textView1);
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                Editable host = edit.getText();
                try {
                    String pingCmd = "ping -c 5 " + host;
                    String pingResult = "";
                    Runtime r = Runtime.getRuntime();
                    Process p = r.exec(pingCmd);
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(p.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                        text.setText(inputLine + "\n\n");
                        pingResult += inputLine;
                        text.setText(pingResult);
                    }
                    in.close();
                }//try
                catch (IOException e) {
                    System.out.println(e);
                }
            }});
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
