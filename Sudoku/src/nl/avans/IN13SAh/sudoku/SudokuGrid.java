package nl.avans.IN13SAh.sudoku;

import java.util.ArrayList;
import java.util.List;

import nl.avans.IN13SAh.sudoku.CanvasView.OnSudokuEventChangeListener;
import nl.avans.game.Game;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

// TODO: Auto-generated Javadoc
/**
 * The Class SudokuGrid. This class contains logic and handlers for the playing
 * field screen.
 */
public class SudokuGrid extends SlidingActivity {

	/** Boolean value for stopping multiple instances of the number input popup. */
	boolean popUpisShown = false;

	/**
	 * Game object array that is shown in the left slidemenu. use
	 * adapter.notifydatasetchanged() after an edit.
	 */
	List<Game> lijst;

	/** The current game. Object that is being viewed. */
	Game currentGame;

	/** Pointer to a the custom view. */
	CanvasView view;

	/** The popup window that is shown when tapping on the sudoku board. */
	PopupWindow popup;

	/** Info button in the right menu */
	Button infoButton;

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

		view = new CanvasView(this, 9);

		view.setOnSudokuEventChangeListener(new OnSudokuEventChangeListener() {

			@Override
			public void OnSelectionChanged(View v, int selX, int selY, Point p) {
				if (SudokuGrid.this.currentGame != null) {
					showPopup(SudokuGrid.this, p);
					SudokuGrid.this.view.enableTouch(false);
				}
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
		getSlidingMenu().setSecondaryMenu(R.layout.secondmenu);
		getSlidingMenu().setBehindOffset(100);

		final ListView listview = (ListView) findViewById(R.id.slidingmenulistview);

		lijst = new ArrayList<Game>();
		final GameArrayAdapter adapter = new GameArrayAdapter(this, lijst);
		listview.setAdapter(adapter);

		adapter.notifyDataSetChanged();

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				SudokuGrid.this.toggle();
				currentGame = lijst.get(position);
				SudokuGrid.this.view.setBoardSize(currentGame.getSize());
				adapter.setSelection(SudokuGrid.this.lijst.indexOf(currentGame));
				adapter.notifyDataSetChanged();
			}

		});

		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					final View view, int position, long id) {
				lijst.remove(position);
				adapter.removeSelection();
				adapter.notifyDataSetChanged();
				SudokuGrid.this.currentGame = null;
				SudokuGrid.this.view.invalidate();
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(300);
				return false;
			}

		});

		Button newGamebutton = (Button) findViewById(R.id.newGameButton);
		newGamebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(SudokuGrid.this);
				dialog.setTitle("New Game");
				LayoutInflater inflater = (LayoutInflater) SudokuGrid.this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.newgamepopup,
						(ViewGroup) findViewById(R.id.newGameRootElement));
				dialog.setContentView(layout);

				Button okButton = (Button) layout
						.findViewById(R.id.newGamePopupOk);
				Button cancelButton = (Button) layout
						.findViewById(R.id.newGamePopupCancel);

				final SeekBar gameSizeSeekerBar = (SeekBar) layout
						.findViewById(R.id.gameSizeSeekbar);
				final SeekBar difficultySeekBar = (SeekBar) layout
						.findViewById(R.id.difficultySeekBar);

				okButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// Create a new game and add it to the list.
						Game newGame = new Game(
								gameSizeSeekerBar.getProgress(),
								difficultySeekBar.getProgress());
						SudokuGrid.this.lijst.add(newGame);
						adapter.setSelection((SudokuGrid.this.lijst.size() - 1));
						adapter.notifyDataSetChanged();
						currentGame = newGame;
						SudokuGrid.this.view.setBoardSize(currentGame.getSize());
						SudokuGrid.this.toggle();
						dialog.dismiss();
					}
				});

				cancelButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});

		/* Info dialog */
		infoButton = (Button) findViewById(R.id.infoButton);
		infoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View messageView = getLayoutInflater().inflate(R.layout.about,
						null, false);

				// When linking text, force to always use default color. This
				// works
				// around a pressed color state bug.
				TextView textView = (TextView) messageView
						.findViewById(R.id.about_credits);
				int defaultColor = textView.getTextColors().getDefaultColor();
				textView.setTextColor(defaultColor);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						SudokuGrid.this);
				builder.setTitle(R.string.app_name);
				builder.setView(messageView);
				builder.create();
				builder.show();
			}
		});
	}

	// The method that displays the popup.
	/**
	 * Show popup.
	 * 
	 * @param context
	 *            the context (activity, should be SudokuGrid)
	 * @param p
	 *            the point where the popup should be drawn.
	 */
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
		popup = new PopupWindow(context);
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
	}

	/**
	 * Pop up button handle. Called from xml layout buttons of the popup.
	 * 
	 * @param v
	 *            the view object that calls this method from xml.
	 */
	public void popUpButtonHandle(View v) {
		Button b = (Button) v;
		if (currentGame != null) {
			int number = Integer.parseInt(b.getText().toString());
			currentGame.setCurrentValue(view.getSelX(), view.getSelY(), number);
			view.invalidate();
			popup.dismiss();
		}
	}

	/**
	 * The Class GameArrayAdapter. Adapter class for the list view on the side
	 * of the class.
	 */
	private class GameArrayAdapter extends ArrayAdapter<Game> {

		/** The context. Should be an activity. */
		private final Context context;

		/**
		 * Adapter internal representation of game objects. (remember, just
		 * pointers)
		 */
		List<Game> games;

		/**
		 * The selection. Used for representing the currently selected game
		 * object.
		 */
		int selection = -1;

		/**
		 * Instantiates a new game array adapter.
		 * 
		 * @param context
		 *            the context, i.e. an activity.
		 * @param objects
		 *            important parameter used for binding a list to this
		 *            adapter.
		 */
		public GameArrayAdapter(Context context, List<Game> objects) {
			super(context, R.layout.slidingmenulistviewitem, objects);
			this.context = context;
			this.games = objects;
		}

		/**
		 * Sets the selection.
		 * 
		 * @param selection
		 *            the new selection
		 */
		public void setSelection(int selection) {
			this.selection = selection;
		}

		/**
		 * Removes the selection.
		 */
		public void removeSelection() {
			this.selection = -1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.slidingmenulistviewitem,
					parent, false);
			TextView textView = (TextView) rowView
					.findViewById(R.id.secondLine);
			textView.setText(games.get(position).toString());
			TextView textview2 = (TextView) rowView
					.findViewById(R.id.firstLine);
			textview2.setText("Sudoku " + games.get(position).getSize() + "x"
					+ games.get(position).getSize());

			rowView.setBackgroundColor(Color.WHITE);
			if (position == selection)
				rowView.setBackgroundColor(Color.LTGRAY);

			return rowView;
		}

	}
}