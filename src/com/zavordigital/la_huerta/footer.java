package com.zavordigital.la_huerta;

import com.google.android.gcm.GCMRegistrar;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;



public class footer extends TabActivity {
	/** Called when the activity is first created. */
	Context context;
	
	private static final int NOTIFICATION_ID = 664242214;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.footer);
		setTabs() ;
		
		
		cancelNotification();
		registerUser(this);
		muestraNotificacion();
		
		if (verificaConexion(footer.this)==false) {
		    Toast.makeText(getBaseContext(),
		            "Comprueba tu conexión a Internet... ", Toast.LENGTH_LONG)
		            .show();
		    //this.finish();
		}else{
			Toast.makeText(getBaseContext(),
		            "loading... ", Toast.LENGTH_LONG)
		            .show();
			//cargaCategoria();
		}	
		
		
	}
	
	public static boolean verificaConexion(Context ctx) {
	    boolean bConectado = false;
	    ConnectivityManager connec = (ConnectivityManager) ctx
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    // No sólo wifi, también GPRS
	    NetworkInfo[] redes = connec.getAllNetworkInfo();
	    // este bucle debería no ser tan ñapa
	    for (int i = 0; i < 2; i++) {
	        // ¿Tenemos conexión? ponemos a true
	        if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
	            bConectado = true;
	        }
	    }
	    return bConectado;
	}
	private void setTabs()
	{
		//addTab("promociones", R.drawable.tab_promocion, promociones.class);
		//addTab("sincronizar", R.drawable.tab_sincronizar, sincronisar.class);
		
		addTab("Inico", R.drawable.tab_inicio, MainActivity.class);
		//addTab("productos", R.drawable.tab_productos, productos.class);
		
		
		addTab("Favoritas", R.drawable.tab_favoritos, favoritas.class);
		
		addTab("Lista del super", R.drawable.tab_lista, lista_super.class);
		
		
	}
	
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
	
	
	
	

	
	// Funciones push
			private void cancelNotification() {
				String ns = Context.NOTIFICATION_SERVICE;
				NotificationManager nMgr = (NotificationManager) this
						.getSystemService(ns);
				nMgr.cancel(NOTIFICATION_ID);
			}

			private void registerUser(Context context) {
				try {
					GCMRegistrar.checkDevice(this);
					GCMRegistrar.checkManifest(this);
					final String regId = GCMRegistrar.getRegistrationId(context);
					if (regId.equals("")) {
						GCMRegistrar.register(context, "289355169441");
						Log.d("GCM------------------------", "Registrado");
						//Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();
					} else {
						Log.d("GCM----------------------------------------", "Ya registrado");
						//Toast.makeText(getApplicationContext(), "ya registrado", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					//Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
						
				}
			}

			private void muestraNotificacion() {
				SharedPreferences prefe = getSharedPreferences("datos",
						Context.MODE_PRIVATE);
				String msg = prefe.getString("msg", "null");
				if (!msg.equals("null")) {
					SharedPreferences preferencias = getSharedPreferences("datos",
							Context.MODE_PRIVATE);
					Editor editor = preferencias.edit();
					editor.putString("msg", "null");
					editor.commit();
					AlertDialog.Builder alert = new AlertDialog.Builder(this);
					alert.setTitle("la huerta");
					alert.setMessage(msg);
					alert.setIcon(getResources().getDrawable(R.drawable.arrow));
					alert.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
					AlertDialog dialog = alert.create();
					dialog.show();
				}
			}
			// Fin funciones push
}