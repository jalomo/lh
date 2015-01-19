package com.zavordigital.la_huerta;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class comentarios extends Activity{
	
	private Button button;
	
	private static adaptadorComentarios adapter;
	private ListView listView;

	static String TAG_comentario="comentario_tx";
	
	private static ProgressDialog progress;
	static ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	
	private static String url_lista_comentarios="http://zavordigital.com/la_huerta/index.php/mobiles/GetAllComentsByRecipe/";

	static JSONParser jsonParser = new JSONParser();
	static JSONArray lista = null;
	static String receta_id;
	EditText comentario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comentarios);
		
		
		 comentario = (EditText) findViewById(R.id.editText1);
		
		Intent in = getIntent();
		receta_id =in.getStringExtra("receta_id");
		
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
	 
			@Override
			public void onClick(View v) {
				
				
				recetas_single.comentar_receta(comentario.getText()+"");
				
				
				
				
				
				progress = new ProgressDialog(comentarios.this);
				progress.setTitle("Descargando");
				progress.setMessage("Espere por favor...");
				progress.setCancelable(false);
				progress.show();
				contactList.clear();
				adapter.notifyDataSetChanged();
				carga_comentarios();
				
				
			}
	 
		});
		
		

		adapter = new adaptadorComentarios(this, contactList);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);
		
		
		progress = new ProgressDialog(this);
		progress.setTitle("Descargando");
		progress.setMessage("Espere por favor...");
		progress.setCancelable(false);
		progress.show();
		contactList.clear();
		adapter.notifyDataSetChanged();
		carga_comentarios();
		
		
	}
	
	
public  void carga_comentarios() {
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					
					String json = jsonParser
							.makeHttpRequest(url_lista_comentarios+'/'+receta_id, "GET", params);
					
					Log.d("Albums JSON: ", "> " + json);
					
					String eventosId = "";
					String eventosTitulo = "";
					
					
					
					lista = new JSONArray(json);
					if (lista != null) {
						
						for (int i = 0; i < lista.length(); i++) {
							JSONObject c = lista.getJSONObject(i);
							
							
								
							
							eventosTitulo = c.getString(TAG_comentario);
							
							
								
								HashMap<String, String> map = new HashMap<String, String>();

								
								map.put(TAG_comentario, eventosTitulo);
								
								

								// adding HashList to ArrayList
								contactList.add(map);
							

							
						}
						
						handler.sendEmptyMessage(0);
					} else {
						
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(0);
				}
				runOnUiThread(new Runnable() {
			        public void run() {
			        	comentario.setText("");
			        }
			    });
			}
		});
		t.start();
	}
	
	
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			adapter.notifyDataSetChanged();
			progress.dismiss();
		}
	};

	
}
