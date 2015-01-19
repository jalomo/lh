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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class buscar extends Activity {
	

	private static String url = "http://zavordigital.com/la_huerta/index.php/mobiles/GetAllCategories";
	
	
	JSONParser jsonParser = new JSONParser();
	JSONArray lista = null;
	private ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	
	private static ProgressDialog progress;
	
	static final String TAG_eventosId = "categoriaid";
	static final String TAG_eventosTitulo = "categoria_nombre";
	static final String TAG_eventosPathImg = "categoria_img";
	
	private ListView listView;
	
	private static adaptadorLista adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_buscar);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		

		adapter = new adaptadorLista(this, contactList);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String id_categoria = ((TextView) view.findViewById(R.id.id_categoria)).getText().toString();
					String nombre_categoria = ((TextView) view.findViewById(R.id.title)).getText().toString();
					
					 					
					Intent in = new Intent(getApplicationContext(), recetas.class);
					in.putExtra(TAG_eventosId, id_categoria);
					in.putExtra("nombre_", nombre_categoria);
					startActivity(in);
					//Toast.makeText(LayerStack.this, tiendaid, Toast.LENGTH_SHORT).show();

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
							.makeHttpRequest(url, "GET", params);
					
					Log.d("Albums JSON: ", "> " + json);
					
					String eventosId = "";
					String eventosTitulo = "";
					String eventosPathImg = "";
					
					
					lista = new JSONArray(json);
					if (lista != null) {
						
						for (int i = 0; i < lista.length(); i++) {
							JSONObject c = lista.getJSONObject(i);
							
							
								
							eventosId = c.getString(TAG_eventosId);
							eventosTitulo = c.getString(TAG_eventosTitulo);
							eventosPathImg = c.getString(TAG_eventosPathImg);
							
								
								HashMap<String, String> map = new HashMap<String, String>();

								map.put(TAG_eventosId, eventosId);
								map.put(TAG_eventosTitulo, eventosTitulo);
								map.put(TAG_eventosPathImg,"http://zavordigital.com/la_huerta/"+ eventosPathImg);
								

								// adding HashList to ArrayList
								contactList.add(map);
							

							
						}
						
						handler.sendEmptyMessage(0);
					} else {
						
					}
				} catch (Exception e) {

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
