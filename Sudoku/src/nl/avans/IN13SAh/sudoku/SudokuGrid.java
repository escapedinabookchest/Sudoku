package nl.avans.IN13SAh.sudoku;

import nl.avans.game.Game;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class SudokuGrid. THis class contains logic and handlers for the playing
 * field screen.
 */
public class SudokuGrid extends Activity {

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
		view.requestFocus();

		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// menu.setShadowWidthRes(R.dimen.shadow_width);
		// menu.setShadowDrawable(R.drawable.shadow);
		// menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// menu.setMenu(R.layout.menu);
	}
}