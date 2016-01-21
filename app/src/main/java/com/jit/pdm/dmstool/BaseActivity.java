package com.jit.pdm.dmstool;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



public class BaseActivity extends Activity{
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.list_menu, menu);
	    return true;
	}
	
}
