package com.malinovskiy.barcodereader.task;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.malinovskiy.barcodereader.domain.BarcodeBean;

public class SendBarcodeTask extends AsyncTask<BarcodeBean, Void, Integer> {
	private static final String TAG_NAME = "SendBarcodeTask";
	private static final String POST_URL = "http://barcodereceiver.herokuapp.com/barcodes";

	@Override
	protected Integer doInBackground(BarcodeBean... params) {
		return doPostRequest(params[0]);
	}

	private int doPostRequest(BarcodeBean bean) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(POST_URL);
			StringEntity input = new StringEntity(bean.toString());
			input.setContentType("application/json");
			postRequest.setEntity(input);
			return httpClient.execute(postRequest).getStatusLine()
					.getStatusCode();
		} catch (IOException e) {
			Log.e(TAG_NAME, "Post request error.", e);
			return 400;
		}
	}

}
