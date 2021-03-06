package com.example.myexplorer;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.myexplorer.CustomListViewAdapter;
import com.example.myexplorer.R;
import com.example.myexplorer.Item;;
	public class MainActivity extends ListActivity {
 	ListView lv;	
 	private List<Item> dr = null;
 	private List<Item> fl = null;
 	private String root;
 	private TextView location;
	private String currentPath;
 	private CustomListViewAdapter adapter=null;
 	public long l1,l2,l3;
 	public static long dirSize(File dir) {

 		if (dir.exists()) {
	        long result = 0;
	        File[] fileList1 = dir.listFiles();
	        for(int i = 0; i < fileList1.length; i++) {
	           	    if(fileList1[i].isDirectory()) {
	                result += dirSize(fileList1 [i]);
	           	    }
	           	    else {
	                result += fileList1[i].length();
	           	    }
	        }
	        return result;
	    }
 		return 0;
	}

 Comparator<? super File> comparator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = (TextView)findViewById(R.id.path);
        lv = (ListView)findViewById(android.R.id.list);
        comparator = filecomparatorByAlphabetically;
        root = Environment.getExternalStorageDirectory().getPath();
        getDir(root);
        Button btnAlphabetically = (Button)findViewById(R.id.button3);
        btnAlphabetically.setOnClickListener(new OnClickListener(){

		
        public void onClick(View arg0) {
		    comparator = filecomparatorByAlphabetically;
		    getDir(currentPath);
        }
		});
		        
        Button btnLastDateModified = (Button)findViewById(R.id.button2);
        btnLastDateModified.setOnClickListener(new OnClickListener(){

		   @Override
		public void onClick(View arg0) {
		   comparator = filecomparatorByLastModified;
		   getDir(currentPath);
		    
		}
		});
        
        Button btnSize = (Button)findViewById(R.id.button4);
        btnSize.setOnClickListener(new OnClickListener() {
			
			@Override
		public void onClick(View arg0) {
				// TODO Auto-generated method stub
			comparator = filecomparatorBySize;
			getDir(currentPath);
				
		}
		});
        
        Button btnFormat = (Button)findViewById(R.id.button1);
        btnFormat.setOnClickListener(new OnClickListener() {
			
			@Override
		public void onClick(View arg0) {
				// TODO Auto-generated method stub
			comparator = filecomparatorByFormat;
			getDir(currentPath);
		}
		});
    }
    
    private void getDir(String dirPath)
    {
    	currentPath = dirPath;
     	location.setText("Location: " + dirPath);
    	 dr = new ArrayList<Item>();
    	fl = new ArrayList<Item>();
    	File f = new File(dirPath);
     	File[] files = f.listFiles();
     
     	if(!dirPath.equals(root))
     	{
    	 	dr.add(0,new Item("..","Parent Directory","",f.getParent(),"directory_up"));
     	}
     
     	Arrays.sort(files, comparator);
  
     	for(File ff: files)
     	{
    	Date lastModDate = new Date(ff.lastModified());
     	DateFormat formater = DateFormat.getDateTimeInstance();
     	String date_modify = formater.format(lastModDate);
                   
      		if(!ff.isHidden() && ff.canRead()){
     
      			if(ff.isDirectory()){
      				l1=dirSize(ff)/1024;
      				String num_item = String.valueOf(l1);
       				dr.add(new Item(ff.getName(),num_item,date_modify,ff.getAbsolutePath(),"directory_icon"));
      			}
          	else
        	  fl.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"file_icon"));
        		  
        	         
      		} 
     	}
     	dr.addAll(fl);
    	adapter=new CustomListViewAdapter(this, R.layout.item_row, dr);
    	lv.setAdapter(adapter );
    }
    
  
    
 Comparator<? super File> filecomparatorByLastModified = new Comparator<File>()
		 {  
	 		public int compare(File file1, File file2) 
	 		{	
	 			if(file1.isDirectory())
	 				{
	 					if (file2.isDirectory())
	 						{
	 						return Long.valueOf(file1.lastModified()).compareTo(file2.lastModified());
	 						}
	 					else
	 						{
	 						return -1;
	 						}
	 				}
	 			else 
	 				{
	 					if (file2.isDirectory())
	 						{
	 						return 1;
	 						}
	 					else
	 						{
	 						return Long.valueOf(file1.lastModified()).compareTo(file2.lastModified());
	 						}
	 				}
	    
	 		}  
		 };

 Comparator<? super File> filecomparatorByFormat = new Comparator<File>()
		 {
	  
	 		public int compare(File file1, File file2) 
	 			{
		 
	 				if(file1.isDirectory())
	 					{
	 						if (file2.isDirectory())
	 							{
	 							return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
	 							}
	 						else
	 							{
	 							return -1;
	 							}
	 					}
	 				else 
	 					{
	 						if (file2.isDirectory())
	 							{
	 							return 1;
	 							}
	 						else
	 							{
	 								String file1Array[] = file1.getName().split("\\.");
	 								String file2Array[] = file2.getName().split("\\.");
	 								String etn1 = file1Array[file1Array.length-1];
	 								String etn2 = file2Array[file2Array.length-1];
	 								return String.valueOf(etn1.toLowerCase()).compareTo(etn2.toLowerCase());
	 							}
	 					}
 
	 			}  
		 };

 Comparator<? super File> filecomparatorByAlphabetically = new Comparator<File>()
		 {
  
	 		public int compare(File file1, File file2) 
	 			{
	
	 				if(file1.isDirectory())
	 					{
	 						if (file2.isDirectory())
	 							{
	 								return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
	 							}
	 						else
	 							{
	 								return -1;
	 							}
	 					}
	 				else 
	 					{
	 						if (file2.isDirectory())
	 							{
	 								return 1;
	 							}
	 						else
	 							{
	 								return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
	 							}
	 					}
		    
	 			}  
 	};
 
 Comparator<? super File> filecomparatorBySize = new Comparator<File>()
	{	  
			public int compare(File file1, File file2) 
				{			 
					if(file1.isDirectory())
						{
							if (file2.isDirectory())
								{	 
									l2=file1.getUsableSpace();
									l3=file2.getUsableSpace();
									return Long.valueOf(l2).compareTo(l3);
								}
							else
								{
								return -1;
								}
						}
					else 
						{
							if (file2.isDirectory())
								{
								return 1;
								}
							else
								{
								return Long.valueOf(file1.length()).compareTo(file2.length());
								}
						}
		 
				}  
	};
 
 @Override
 protected void onListItemClick(ListView l, View v, int position, long id) {
  // TODO Auto-generated method stub
	 super.onListItemClick(l, v, position, id);
     Item o = adapter.getItem(position);
     File  file =new File(o.getPath());
  
  if (file.isDirectory())
  	{
	  if(file.canRead())
	  	{
		  getDir(o.getPath());
	  	}
	  else
	  	{
		  new AlertDialog.Builder(this)
		  .setTitle(file.getName() + " folder can't be read!")
		  .setPositiveButton("OK", null).show(); 
	  	} 
  	}
  else 
  	{
	  new AlertDialog.Builder(this)	
	  .setTitle(file.getName() + "  " + file.length()/1024 + " KB")
	  .setPositiveButton("OK", null).show();
  	}
 }


}