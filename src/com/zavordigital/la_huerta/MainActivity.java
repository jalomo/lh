package com.zavordigital.la_huerta;


import com.google.android.gcm.GCMRegistrar;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_inicio);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		Button button_recetas = (Button) findViewById(R.id.recetas);
		 
		button_recetas.setOnClickListener(new View.OnClickListener() {
			@Override
				public void onClick(View view) {
				
						// Launching News Feed Screen
						Intent i = new Intent(getApplicationContext(), buscar.class);
						//i.putExtra("url", url);
						//i.putExtra("categoria", 1);
						startActivity(i);
					
				}
			});  
		
		Button button_sitios = (Button) findViewById(R.id.sitios);
		 
		button_sitios.setOnClickListener(new View.OnClickListener() {
			@Override
				public void onClick(View view) {
				
						// Launching News Feed Screen
						Intent i = new Intent(getApplicationContext(), sitios.class);
						//i.putExtra("url", url);
						//i.putExtra("categoria", 1);
						startActivity(i);
					
				}
			});  
		
		Button button_instrucciones = (Button) findViewById(R.id.instrucciones);
		 
		button_instrucciones.setOnClickListener(new View.OnClickListener() {
			@Override
				public void onClick(View view) {
				
						// Launching News Feed Screen
						Intent i = new Intent(getApplicationContext(), instrucciones.class);
						//i.putExtra("url", url);
						//i.putExtra("categoria", 1);
						startActivity(i);
					
				}
			});  
	}

	
	

}
