package com.example.myexplorer;

import java.util.List; 
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<Item>
{
		
        private Context cxt;
        private int id;
        private List<Item>items;
       
        public CustomListViewAdapter(Context context, int textViewResourceId,
                List<Item> objects) {
                super(context, textViewResourceId, objects);
                cxt = context;
                id = textViewResourceId;
                items = objects;
        		}
        
        public Item getItem(int i)
         {
                 return items.get(i);
         }
        
       
       public View getView(int position, View convertView, ViewGroup parent)
         {
               View v = convertView;
               if (v == null)
               {
            	   LayoutInflater vi = (LayoutInflater)cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   v = vi.inflate(id, null);
               }
              
                             
               final Item any = items.get(position);
               if (any != null) {
                       TextView t1 = (TextView) v.findViewById(R.id.TextView01);
                       TextView t2 = (TextView) v.findViewById(R.id.TextView02);
                       TextView t3 = (TextView) v.findViewById(R.id.TextViewDate);
                       ImageView image1 = (ImageView) v.findViewById(R.id.fd_Icon1);
                       String uri = "drawable/" + any.getImage();
                       int imageResource = cxt.getResources().getIdentifier(uri, null, cxt.getPackageName());
                       Drawable image = cxt.getResources().getDrawable(imageResource);
                       image1.setImageDrawable(image);
                       if(t1!=null)
                    	   		t1.setText(any.getName());
                       if(t2!=null)
                                t2.setText(any.getData());
                       if(t3!=null)
                                t3.setText(any.getDate());
               }
               return v;
       }
}