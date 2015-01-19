package com.zavordigital.la_huerta;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;


import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TabHost.OnTabChangeListener;

public class recetas_single extends TabActivity implements OnClickListener {
	
	
	private RatingBar ratingBar;
	private TextView ratingValue;
	private Button button;
	
	
	
	String Texto="";
	String url_="";
	String titulo="";
	String tiempo="";
	String votos="";
	String receta_video="";
	static String receta_id="";
	 
	
	private static String url_votar = "http://zavordigital.com/la_huerta/index.php/mobiles/votar_receta/";
	private static String url_comentar="http://zavordigital.com/la_huerta/index.php/mobiles/AddComentByRecipe/";
	
	
	static JSONParser jsonParser = new JSONParser();
	
	
	String voto_total="";
	
	
	

    //declaramos los elementos del XML
    EditText etTitulo, etDescripcion, etCaption, etUrl, etLink;
    Button publicar;
    
    //Guardamos en una constante el ID de nuestra app de Facebook
     private static String ID_FB="787373441273183";
    //creamos un objeto Facebook
     private Facebook facebook;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_receta_single);
		
		
		Intent in = getIntent();
		Texto =in.getStringExtra("receta_text");
		url_ =in.getStringExtra("url_");
		titulo =in.getStringExtra("receta_nombre");
		tiempo =in.getStringExtra("tiempo");
		votos =in.getStringExtra("votos");
		receta_id =in.getStringExtra("recetaid");
		receta_video =in.getStringExtra("receta_video");
		
		//Toast.makeText(this, receta_video, Toast.LENGTH_SHORT).show();
		
		
		TextView texto = (TextView)findViewById(R.id.title); 
		texto.setText(titulo);
		
		TextView votos_ = (TextView)findViewById(R.id.descripcion); 
		votos_.setText(votos);
		
		
       final TabHost tabHost = getTabHost(); // Creamos el tabhost de la actividad    
       TabHost.TabSpec spec; // Creamos un recurso para las propiedades de la pestana
       Intent intent; // Intent que se utilizara para abrir cada pestana
       Resources res = getResources(); //Obtenemos los recursos
       
       //Se crea el intent para abrir la actividad que en realidad es una clase
       intent = new Intent().setClass(this, paso_paso.class);
       spec = tabHost.newTabSpec("Pestaña1").setContent(intent).setIndicator("    Paso a paso    "); 
       intent.putExtra("texto", Texto);
       intent.putExtra("url_", url_);
       intent.putExtra("receta_nombre", titulo);
       intent.putExtra("tiempo", tiempo);
       intent.putExtra("votos", votos);
       intent.putExtra("recetaid", receta_id);
     //intent.putExtra("receta_text", Texto);
       //Se carga la pestana en el contenedor TabHost 
       tabHost.addTab(spec);
       tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#d8dcde")); 
       
       intent = new Intent().setClass(this, ingredientes.class);
       spec = tabHost.newTabSpec("Pestaña2").setContent(intent).setIndicator("    Ingredientes    ");  
       intent.putExtra("recetaid", receta_id);
       tabHost.addTab(spec);
       
       tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#d8dcde")); 

       intent = new Intent().setClass(this, comentarios.class);
       intent.putExtra("receta_id", receta_id);
       spec = tabHost.newTabSpec("Pestaña3").setContent(intent).setIndicator("    Comentarios    ");
       tabHost.addTab(spec);
       tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#d8dcde")); 
       
      
       
       ImageView image = (ImageView) findViewById(R.id.list_image2);
       String image_url = url_;//"http://zavordigital.com/la_huerta/statics//imagenes_categorias//14-02-2014-test1.jpg";
       ImageLoader imgLoader = new ImageLoader(getApplicationContext());
       imgLoader.DisplayImage(image_url, image);
       
       
       
       
       addListenerOnButton() ;
       
       
       publicar = (Button)findViewById(R.id.button3);
       publicar.setOnClickListener(this);
       
       
       String[] separated = votos.split(":");
      
       int d= (int) ((Integer.parseInt(separated[1])*5) /100);
       RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
       rb.setRating(d);
       
       
	}
	
	
	
	/* public void addListenerOnRatingBar() {
		 
			ratingBar = (RatingBar) findViewById(R.id.ratingBar);
			ratingValue = (TextView) findViewById(R.id.txtRatingValue);
		 
			//if rating value is changed,
			//display the current rating value in the result (textview) automatically
			ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				
				public void onRatingChanged(RatingBar ratingBar, float rating,	boolean fromUser) {
		 
					ratingValue.setText(String.valueOf(rating));
		 
				}
			});
		  }
		 */  
		  public void addListenerOnButton() {
		 
			ratingBar = (RatingBar) findViewById(R.id.ratingBar);
			button = (Button) findViewById(R.id.button2);
		 
			//if click on me, then display the current rating value.
			button.setOnClickListener(new OnClickListener() {
		 
				@Override
				public void onClick(View v) {
		 
					//Toast.makeText(recetas_single.this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
					Toast.makeText(recetas_single.this, "voto efectuado con exito", Toast.LENGTH_LONG).show();
					String votar=votar_receta(String.valueOf(ratingBar.getRating()));
					
					TextView votos_ = (TextView)findViewById(R.id.descripcion); 
					votos_.setText("votos:"+votar);
				}
		 
			});
		 
		  }

	public void atras(View v) {
		
		finish();
	}
	
	
	
	
	public String votar_receta(final String valor) {
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					
					String json = jsonParser
							.makeHttpRequest(url_votar+receta_id+'/'+valor, "GET", params);
					
					Log.d("Albums JSON: ", "> " + json);
					
					voto_total=json;
					
				} catch (Exception e) {

				}
			}
		});
		t.start();
		
		return voto_total;
	}
	
	
	public static void comentar_receta(final String comentario){

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("comentario", comentario));
					String json = jsonParser
							.makeHttpRequest(url_comentar+'/'+receta_id, "POST", params);
					
					Log.d("Albums JSON: ", "> " + json);
					
					
					
				} catch (Exception e) {

				}
			}
		});
		t.start();
		
	}
	
	
	
	
	
	
	
	


	@Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()==publicar.getId()){
            //al pulsar sobre el boton publicar invocamos el método publicaMuro()
            publicarMuro(); 
        }       
    }
    
    private void publicarMuro() {
        // TODO Auto-generated method stub
            
        //Creamos un objeto facebook con el id de nuestra app
            facebook = new Facebook(ID_FB);
        //Ejecutamos la sesion de facebook  
            new AsyncFacebookRunner(facebook);
            
        //en función del estado de la sesión hacemos una cosa u otra
            if(!facebook.isSessionValid()) {
                //si la sesion es valida mostramos en pantalla que hemos accedido correctamente y
                //publicamos el mensaje con el método mensaje_en_el_muro()
                facebook.authorize(this, new String[] {}, new DialogListener() {
                    @Override
                    
                    
                    public void onComplete(Bundle values) {
                        
                        Toast mensaje= Toast.makeText(recetas_single.this,"Has iniciado la sesion correctamente.", Toast.LENGTH_SHORT);
                        mensaje.show();
                        //escribimos el mensaje en el muro
                        mensaje_En_El_Muro();
                    }
                  
                    //Si la sesion devuelve un error mostramos un mensaje de error

                    @Override
                    public void onFacebookError(FacebookError error) {
                        
                        Toast mensaje= Toast.makeText(recetas_single.this,"Ha ocurrido un error al intentar iniciar sesión", Toast.LENGTH_SHORT);
                        mensaje.show();
                    }
                    
                    @Override
                    public void onError(DialogError e) {
                        
                        Toast mensaje= Toast.makeText(recetas_single.this,"Ha ocurrido un error al intentar iniciar sesión", Toast.LENGTH_SHORT);
                        mensaje.show();
                    }
                    
                    @Override
                    public void onCancel() {}
                });
            }          
        }
    
    private void mensaje_En_El_Muro() {
        // TODO Auto-generated method stub
        
        Bundle params = new Bundle();
        
        //introducimos dentro del objeto Bundle el texto que introduzcamos en los editText.
        
       
        
        
        params.putString("description", titulo);
        params.putString("name", titulo);
        params.putString("caption",titulo);
        params.putString("picture",url_);
        params.putString("link", "http://lahuerta.com.mx");
        //ejecutamos un dialogo con el objeto Bundle rellenado
        facebook.dialog(recetas_single.this, "feed", params, new SampleDialogListener());
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    public class SampleDialogListener extends BaseDialogListener {

        public void onComplete(Bundle values) {
            Toast mensaje= Toast.makeText(recetas_single.this,"Enviado", Toast.LENGTH_SHORT);
            mensaje.show();
        }
    }
	
	
    
    
    
    

	 @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	 
	        // Checks the orientation of the screen
	        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	           // Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	        	
	        	
	     		if(receta_video.equals("0")){
	     			
	     			//Toast.makeText(recetas_single.this, "da de alta un servidor", Toast.LENGTH_SHORT).show();
	     			String urlvideo="https://www.youtube.com/watch?v=plQaQo5Uoxg";
	     			String[] separated = urlvideo.split("v=");
	     			
	     			//Toast.makeText(recetas_single.this, "da de alta un servidor", Toast.LENGTH_SHORT).show();
	     			
	     			
	     			
	     			 Intent i = new Intent(getApplicationContext(), video_youtube.class);
	 	            i.putExtra("receta_video", separated[1]);
	 				startActivity(i);
	     			
	     			
	     		}else{
	     			
	     			String urlvideo=receta_video;
	     			String[] separated = urlvideo.split("v=");
	     			
	     			
	     			
	     			 Intent i = new Intent(getApplicationContext(), video_youtube.class);
	 	            i.putExtra("receta_video", separated[1]);
	 				startActivity(i);
	     		
	     		}
	        	
	        	
	            
	           
	            
	            
	        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	           // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	        }
	    }
	
	
    
    
}
