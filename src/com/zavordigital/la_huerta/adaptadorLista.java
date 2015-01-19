package com.zavordigital.la_huerta;


import java.util.ArrayList;
import java.util.HashMap;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

 
public class adaptadorLista extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public adaptadorLista(Activity a,ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.item_list, null);
 
        TextView titulox = (TextView)vi.findViewById(R.id.title); 
        TextView id_categoria = (TextView)vi.findViewById(R.id.id_categoria); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image1); 
        
    
        
        HashMap<String, String> catego = new HashMap<String, String>();
        catego = data.get(position);
 
        
        titulox.setText(catego.get(buscar.TAG_eventosTitulo));
        id_categoria.setText(catego.get(buscar.TAG_eventosId));
       
        imageLoader.DisplayImage(catego.get(buscar.TAG_eventosPathImg), thumb_image);
        
        
        
        return vi;
    }
}



