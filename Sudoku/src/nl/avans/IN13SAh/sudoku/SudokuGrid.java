package nl.avans.IN13SAh.sudoku;

import java.util.ArrayList;
import java.util.List;

import nl.avans.IN13SAh.sudoku.CanvasView.OnSudokuEventChangeListener;
import nl.avans.game.Game;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

// TODO: Auto-generated Javadoc
/**
 * The Class SudokuGrid. THis class contains logic and handlers for the playing
 * field screen.
 */
public class SudokuGrid extends SlidingActivity {

	GridView MyGrid; // The gridview object that will be displayed on the
						// screen.

	boolean popUpisShown = false;
	List<Game> lijst;
	Game currentGame;

	Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;

	CanvasView view;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout ll = new LinearLayout(this);
		ll.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		ll.setOrientation(LinearLayout.VERTICAL);

		view = new CanvasView(this);
		view.setOnSudokuEventChangeListener(new OnSudokuEventChangeListener() {

			@Override
			public void OnSelectionChanged(View v, int selX, int selY, Point p) {
				showPopup(SudokuGrid.this, p);
				SudokuGrid.this.view.enableTouch(false);
			}

			@Override
			public int OnGetCurrentValueOfPosition(View v, int x, int y) {
				if (currentGame != null)
					return currentGame.getCurrentValue(x, y);
				else
					return 0;
			}
		});

		ll.addView(view);

		setContentView(ll);
		setBehindContentView(R.layout.slidingmenulistview);

		view.requestFocus();

		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getSlidingMenu().setBehindScrollScale(0.25f);
		getSlidingMenu().setFadeDegree(0.25f);
		getSlidingMenu().setSlidingEnabled(true);
		getSlidingMenu().setMenu(R.layout.slidingmenulistview);
		getSlidingMenu().setSecondaryMenu(R.layout.sudokutitlescreen_main);
		getSlidingMenu().setBehindOffset(100);

		final ListView listview = (ListView) findViewById(R.id.slidingmenulistview);

		lijst = new ArrayList<Game>();
		final GameArrayAdapter adapter = new GameArrayAdapter(this, lijst);
		listview.setAdapter(adapter);

		lijst.add(new Game(9, 0));
		lijst.add(new Game(9, 0));
		adapter.notifyDataSetChanged();

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				SudokuGrid.this.toggle();
				currentGame = lijst.get(position);
				view.invalidate();
				adapter.notifyDataSetChanged();
			}

		});

		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					final View view, int position, long id) {
				lijst.remove(position);
				adapter.notifyDataSetChanged();
				SudokuGrid.this.currentGame = null;
				SudokuGrid.this.view.invalidate();
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(300);
				return false;
			}

		});
	}

	// The method that displays the popup.
	private void showPopup(final Activity context, Point p) {
		// If the popup is shown, do not draw another one, just ignore.
		if (popUpisShown)
			return;
		popUpisShown = true;
		int popupWidth = 300;
		int popupHeight = 350;

		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) context
				.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popuplayout, viewGroup);

		// Creating the PopupWindow
		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);
		popup.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				SudokuGrid.this.popUpisShown = false;
				SudokuGrid.this.view.enableTouch(true);
			}
		});

		// Some offset to align the popup a bit to the right, and a bit down,
		// relative to button's position.
		int OFFSET_X = 30;
		int OFFSET_Y = 30;

		// Clear the default translucent background
		popup.setBackgroundDrawable(new BitmapDrawable());

		// Displaying the popup at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y
				+ OFFSET_Y);

		// Getting a reference to Close button, and close the popup when
		// clicked.
		Button close = (Button) layout.findViewById(R.id.b1);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popup.dismiss();
			}
		});
	}

	private class GameArrayAdapter extends ArrayAdapter<Game> {

		private final Context context;
		List<Game> games;

		public GameArrayAdapter(Context context, List<Game> objects) {
			super(context, R.layout.slidingmenulistviewitem, objects);
			this.context = context;
			this.games = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.slidingmenulistviewitem,
					parent, false);
			TextView textView = (TextView) rowView
					.findViewById(R.id.secondLine);
			textView.setText(games.get(position).toString());

			return rowView;
		}

	}
}