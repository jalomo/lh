package com.zavordigital.la_huerta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class ingredientes extends Activity {
	

	private static String url = "http://zavordigital.com/la_huerta/index.php/mobiles/GetAllIngredientsByRecipe/";
	
	
	JSONParser jsonParser = new JSONParser();
	JSONArray lista = null;
	private ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	
	private static ProgressDialog progress;
	
	static final String TAG_recetaId = "ingredienteid";
	static final String TAG_recetaTitulo = "ingrediente_nombre";
	
	
	private ListView listView;
	
	private static adapterIngredientes adapter;
	
	private SQLiteDatabase db;
	
	static String receta_id="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingredientes);
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		Intent in = getIntent();
		receta_id =in.getStringExtra("recetaid");
		
		//Toast.makeText(ingredientes.this, receta_id, Toast.LENGTH_SHORT).show();
		
		 SQLiteHelper usdbh =
	                new SQLiteHelper(this, "DBUsuarios", null, 1);
	     
	        db = usdbh.getWritableDatabase();
		
		adapter = new adapterIngredientes(this, contactList);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String ingre = ((TextView) view.findViewById(R.id.title)).getText().toString();
					
					showPopUpPhone(ingre) ;				
					

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
	
	
private void showPopUpPhone(final String ingrediente) { 
   	 
   	
   	    
   	    // (3) create a new String using the date format we want
   	   
    	
   	 
   		 LayoutInflater factory = LayoutInflater.from(this);

   		//text_entry is an Layout XML file containing two text field to display in alert dialog
   		final View textEntryView = factory.inflate(R.layout.dialog_ingrediente, null);

   		
   		final EditText cantidad = (EditText) textEntryView.findViewById(R.id.id_cantidad);


   		
   		cantidad.setText("", TextView.BufferType.EDITABLE);
   		
   		cantidad.requestFocus();
   		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

   		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
   		alert.setIcon(R.drawable.ic_launcher).setTitle("").setView(textEntryView).setPositiveButton("Aceptar",
   		  new DialogInterface.OnClickListener() {
   		   public void onClick(DialogInterface dialog,
   		     int whichButton) {
   			   	
   			
   			   String cantidad1 = cantidad.getText().toString().trim();
   			 ContentValues nuevoRegistro = new ContentValues();
				nuevoRegistro.put("texto", ingrediente);
				
				
				 db.insert("ingredientes", null, nuevoRegistro);
				 
				  
   			  
   		   }
   		  }).setNegativeButton("Cancelar",
   		  new DialogInterface.OnClickListener() {
   		   public void onClick(DialogInterface dialog,
   		     int whichButton) {
   		     /*
   		     * User clicked cancel so do some stuff
   		     */
   		   }
   		  });
   		alert.show();
   		 
   		
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
							.makeHttpRequest(url+"/"+receta_id, "GET", params);
					
					Log.d("JSON INGREDIENTES: ", "> " + json);
					
					String eventosId = "";
					String eventosTitulo = "";
					
					
					
					lista = new JSONArray(json);
					if (lista != null) {
						
						for (int i = 0; i < lista.length(); i++) {
							JSONObject c = lista.getJSONObject(i);
							
							
								
							eventosId = c.getString(TAG_recetaId);
							eventosTitulo = c.getString(TAG_recetaTitulo);
							
							
								
								HashMap<String, String> map = new HashMap<String, String>();

								map.put(TAG_recetaId, eventosId);
								map.put(TAG_recetaTitulo, eventosTitulo);
								
								

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

