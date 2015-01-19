package com.zavordigital.la_huerta;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class video_youtube extends YouTubeBaseActivity implements OnInitializedListener {

	
	String receta_video="";
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      setContentView(R.layout.video_youtube);
      
      Intent in = getIntent();
      receta_video =in.getStringExtra("receta_video");
      
      
      if(!isAppInstalled("com.google.android.youtube")) {
          try {
              startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+"com.google.android.youtube")));
          } catch (android.content.ActivityNotFoundException anfe) {
              startActivity(new Intent(Intent.ACTION_VIEW, 
                  Uri.parse("http://play.google.com/store/apps/details?id="+"com.google.android.youtube")));
          }         
      }
      
      
      
      YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
      youTubeView.initialize("AIzaSyDviL7YPTpl7q-5gQ9FReClR3SHW48qUT8", this);
  }

   @Override
    public void onInitializationFailure(Provider provider,
        YouTubeInitializationResult result) {
     Toast.makeText(this, 
           "Error " + result.toString(), 
           Toast.LENGTH_LONG).show();   
    }

   @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player,
        boolean wasRestored) {     
        if (!wasRestored) {
          //player.cueVideo("RcVyl9X3gFo");//LOL Cats
        	player.loadVideo(receta_video);//LOL Cats
            player.setFullscreen(true);
        }
    }
   
   
   private boolean isAppInstalled(String uri) {
	   PackageManager pm = getPackageManager();
	   boolean installed = false;
	    try {
	       pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
	       installed = true;
	    } catch (PackageManager.NameNotFoundException e) {
	           installed = false;
	    }
	      return installed;
	 }
   
   
   
   
   @Override
   public void onConfigurationChanged(Configuration newConfig) {
       super.onConfigurationChanged(newConfig);

       // Checks the orientation of the screen
       if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
           //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
           
           
       } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
          //Toast.makeText(this, "hola mundo", Toast.LENGTH_SHORT).show();
    	   finish();
       }
   }
   
   
   
   
   
}