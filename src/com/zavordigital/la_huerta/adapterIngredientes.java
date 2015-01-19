package com.zavordigital.la_huerta;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class adapterIngredientes extends BaseAdapter {
	 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public adapterIngredientes(Activity a,ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.item_ingredientes, null);
 
        TextView titulox = (TextView)vi.findViewById(R.id.title); 
        TextView id_ = (TextView)vi.findViewById(R.id.id_); 
        
        
    
        
        HashMap<String, String> catego = new HashMap<String, String>();
        catego = data.get(position);
 
        
        titulox.setText(catego.get(ingredientes.TAG_recetaTitulo));
        id_.setText(catego.get(ingredientes.TAG_recetaId));
       
       
        
        
        
        return vi;
    }
}


