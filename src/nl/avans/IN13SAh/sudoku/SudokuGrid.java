package nl.avans.IN13SAh.sudoku;

import nl.avans.game.Game;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
	Game game;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CanvasView view = new CanvasView(this);
		setContentView(view);
		view.requestFocus();
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
			int x = position % 9;
			int y = position / 9;

			TextView tv = (TextView) MyView.findViewById(R.id.grid_item_text);

			tv.setText("" + game.getCurrentValue(x, y));

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

		public int getSelection() {
			return selection;
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