package com.zavordigital.la_huerta;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class favoritas extends Activity{
	
	String Texto="";
	String url_="";
	String titulo="";
	String tiempo="";
	String votos="";
	static String receta_id="";
	private Button buttonf;
	private SQLiteDatabase db;
	private static ProgressDialog progress;
	private ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	private ListView listView;
	private static adapterFavoritas adapter;
	
	
	
	static final String TAG_recetaId = "recetaid";
	static final String TAG_recetaTitulo = "receta_nombre";
	static final String TAG_recetaPathImg = "url_";
	static final String TAG_recetaTiempo = "tiempo";
	static final String TAG_recetaVotos = "votos";
	static final String TAG_receta_text = "receta_text";
	
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favoritas);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Intent in = getIntent();
		Texto =in.getStringExtra("texto");
		
		url_ =in.getStringExtra("url_");
		titulo =in.getStringExtra("receta_nombre");
		tiempo =in.getStringExtra("tiempo");
		votos =in.getStringExtra("votos");
		receta_id =in.getStringExtra("recetaid");
		
		
		
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
	 
			@Override
			public void onClick(View v) {
				
				
				db.delete("favoritos", null, null);
				carga_contenido();
				
			}
	 
		});
		
		
		
		 SQLiteHelper usdbh =
	                new SQLiteHelper(this, "DBUsuarios", null, 1);
	     
	        db = usdbh.getWritableDatabase();
	        
	        adapter = new adapterFavoritas(this, contactList);
			listView = (ListView) findViewById(R.id.list);
			listView.setAdapter(adapter);
			
			
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String id_categoria = ((TextView) view.findViewById(R.id.id_categoria)).getText().toString();
					String nombre = ((TextView) view.findViewById(R.id.title)).getText().toString();
					String texto = ((TextView) view.findViewById(R.id.texto_)).getText().toString();
					String url_ = ((TextView) view.findViewById(R.id.url_)).getText().toString();
					String tiempo = ((TextView) view.findViewById(R.id.minutos)).getText().toString();
					String votos_ = ((TextView) view.findViewById(R.id.descripcion)).getText().toString();
					
					 					
					Intent in = new Intent(getApplicationContext(), recetas_single.class);
					in.putExtra(TAG_recetaId, id_categoria);
					in.putExtra(TAG_recetaTitulo, nombre);
					in.putExtra(TAG_receta_text, texto);
					in.putExtra("url_", url_);
					in.putExtra("tiempo", tiempo);
					in.putExtra("votos", votos_);
					startActivity(in);
					//Toast.makeText(LayerStack.this, tiendaid, Toast.LENGTH_SHORT).show();

				}
			});
	        
			 
	        
	}
	@Override
	protected void onResume()
	{
	   super.onResume();

	   carga_contenido(); 
	}
	
	public void carga_contenido(){
		progress = new ProgressDialog(this);
		progress.setTitle("Descargando");
		progress.setMessage("Espere por favor...");
		progress.setCancelable(false);
		progress.show();
		contactList.clear();
		adapter.notifyDataSetChanged();
		consultaFavoritos();   
		
	}
	
	 public void consultaFavoritos(){
	    	

			//Alternativa 1: método rawQuery()
			Cursor c = db.rawQuery("SELECT id,receta_text,url_,receta_nombre,tiempo,votos,recetaid FROM favoritos", null);
						
			//Alternativa 2: método delete()		 
			//String[] campos = new String[] {"codigo", "nombre"};
			//Cursor c = db.query("Usuarios", campos, null, null, null, null, null);
			
			//Recorremos los resultados para mostrarlos en pantalla
			//txtResultado.setText("");
			
			
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			          String id = c.getString(0);
			          String receta_text = c.getString(1);
			          String url_ = c.getString(2);
			          String receta_nombre = c.getString(3);
			          String tiempo = c.getString(4);
			          String votos = c.getString(5);
			          String recetaid = c.getString(6);
			          
			         // txtResultado.append(" " + cod + " - " + nom + "\n");
			        //  Log.d("bd------------------------", receta_text);
			          HashMap<String, String> map = new HashMap<String, String>();

						map.put("id", id);
						map.put("receta_text", receta_text+" ");
						map.put("url_", url_);
						map.put("receta_nombre", receta_nombre);
						map.put("tiempo", tiempo);
						map.put("votos", votos);
						map.put("recetaid", recetaid);
						//map.put(TAG_direccion,direccion);
						
						

						// adding HashList to ArrayList
						contactList.add(map);
			          
			          
			     } while(c.moveToNext());
			 	handler.sendEmptyMessage(0);
			} else {
				Log.d("TIENDAS: ", "null");
				handler.sendEmptyMessage(0);
			}
	    }
	
	 private static Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				adapter.notifyDataSetChanged();
				progress.dismiss();
			}
		};
	
	
	/* @Override
		protected void onDestroy() {
		    super.onDestroy();
		    db.close();
		}*/

}