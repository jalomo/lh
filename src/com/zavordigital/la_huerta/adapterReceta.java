package com.zavordigital.la_huerta;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class adapterReceta extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public adapterReceta(Activity a,ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item_receta, null);
 
        TextView titulox = (TextView)vi.findViewById(R.id.title); 
        TextView descripcion = (TextView)vi.findViewById(R.id.descripcion); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image2); 
        TextView minutos = (TextView)vi.findViewById(R.id.minutos); 
        TextView id_ = (TextView)vi.findViewById(R.id.id_categoria); 
        TextView url_video = (TextView)vi.findViewById(R.id.url_video); 
        
        TextView texto = (TextView)vi.findViewById(R.id.texto_);
        TextView url_ = (TextView)vi.findViewById(R.id.url_);
        
        
        
    
        
        HashMap<String, String> evento = new HashMap<String, String>();
        evento = data.get(position);
 
        
        titulox.setText(evento.get(recetas.TAG_recetaTitulo));
        descripcion.setText(evento.get(recetas.TAG_recetaVotos));
        minutos.setText(evento.get(recetas.TAG_recetaTiempo));
        id_.setText(evento.get(recetas.TAG_recetaId));
        
        url_.setText(evento.get(recetas.TAG_recetaPathImg));
        texto.setText(evento.get(recetas.TAG_receta_text));
        
        
        url_video.setText(evento.get(recetas.TAG_receta_video));
        
       
     
       
        imageLoader.DisplayImage(evento.get(recetas.TAG_recetaPathImg), thumb_image);
        return vi;
    }
}



