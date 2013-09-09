package nl.avans.IN13SAh.sudoku;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SudokuGrid extends Activity
{
   GridView MyGrid;
   
   @Override
   public void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      /*Here we setContentView() to main.xml, get the GridView and then fill it with the
                   ImageAdapter class that extend from BaseAdapter */

      MyGrid = (GridView)findViewById(R.id.MyGrid);
      MyGrid.setAdapter(new ImageAdapter(this));
   }
   
   public class ImageAdapter extends BaseAdapter
   {
      Context MyContext;
      
      public ImageAdapter(Context _MyContext)
      {
         MyContext = _MyContext;
      }
      
      @Override
      public int getCount()
      {
                        /* Set the number of element we want on the grid */
         return 81;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent)
      {
         View MyView = convertView;
         
         if ( convertView == null )
         {
            /*we define the view that will display on the grid*/
            
            //Inflate the layout
            LayoutInflater li = getLayoutInflater();
            MyView = li.inflate(R.layout.grid_item, null);
            
            // Add The Text!!!
            TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
            tv.setText("" + position);
            
            MyView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TextView tv = (TextView) v.findViewById(R.id.grid_item_text);
					Toast.makeText(SudokuGrid.this, tv.getText(), Toast.LENGTH_SHORT).show();
				}
			});
         }
         
         return MyView;
      }

      @Override
      public Object getItem(int arg0) {
         // TODO Auto-generated method stub
         return null;
      }

      @Override
      public long getItemId(int arg0) {
         // TODO Auto-generated method stub
         return 0;
      }
   }
}