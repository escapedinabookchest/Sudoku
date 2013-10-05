package nl.avans.IN13SAh.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class CanvasView extends View {

	private OnSudokuEventChangeListener listener;
	private int selX;
	private int selY;
	private Rect selRect;
	private int height, width;
	private boolean enableTouch = true;

	public interface OnSudokuEventChangeListener {
		void OnSelectionChanged(View v, int selX, int selY, Point p);

		int OnGetCurrentValueOfPosition(View v, int x, int y);
	}

	public CanvasView(Context context) {
		super(context);
		selX = 0;
		selY = 0;
		this.selRect = new Rect();
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	public void setOnSudokuEventChangeListener(
			OnSudokuEventChangeListener listener) {
		this.listener = listener;
	}

	/* Methode om de view aan of uit te zetten. */
	public void enableTouch(boolean newValue) {
		this.enableTouch = newValue;
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putInt("selX", selX);
		bundle.putInt("selY", selY);
		bundle.putParcelable("viewState", super.onSaveInstanceState());

		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		Bundle bundle = new Bundle();
		// select(bundle.getInt("selX"), bundle.getInt("selY"))
		bundle.getParcelable("viewState");

		super.onRestoreInstanceState(state);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!enableTouch)
			return false;
		if (event.getAction() == MotionEvent.ACTION_UP) {

			select((int) (event.getX() / width), (int) (event.getY() / height));
			// De listener wordt aangeroepen. Belangrijk!
			listener.OnSelectionChanged(this, selX, selY,
					new Point((int) event.getX(), (int) event.getY()));
			Log.d("PuzzleView", "onTouchEvent: " + selX + ", " + selY);
		}
		return true;
	}

	private boolean select(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d("PuzzleView", "drawing bg");
		// draw background
		Paint background = loadColor(R.color.puzzle_background);
		canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(),
				background);

		Log.d("PuzzleView", "drawing lines");
		// draw board
		// line colors
		Paint dark = loadColor(R.color.puzzle_dark);
		dark.setStrokeWidth(2);
		Paint hilite = loadColor(R.color.puzzle_hilite);
		Paint light = loadColor(R.color.puzzle_light);

		// draw lines
		int i = 0;
		while (i < 9) {
			// minor lines
			canvas.drawLine(0, i * height, getWidth(), i * height, light);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
			// major lines
			if (i % 3 == 0) {
				canvas.drawLine(0, i * height, getWidth(), i * height, dark);
				canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
						hilite);
				canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
				canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
						hilite);
			}
			i++;
		}

		// draw numbers
		Log.d("PuzzleView", "drawing numbers");
		Paint foreground = loadColor(R.color.puzzle_foreground);
		foreground.setStyle(Style.FILL);
		foreground.setTextSize((float) (height * 0.75));
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);

		FontMetrics fm = foreground.getFontMetrics();
		float x = width / 2;
		float y = height / 2 - (fm.ascent + fm.descent) / 2;
		i = 0;
		int j = 0;
		while (i < 9) {
			j = 0;
			while (j < 9) {
				int celwaarde = listener
						.OnGetCurrentValueOfPosition(this, i, j);
				// lege string bij nul.
				String celtext = celwaarde == 0 ? "" : "" + celwaarde;

				canvas.drawText(celtext, i * width + x, j * height + y,
						foreground);
				j++;
			}
			i++;
		}

		// draw hints
		/*
		 * if (Prefs.getHints(getContext)) colors = [
		 * loadColor(R.color.puzzle_hint_0), loadColor(R.color.puzzle_hint_1),
		 * loadColor(R.color.puzzle_hint_2) ] r = Rect.new i = 0 while (i<9) j =
		 * 0 while (j<9) used =@game.getUsedTiles(i,j) moves_left = 9 -
		 * used.size if (moves_left < colors.size) getRect(i, j, r)
		 * canvas.drawRect(r,Paint(colors.get(moves_left))) end j+=1 end i+=1
		 * end end
		 */

		// draw selection
		Paint selected = loadColor(R.color.puzzle_selected);
		selected.setAlpha(80);
		canvas.drawRect(selRect, selected);
		super.onDraw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.d("PuzzleView", "size changed: #{w}x#{h}");
		width = (int) (w / 9.0);
		height = (int) (h / 9.0);
		getRect(selX, selY, selRect);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private Rect getRect(int x, int y, Rect rect) {
		rect.set(x * width, y * height, x * width + width, y * height + height);
		return rect;
	}

	private Paint loadColor(int key) {
		Paint paint = new Paint();
		paint.setColor(getResources().getColor(key));
		return paint;
	}
}
