package com.zavordigital.la_huerta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class recetas extends Activity {
	

	private static String url = "http://zavordigital.com/la_huerta/index.php/mobiles/GetAllRecipesByCategory/";
	
	
	JSONParser jsonParser = new JSONParser();
	JSONArray lista = null;
	private ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	
	private static ProgressDialog progress;
	
	static final String TAG_recetaId = "recetaid";
	static final String TAG_recetaTitulo = "receta_nombre";
	static final String TAG_recetaPathImg = "receta_img";
	static final String TAG_recetaTiempo = "receta_tiempo";
	static final String TAG_recetaVotos = "receta_votos";
	static final String TAG_receta_text = "receta_text";
	static final String TAG_receta_video="receta_video";
	
	private ListView listView;
	
	private static adapterReceta adapter;
	
	public static String id_categoria="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_receta);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		

		Intent in = getIntent();
		id_categoria =in.getStringExtra("categoriaid");
		String nombre_ =in.getStringExtra("nombre_");
		
		 TextView tipo = (TextView)findViewById(R.id.tipo); 
		 tipo.setText(nombre_);
		
		adapter = new adapterReceta(this, contactList);
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
					String video_url = ((TextView) view.findViewById(R.id.url_video)).getText().toString();
					
					 					
					Intent in = new Intent(getApplicationContext(), recetas_single.class);
					in.putExtra(TAG_recetaId, id_categoria);
					in.putExtra(TAG_recetaTitulo, nombre);
					in.putExtra(TAG_receta_text, texto);
					in.putExtra("url_", url_);
					in.putExtra("tiempo", tiempo);
					in.putExtra("votos", votos_);
					in.putExtra(TAG_receta_video, video_url);
					startActivity(in);
					//Toast.makeText(recetas.this, video_url, Toast.LENGTH_SHORT).show();

				}
			});
		
		
		progress = new ProgressDialog(this);
		progress.setTitle("Descargando");
		progress.setMessage("Espere por favor...");
		progress.setCancelable(false);
		progress.show();
		contactList.clear();
		adapter.notifyDataSetChanged();
		cargaLista();
	}
	
	public void atras(View v) {
		
		finish();
	}
	public void cargaLista() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					
					String json = jsonParser
							.makeHttpRequest(url+id_categoria, "GET", params);
					
					Log.d("Albums JSON: ", "> " + json);
					
					String eventosId = "";
					String eventosTitulo = "";
					String eventosPathImg = "";
					String recetaTiempo = "";
					String recetaVotos = "";
					String receta_texto = "";
					String receta_video = "";
					
					
					lista = new JSONArray(json);
					if (lista != null) {
						
						for (int i = 0; i < lista.length(); i++) {
							JSONObject c = lista.getJSONObject(i);
							
							
								
							eventosId = c.getString(TAG_recetaId);
							eventosTitulo = c.getString(TAG_recetaTitulo);
							eventosPathImg = c.getString(TAG_recetaPathImg);
							recetaTiempo = c.getString(TAG_recetaTiempo);
							recetaVotos = c.getString(TAG_recetaVotos);
							receta_texto = c.getString(TAG_receta_text);
							receta_video = c.getString(TAG_receta_video);
							
								
								HashMap<String, String> map = new HashMap<String, String>();

								map.put(TAG_recetaId, eventosId);
								map.put(TAG_recetaTitulo, eventosTitulo);
								map.put(TAG_recetaPathImg,"http://zavordigital.com/la_huerta/statics/imagenes_recetas/"+ eventosPathImg);
								map.put(TAG_recetaTiempo, recetaTiempo);
								map.put(TAG_recetaVotos, "votos:"+recetaVotos);
								map.put(TAG_receta_text, receta_texto);
								map.put(TAG_receta_video, receta_video);
								

								// adding HashList to ArrayList
								contactList.add(map);
							

							
						}
						
						handler.sendEmptyMessage(0);
					} else {
						handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(0);
				}
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
