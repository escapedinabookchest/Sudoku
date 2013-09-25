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
import android.widget.Button;
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
	Game game = new Game();
	IGUItemp game = new IGUItempConcrete();
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
		setContentView(R.layout.sudokugrid_main); // Set the layout.

		MyGrid = (GridView) findViewById(R.id.MyGrid);
		adapter = new VakjeAdapter(this);
		MyGrid.setAdapter(adapter);

		// buttons
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);
		buttonClear = (Button) findViewById(R.id.buttonClear);
		buttonSolve = (Button) findViewById(R.id.buttonSolve);
		buttonGenerate = (Button) findViewById(R.id.buttonGenerate);

		// TODO selection position naar x,y converteren en invullen bij
		// listeners.
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 1);
				adapter.notifyDataSetChanged();
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 2);
				adapter.notifyDataSetChanged();
			}
		});

		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 3);
				adapter.notifyDataSetChanged();
			}
		});

		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 4);
				adapter.notifyDataSetChanged();
			}
		});

		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 5);
				adapter.notifyDataSetChanged();
			}
		});

		button6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 6);
				adapter.notifyDataSetChanged();
			}
		});

		button7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 7);
				adapter.notifyDataSetChanged();
			}
		});

		button8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 8);
				adapter.notifyDataSetChanged();
			}
		});

		button9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 9);
				adapter.notifyDataSetChanged();
			}
		});

		buttonClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.setCurrentValue(0, 1, 0);
				adapter.notifyDataSetChanged();
			}
		});

		buttonSolve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.solveGame();
				adapter.notifyDataSetChanged();
			}
		});

		buttonGenerate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				game.generateGame();
				adapter.notifyDataSetChanged();
			}
		});

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
			return game.getNumberOfColumns() * game.getNumberOfRows();
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
			tv.setText("" + game.getCurrentValue(position, position));

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