package com.zavordigital.la_huerta;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class lista_super extends Activity{
	
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
	private static adapterLista adapter;
	
	private Button button;
	private Button borra_lista;
	
	static final String TAG_texto = "texto";
	
	static boolean aux=true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_super);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
	 
			@Override
			public void onClick(View v) {
				
				
				showPopUpPhone();
				
			}
	 
		});
		
		borra_lista = (Button) findViewById(R.id.button2);
		borra_lista.setOnClickListener(new OnClickListener() {
	 
			@Override
			public void onClick(View v) {
				
				
				db.delete("ingredientes", null, null);
				carga_contenido();
				
			}
	 
		});
		
		
		
		
		 SQLiteHelper usdbh =
	                new SQLiteHelper(this, "DBUsuarios", null, 1);
	     
	        db = usdbh.getWritableDatabase();
	        
	        adapter = new adapterLista(this, contactList);
			listView = (ListView) findViewById(R.id.list);
			listView.setAdapter(adapter);
	        
			
			//if(aux==true){
				//carga_contenido(); 
				
			//}
			// carga_contenido(); 
			 
			
	        
	}
	
	@Override
	protected void onResume()
	{
	   super.onResume();

	   carga_contenido(); 
	}
	 
	
	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub

        //You can add your own method to refresh data within the tab                 //(Ex:  refreshData())
	
	super.onWindowFocusChanged(hasFocus);
	 //if (hasFocus)
		// carga_contenido(); 
	 
	
	}
	
	
private void showPopUpPhone() { 
   	 
   	
   	    
   	    // (3) create a new String using the date format we want
   	   
    	
   	 
   		 LayoutInflater factory = LayoutInflater.from(this);

   		//text_entry is an Layout XML file containing two text field to display in alert dialog
   		final View textEntryView = factory.inflate(R.layout.dialog_layou, null);

   		
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
				nuevoRegistro.put("texto", cantidad1);
				
				
				 db.insert("ingredientes", null, nuevoRegistro);
				 
				    progress = new ProgressDialog(lista_super.this);
					progress.setTitle("Descargando");
					progress.setMessage("Espere por favor...");
					progress.setCancelable(false);
					progress.show();
					contactList.clear();
					adapter.notifyDataSetChanged();
					consultaFavoritos();  
					
   			  
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
    
	
	
	
	public void carga_contenido(){
		
		
		progress = new ProgressDialog(lista_super.this);
		progress.setTitle("Descargando");
		progress.setMessage("Espere por favor...");
		progress.setCancelable(false);
		progress.show();
		contactList.clear();
		adapter.notifyDataSetChanged();
		consultaFavoritos();   
		
		
		aux=false;
		
	}
	
	 public void consultaFavoritos(){
	    	

			//Alternativa 1: método rawQuery()
			Cursor c = db.rawQuery("SELECT texto FROM ingredientes", null);
						
			//Alternativa 2: método delete()		 
			//String[] campos = new String[] {"codigo", "nombre"};
			//Cursor c = db.query("Usuarios", campos, null, null, null, null, null);
			
			//Recorremos los resultados para mostrarlos en pantalla
			//txtResultado.setText("");
			
			
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			          String id = c.getString(0);
			          
			          
			         // txtResultado.append(" " + cod + " - " + nom + "\n");
			        //  Log.d("bd------------------------", receta_text);
			          HashMap<String, String> map = new HashMap<String, String>();

						map.put("texto", id);
					
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