package nl.avans.IN13SAh.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
						SudokuGrid.class);
				startActivity(intent);
			}
		});

		infoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SudokuTitleScreen.this);
				builder.setMessage(R.string.infoDialogMessage)
						.setPositiveButton(
								R.string.infoDialogPositiveButtonText,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								});
			}
		});
	}
}
