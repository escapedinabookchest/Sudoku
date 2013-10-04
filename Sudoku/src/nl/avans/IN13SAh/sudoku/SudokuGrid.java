package nl.avans.IN13SAh.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.avans.game.Game;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

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

		Button b1 = new Button(this);
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
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
				"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
				"Android", "iPhone", "WindowsMobile" };

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i)
			list.add(values[i]);

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				R.layout.slidingmenulistviewitem, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				SudokuGrid.this.toggle();
			}

		});

	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
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