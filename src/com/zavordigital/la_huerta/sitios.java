package com.zavordigital.la_huerta;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class sitios extends Activity{
	Context context;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_sisitos);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
public void atras(View v) {
		
		finish();
	}


 	public void pagina(View v){
 		
 		String url = "http://lahuerta.com.mx/";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
 	}
 	
 public void pagina_facebook(View v){
 		
 		String url = "https://facebook.com/LaHuertaMx";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
 	}
 
 public void pagina_twitter(View v){
		
		String url = "https://twitter.com/lahuertamx";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
 
 	public void llamar(View v){
 		
 		Intent dial = new Intent();
		dial.setAction("android.intent.action.DIAL");
		dial.setData(Uri.parse("tel:" + "01 800 849 5432"));
		dial.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(dial);
 	}
}
