package com.zavordigital.la_huerta;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class paso_paso extends Activity{
	
	String Texto="";
	String url_="";
	String titulo="";
	String tiempo="";
	String votos="";
	static String receta_id="";
	private Button buttonf;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paso_paso);
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Intent in = getIntent();
		Texto =in.getStringExtra("texto");
		
		url_ =in.getStringExtra("url_");
		titulo =in.getStringExtra("receta_nombre");
		tiempo =in.getStringExtra("tiempo");
		votos =in.getStringExtra("votos");
		receta_id =in.getStringExtra("recetaid");
		
		 TextView texto = (TextView)findViewById(R.id.title); 
		texto.setText(""+Html.fromHtml(Texto));
		
		addfavoritos();
		
		
		 SQLiteHelper usdbh =
	                new SQLiteHelper(this, "DBUsuarios", null, 1);
	     
	        db = usdbh.getWritableDatabase();
	}
	
	 public void addfavoritos() {
		 
			
			buttonf = (Button) findViewById(R.id.button1);
		 
			//if click on me, then display the current rating value.
			buttonf.setOnClickListener(new OnClickListener() {
		 
				@Override
				public void onClick(View v) {
		 
					//Toast.makeText(recetas_single.this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
					//Toast.makeText(paso_paso.this, "se agregor a favoritos", Toast.LENGTH_LONG).show();
					
					
					 ContentValues nuevoRegistro = new ContentValues();
						nuevoRegistro.put("url_", url_);
						nuevoRegistro.put("receta_nombre", titulo);
						nuevoRegistro.put("tiempo", tiempo);
						nuevoRegistro.put("votos", votos);
						nuevoRegistro.put("recetaid", receta_id);
						nuevoRegistro.put("receta_text", Texto);
						
						 db.insert("favoritos", null, nuevoRegistro);
						 Toast.makeText(paso_paso.this, "se agrego a favoritos", Toast.LENGTH_LONG).show();
						
						
						/*Cursor c = db.rawQuery("SELECT recetaid FROM favoritos where recetaid="+receta_id, null);
						 String Pro="0";
						 if (c.moveToFirst()) {
								
							     //Recorremos el cursor hasta que no haya más registros
							     do {
							    	// Pro= c.getString(0);
							         
							    	 Pro="1";
							          
							     } while(c.moveToNext());
							     
							    
							}
						 
						
						 
						 if(Pro.equals("0")){*/
							 
							// db.insert("favoritos", null, nuevoRegistro);
							// Toast.makeText(paso_paso.this, "se agrego a favoritos", Toast.LENGTH_LONG).show();
							 /*}else{
						
							 Toast.makeText(paso_paso.this, "error", Toast.LENGTH_LONG).show();
						 }
						 
						 Pro="0";
						 
					*/
					
					
					
				}
		 
			});
		 
		  }
	 
	/* @Override
		protected void onDestroy() {
		    super.onDestroy();
		    db.close();
		}*/

}
