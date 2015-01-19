package com.zavordigital.la_huerta;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class single_instruccion extends Activity {
	
	public static final String TAG_DESCRIPCION = "instruccion_descrip";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_instruccion);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent in = getIntent();
      
        String description = in.getStringExtra(TAG_DESCRIPCION);
      
		
		TextView Pdescripcion = (TextView) findViewById(R.id.descripcion);
		Pdescripcion.setText(""+Html.fromHtml(description));
		
		
		
		
	}
	
public void atras(View v) {
		
		finish();
	}
}
 