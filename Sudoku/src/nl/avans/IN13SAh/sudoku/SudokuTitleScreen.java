package nl.avans.IN13SAh.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class SudokuTitleScreen. Represents the title screen that is launched
 * first.
 */
public class SudokuTitleScreen extends Activity {

	private Button startButton;
	private Button infoButton;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sudokutitlescreen_main);

		startButton = (Button) findViewById(R.id.startgameButton);
		infoButton = (Button) findViewById(R.id.showinfoButton);

		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SudokuTitleScreen.this,
						LevelSelect.class);
				startActivity(intent);
			}
		});

		infoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showAbout();
			}
		});
	}
	
	protected void showAbout() {
        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);
 
        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        int defaultColor = textView.getTextColors().getDefaultColor();
        textView.setTextColor(defaultColor);
 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }
}
