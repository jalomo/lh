package com.zavordigital.la_huerta;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService {

	// private static final int FM_NOTIFICATION_ID = 7551566;
	private static final String URL_SERVER = "http://www.zavordigital.com/la_huerta/index.php/mobiles/RegisterUser_V1";
	private static final int NOTIFICATION_ID = 664242214;

	// private Context context;

	public GCMIntentService() {
		// super("AIzaSyD81EnhgTgSOVE6mMBhQ8z7t2OXo03i3N4");
		super("AIzaSyBvWrtZwThFIbdCUeNwh_HFVngnhEKoTho");
	}

	@Override
	protected void onError(Context context, String errorId) {
		Log.d("GCM --*", "Error: " + errorId);

	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		 String msg = intent.getExtras().getString("message");
		
		createNotification(msg, context);
	}

	private void registrarUsuario(String username, String regId) {
		Log.d("GCM------->", "registrarUsuario");
		Log.d("GCM<<<<<<<<", "username: " + username);
		Log.d("GCM>>>>>>>>>>>", "regID: " + regId);
		try {
			SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
	        String codigo = prefe.getString("codigo","-1");
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("device", "ANDROID"));
			nameValuePairs.add(new BasicNameValuePair("token", regId));

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(URL_SERVER );
			if (nameValuePairs != null)
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String res = httpclient.execute(httppost, responseHandler);
			SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
	        Editor editor=preferencias.edit();
	        editor.putString("codigo", res);
	        editor.commit();
			Log.d("GCM---------------------------------", "RES: " + res);
			Toast.makeText(getApplicationContext(),res, Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Log.w("GCM", "ex: " + e.getMessage().toString());
			//Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.d("GCM", "onRegistered: Registrado OK.");
		// En este punto debeis obtener el usuario donde lo tengais guardado.
		// Si no teneis un sistema de login y los usuarios son an�nimos podeis
		// simplemente almacenar el regId
		registrarUsuario(regId, regId);
		
		Log.d("GCM---------------------------------", "onRegistered: " + regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d("GCM", "onUnregistered: Desregistrado OK.");
	}

	private void createNotification(String msg, Context context) {
		SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor=preferencias.edit();
        editor.putString("msg", msg);
        editor.commit();
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.estrella1)
                .setContentTitle("la huerta")
                .setSound(uri)
                .setContentText(msg);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, footer.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(footer.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}
