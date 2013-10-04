package nl.avans.IN13SAh.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.avans.IN13SAh.sudoku.CanvasView.OnSudokuEventChangeListener;
import nl.avans.game.Game;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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
	Game game;
	boolean popUpisShown = false;

	Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
	Point p; // popup plaats

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		game = new Game(9, 0);

		LinearLayout ll = new LinearLayout(this);
		ll.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		ll.setOrientation(LinearLayout.VERTICAL);

		LinearLayout bll = new LinearLayout(this);
		bll.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		CanvasView view = new CanvasView(this);
		view.setOnSudokuEventChangeListener(new OnSudokuEventChangeListener() {

			@Override
			public void OnSelectionChanged(View v, int selX, int selY, Point p) {
				showPopup(SudokuGrid.this, p);
			}
		});

		b1 = new Button(this);

		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				// Open popup window
				if (p != null)
					showPopup(SudokuGrid.this, p);
			}
		});

		b1.setText("1");
		Button b2 = new Button(this);
		b2.setText("2");
		Button b3 = new Button(this);
		b3.setText("3");
		Button b4 = new Button(this);
		b4.setText("4");
		Button b5 = new Button(this);
		b5.setText("5");
		Button b6 = new Button(this);
		b6.setText("6");
		Button b7 = new Button(this);
		b7.setText("7");
		Button b8 = new Button(this);
		b8.setText("8");
		Button b9 = new Button(this);
		b9.setText("9");

		bll.addView(b1);
		bll.addView(b2);
		bll.addView(b3);
		bll.addView(b4);
		bll.addView(b5);
		bll.addView(b6);
		bll.addView(b7);
		bll.addView(b8);
		bll.addView(b9);

		ll.addView(bll);
		ll.addView(view);

		setContentView(ll);
		setBehindContentView(R.layout.slidingmenulistview);

		view.requestFocus();

		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getSlidingMenu().setBehindScrollScale(0.25f);
		getSlidingMenu().setFadeDegree(0.25f);
		getSlidingMenu().setSlidingEnabled(true);
		getSlidingMenu().setMenu(R.layout.slidingmenulistview);
		getSlidingMenu().setBehindOffset(100);

		final ListView listview = (ListView) findViewById(R.id.slidingmenulistview);

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i)
			list.add(values[i]);

		final GameArrayAdapter adapter = new GameArrayAdapter(this, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				SudokuGrid.this.toggle();
			}

		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		int[] location = new int[2];

		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		b1.getLocationOnScreen(location);

		// Initialize the Point with x, and y positions
		p = new Point();
		p.x = location[0];
		p.y = location[1];
	}

	// The method that displays the popup.
	private void showPopup(final Activity context, Point p) {
		// If the popup is shown, do not draw another one, just ignore.
		if (popUpisShown)
			return;
		popUpisShown = true;
		int popupWidth = 300;
		int popupHeight = 250;

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

	String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
			"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
			"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
			"Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
			"iPhone", "WindowsMobile" };

	private class GameArrayAdapter extends ArrayAdapter<String> {

		private final Context context;
		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public GameArrayAdapter(Context context, List<String> objects) {
			super(context, R.layout.slidingmenulistviewitem, objects);
			this.context = context;

			for (int i = 0; i < objects.size(); ++i)
				mIdMap.put(objects.get(i), i);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.slidingmenulistviewitem,
					parent, false);
			TextView textView = (TextView) rowView
					.findViewById(R.id.secondLine);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
			textView.setText(values[position]);

			return rowView;
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}