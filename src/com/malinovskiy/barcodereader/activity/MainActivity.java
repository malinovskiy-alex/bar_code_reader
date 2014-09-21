package com.malinovskiy.barcodereader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.malinovskiy.barcodereader.R;

public class MainActivity extends Activity {

	public static final String UN_LOCATION_CODE = "locationCode";
	public static final String ENTRY_TYPE = "entryType";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button scanButton = (Button) findViewById(R.id.scan);
		scanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent scanResult = new Intent(getApplicationContext(),
						ScanResultActivity.class);
				scanResult.putExtra(UN_LOCATION_CODE,
						((EditText) findViewById(R.id.locationCode)).getText().toString());
				scanResult.putExtra(ENTRY_TYPE,
						((Spinner) findViewById(R.id.entryType))
								.getSelectedItem().toString());
				startActivity(scanResult);
			}
		});
		Button quitButton = (Button) findViewById(R.id.quit);
		quitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
