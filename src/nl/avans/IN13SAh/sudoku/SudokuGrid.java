package nl.avans.IN13SAh.sudoku;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class SudokuGrid. THis class contains logic and handlers for the playing
 * field screen.
 */
public class SudokuGrid extends Activity {

	GridView MyGrid; // The gridview object that will be displayed on the
						// screen.
	VakjeAdapter adapter;
	Button button1, button2, button3, button4, button5, button6, button7,
			button8, button9;
	Button buttonClear, buttonSolve, buttonGenerate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// We'll be creating an image that is 100 pixels wide and 200 pixels
		// tall.
		int width = 100;
		int height = 200;

		// Create a bitmap with the dimensions we defined above, and with a
		// 16-bit pixel format. We'll
		// get a little more in depth with pixel formats in a later post.
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

		// Create a paint object for us to draw with, and set our drawing color
		// to blue.
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);

		// Create a new canvas to draw on, and link it to the bitmap that we
		// created above. Any drawing
		// operations performed on the canvas will have an immediate effect on
		// the pixel data of the
		// bitmap.
		Canvas canvas = new Canvas(bitmap);

		// Fill the entire canvas with a red color.
		canvas.drawColor(Color.RED);

		// Draw a rectangle inside our image using the paint object we defined
		// above. The rectangle's
		// upper left corner will be at (25,50), and the lower left corner will
		// be at (75,150). Since we set
		// the paint object's color above, this rectangle will be blue.
		canvas.drawRect(25, 50, 75, 150, paint);

		// In order to display this image in our activity, we need to create a
		// new ImageView that we
		// can display.
		ImageView imageView = new ImageView(this);

		// Set this ImageView's bitmap to the one we have drawn to.
		imageView.setImageBitmap(bitmap);

		// Create a simple layout and add our image view to it.
		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		layout.addView(imageView, params);
		layout.setBackgroundColor(Color.BLACK);

		// Show this layout in our activity.
		setContentView(layout);
	}

	/**
	 * The Class VakjeAdapter.
	 */
	public class VakjeAdapter extends BaseAdapter {

		/** The My context. */
		Context MyContext;
		private int selection = -1;

		private int[][] test = new int[9][9];

		/**
		 * Instantiates a new VakjeAdapter.
		 * 
		 * @param _MyContext
		 *            the _ my context
		 */
		public VakjeAdapter(Context _MyContext) {
			MyContext = _MyContext;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			// return game.getNumberOfColumns() * game.getNumberOfRows();
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View MyView = null; // The view object that represents a item
								// in the grid. Use the position field
								// to identify.

			/* We define the view that will display on the grid */

			// Inflate the layout using the grid_item layout file.
			LayoutInflater li = getLayoutInflater();
			MyView = li.inflate(R.layout.sudokugrid_item, null);

			// Do something with the grid view items.
			TextView tv = (TextView) MyView.findViewById(R.id.grid_item_text);
			// tv.setText("" + game.getCurrentValue(position, position));

			MyView.setTag(new Integer(position));

			// On click event for the grid item.
			MyView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Get the textview from the grid item view.

					// kleuren weg
					selection = (Integer) v.getTag();

					TextView tv = (TextView) v
							.findViewById(R.id.grid_item_text);
					// Print a toast, for testing. TODO call
					Toast.makeText(SudokuGrid.this, tv.getText(),
							Toast.LENGTH_SHORT).show();

					VakjeAdapter.this.notifyDataSetChanged();
				}
			});

			if (position == selection)
				MyView.setBackgroundColor(Color.YELLOW);

			return MyView;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}