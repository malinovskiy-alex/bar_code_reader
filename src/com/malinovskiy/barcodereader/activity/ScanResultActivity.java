package com.malinovskiy.barcodereader.activity;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.malinovskiy.barcodereader.R;
import com.malinovskiy.barcodereader.domain.BarcodeBean;
import com.malinovskiy.barcodereader.task.SendBarcodeTask;

public class ScanResultActivity extends Activity {
	private static final String TASK_TAG_NAME = "SendBarcodeTask";
	private IntentIntegrator scanIntegrator;
	private BarcodeBean bean;
	private TextView errors;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_result);
		Button completeButton = (Button) findViewById(R.id.complete);
		errors = ((TextView) findViewById(R.id.errors));
		completeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					try {
						SendBarcodeTask task = new SendBarcodeTask();
						task.execute(bean);
						if (task.get() != SendBarcodeTask.SUCCESSFULL_CODE) {
							errors.setText("Unable to send data on server. Please try again.");
						} else {
							errors.setText("Barcode is added on the server.");
						}
					} catch (InterruptedException e) {
						Log.e(TASK_TAG_NAME,
								"Post barcode task was interrupted.", e);
					} catch (ExecutionException e) {
						Log.e(TASK_TAG_NAME,
								"There is an error during task execution", e);
					}
				} else {
					errors.setText("No network connection available.");
				}
			}
		});
		Button rescanButton = (Button) findViewById(R.id.rescan);
		rescanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				scanIntegrator.initiateScan();
			}
		});
		Button cancelButton = (Button) findViewById(R.id.cancel);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent main = new Intent(getApplicationContext(),
						MainActivity.class);
				main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(main);
			}
		});
		setupBean();
		scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.scan_result, menu);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);
		if (scanningResult == null) {
			errors.setText("Scan is not successful! Please try again.");
			((Button) findViewById(R.id.complete))
					.setVisibility(View.INVISIBLE);
		} else {
			bean.setBarcode(scanningResult.getContents());
		}
	}

	private void setupBean() {
		bean = new BarcodeBean();
		Intent i = getIntent();
		bean.setLocationCode(i.getExtras().getString(
				MainActivity.UN_LOCATION_CODE));
		bean.setType(i.getExtras().getString(MainActivity.ENTRY_TYPE));
	}

}
