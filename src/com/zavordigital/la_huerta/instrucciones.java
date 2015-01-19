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

public class instrucciones extends Activity {
	

	private static String url = "http://zavordigital.com/la_huerta/index.php/mobiles/GetAllInstruccions";
	
	
	JSONParser jsonParser = new JSONParser();
	JSONArray lista = null;
	private ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	
	private static ProgressDialog progress;
	
	static final String TAG_recetaId = "instruccionid";
	static final String TAG_recetaTitulo = "instruccion_nombre";
	static final String Tag_instruccion_descrip="instruccion_descrip";
	
	private ListView listView;
	
	private static adapterInstrucciones adapter;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_instrucciones);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		adapter = new adapterInstrucciones(this, contactList);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String description = ((TextView) view.findViewById(R.id.descripcion)).getText().toString();
					
					 					
					Intent in = new Intent(getApplicationContext(), single_instruccion.class);
					in.putExtra(Tag_instruccion_descrip, description);
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
					String texto_uso="";
					
					
					lista = new JSONArray(json);
					if (lista != null) {
						
						for (int i = 0; i < lista.length(); i++) {
							JSONObject c = lista.getJSONObject(i);
							
							
								
							eventosId = c.getString(TAG_recetaId);
							eventosTitulo = c.getString(TAG_recetaTitulo);
							texto_uso=c.getString(Tag_instruccion_descrip);
							
								
								HashMap<String, String> map = new HashMap<String, String>();

								map.put(TAG_recetaId, eventosId);
								map.put(TAG_recetaTitulo, eventosTitulo);
								map.put(Tag_instruccion_descrip, texto_uso);
								
								

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
