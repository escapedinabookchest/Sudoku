package nl.avans.IN13SAh.sudoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

// TODO: Auto-generated Javadoc
/**
 * The Class CanvasView. A custom view for drawing the sudoku field.
 */
class CanvasView extends View {

	/** The listener. Used for responding to view events. */
	private OnSudokuEventChangeListener listener;

	/** The x value of the selection. */
	private int selX;

	/** The y value of the selection. */
	private int selY;

	/** The rectangle object of the selection. */
	private Rect selRect;

	/** The width and height of the grid in pixels. */
	private int height, width;

	/** The board size. (for example 9 meaning 9x9) */
	private float boardSize;

	/** Value for enabeling touch responseness for the view. */
	private boolean enableTouch = true;

	final GestureDetector gestureDetector = new GestureDetector(getContext(),
			new GestureDetector.SimpleOnGestureListener() {

				@Override
				public void onLongPress(MotionEvent e) {
					Vibrator vib = (Vibrator) CanvasView.this.getContext()
							.getSystemService(Context.VIBRATOR_SERVICE);
					vib.vibrate(300);

					listener.OnLongPressAction(CanvasView.this,
							CanvasView.this.selX, CanvasView.this.selY);
				}

				@Override
				public boolean onDoubleTap(MotionEvent e) {
					gestureDetector.setIsLongpressEnabled(false);
					return true;
				};

				@Override
				public boolean onSingleTapConfirmed(MotionEvent e) {

					if (!enableTouch)
						return true;

					select((int) (e.getX() / width), (int) (e.getY() / height));
					// De listener wordt aangeroepen. Belangrijk!
					listener.OnSelectionChanged(CanvasView.this, selX, selY,
							new Point((int) e.getX(), (int) e.getY()));
					Log.d("PuzzleView", "onTouchEvent: " + selX + ", " + selY);

					return true;
				};

			});

	/**
	 * The listener interface for receiving onSudokuEventChange events. The
	 * class that is interested in processing a onSudokuEventChange event
	 * implements this interface, and the object created with that class is
	 * registered with a component using the component's
	 * <code>addOnSudokuEventChangeListener<code> method. When
	 * the onSudokuEventChange event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see OnSudokuEventChangeEvent
	 */
	public interface OnSudokuEventChangeListener {

		/**
		 * On selection changed.
		 * 
		 * @param v
		 *            view object (will be Canvasview)
		 * @param selX
		 *            the x value of the selection
		 * @param selY
		 *            the y value of the selection
		 * @param p
		 *            the point that was touched (containing pixels)
		 */
		void OnSelectionChanged(View v, int selX, int selY, Point p);

		/**
		 * Method the view uses to get information about a position (from a game
		 * object).
		 * 
		 * @param v
		 *            view object (will be canvasview)
		 * @param x
		 *            x value in the grid
		 * @param y
		 *            y value in the grid
		 * @return the int value of a specific field
		 */
		int OnGetCurrentValueOfPosition(View v, int x, int y);

		boolean ShouldDrawSelection();

		void OnLongPressAction(View v, int x, int y);
	}

	Bitmap background; // maar 1 keer inladen.

	/**
	 * Instantiates a new canvas view.
	 * 
	 * @param context
	 *            the context (an activity for example)
	 * @param boardSize
	 *            the board size (for example 9 for 9x9)
	 */
	public CanvasView(Context context, int boardSize) {
		super(context);
		selX = -1;
		selY = -1;

		/* achtergrond plaatje laden en schalen naar device size */
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		this.background = BitmapFactory.decodeResource(getResources(),
				R.drawable.startup_background);
		this.background = Bitmap.createScaledBitmap(this.background,
				display.getWidth(), display.getHeight(), true);

		this.boardSize = boardSize;
		this.selRect = new Rect();
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	/**
	 * Sets the on sudoku event change listener.
	 * 
	 * @param listener
	 *            set the sudokueventchange listener for this view.
	 */
	public void setOnSudokuEventChangeListener(
			OnSudokuEventChangeListener listener) {
		this.listener = listener;
	}

	/* Methode om de view aan of uit te zetten. */
	/**
	 * Enable touch for this view.
	 * 
	 * @param newValue
	 *            the new enableTouch value
	 */
	public void enableTouch(boolean newValue) {
		this.enableTouch = newValue;
	}

	/**
	 * Gets the sel x.
	 * 
	 * @return the sel x
	 */
	public int getSelX() {
		return (int) selX;
	}

	/**
	 * Gets the sel y.
	 * 
	 * @return the sel y
	 */
	public int getSelY() {
		return (int) selY;
	}

	/**
	 * Sets the board size.
	 * 
	 * @param newSize
	 *            the new board size (for example: 9 for 9x9)
	 */
	public void setBoardSize(int newSize) {
		this.boardSize = newSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onSaveInstanceState()
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putInt("selX", selX);
		bundle.putInt("selY", selY);
		bundle.putParcelable("viewState", super.onSaveInstanceState());

		return bundle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onRestoreInstanceState(android.os.Parcelable)
	 */
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		Bundle bundle = new Bundle();
		// select(bundle.getInt("selX"), bundle.getInt("selY"))
		bundle.getParcelable("viewState");

		super.onRestoreInstanceState(state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		gestureDetector.setIsLongpressEnabled(true);

		return true;
	}

	/**
	 * Private method for switchting the selection (visual) in the view.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	private boolean select(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		Log.d("PuzzleView", "drawing bg");
		// draw background
		Paint background = loadColor(R.color.puzzle_background);
		// Dit is waarschijnlijk traag als fuck

		if (!listener.ShouldDrawSelection()) { // geen selectie, wel plaatje
			canvas.drawBitmap(this.background, 0f, 0f, background);
		} else {
			canvas.drawRect(0.0f, 0.0f, (float) getWidth(),
					(float) getHeight(), background);

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
					canvas.drawLine(0, i * height + 1, getWidth(), i * height
							+ 1, hilite);
					canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
					canvas.drawLine(i * width + 1, 0, i * width + 1,
							getHeight(), hilite);
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
					int celwaarde = listener.OnGetCurrentValueOfPosition(this,
							i, j);
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
			 * loadColor(R.color.puzzle_hint_0),
			 * loadColor(R.color.puzzle_hint_1),
			 * loadColor(R.color.puzzle_hint_2) ] r = Rect.new i = 0 while (i<9)
			 * j = 0 while (j<9) used =@game.getUsedTiles(i,j) moves_left = 9 -
			 * used.size if (moves_left < colors.size) getRect(i, j, r)
			 * canvas.drawRect(r,Paint(colors.get(moves_left))) end j+=1 end
			 * i+=1 end end
			 */

			// draw selection
			Paint selected = loadColor(R.color.puzzle_selected);
			selected.setAlpha(80);
			canvas.drawRect(selRect, selected);
		}

		// Draw call!
		super.onDraw(canvas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.d("PuzzleView", "size changed: #{w}x#{h}");
		width = (int) (w / boardSize);
		height = (int) (h / boardSize);
		getRect(selX, selY, selRect);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * Gets the rect. Used for getting a rect object at a specific position in
	 * the sudoku canvas.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param rect
	 *            the rect
	 * @return the rect
	 */
	private Rect getRect(int x, int y, Rect rect) {
		rect.set(x * width, y * height, x * width + width, y * height + height);
		return rect;
	}

	/**
	 * Load color. Create a paint object with a color key. Used in drawing.
	 * 
	 * @param key
	 *            the key
	 * @return the paint
	 */
	private Paint loadColor(int key) {
		Paint paint = new Paint();
		paint.setColor(getResources().getColor(key));
		return paint;
	}
}
