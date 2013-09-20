package nl.avans.IN13SAh.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LevelSelect extends Activity implements OnClickListener {
	private Button btnBack;
	private Button btnStart;
	
	private RadioGroup radioDifficulty;
	private RadioGroup radioSize;
	private RadioButton radioDifficultyButton;
	private RadioButton radioSizeButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sudokulevel_select);
		
		addButtonListener();
	}
	
	private void addButtonListener() {
		btnBack = (Button) findViewById(R.id.btnBack);
		btnStart = (Button) findViewById(R.id.btnStart);
		
		radioDifficulty = (RadioGroup) findViewById(R.id.radioDifficulty);
		radioSize = (RadioGroup) findViewById(R.id.radioSize);
		
		btnBack.setOnClickListener(this);
		btnStart.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		radioDifficultyButton = (RadioButton) findViewById(radioDifficulty.getCheckedRadioButtonId());
		radioSizeButton = (RadioButton) findViewById(radioSize.getCheckedRadioButtonId());
		
		switch(v.getId()) {
		case R.id.btnBack:
			finish();
			break;
		case R.id.btnStart:
			Toast.makeText(this, radioDifficultyButton.getText() + " - " + radioSizeButton.getText(), Toast.LENGTH_SHORT).show();
			//TODO: send info to model
			
			Intent intent = new Intent(this, SudokuGrid.class);
			startActivity(intent);
			break;
		}
	}
}