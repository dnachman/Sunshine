package com.logicalenigma.sunshineeclipse;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new DetailFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(settingsIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DetailFragment extends Fragment {
		
		private final static String TAG = DetailFragment.class.getName();
		
		private final static String FORECAST_SHARE_HASHTAG = "#SunshineApp";
		
		private String mForecastString;

		public DetailFragment() {
			setHasOptionsMenu(true);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detail,
					container, false);
			
			TextView detailText = (TextView) rootView.findViewById(R.id.detail_text);
			Intent intent = getActivity().getIntent();
			if (intent.hasExtra(Intent.EXTRA_TEXT)) {
				mForecastString = intent.getStringExtra(Intent.EXTRA_TEXT);
			}
			else {
				mForecastString = "Forecast unavailable";
			}
			detailText.setText(mForecastString);
			
			return rootView;
		}
		
		private Intent createShareForecastIntent() {
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastString + FORECAST_SHARE_HASHTAG);
			
			return shareIntent;
			
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.detailfragment, menu);
			
			MenuItem item = menu.findItem(R.id.action_share);
			
			// get provider and hold on to it to set/change the share intent
			ShareActionProvider mShareActionProvider = (ShareActionProvider) item.getActionProvider();
			
			// attach an intent to this shareactionprovider. you can update this any time, 
			// like when  the user selects a new piece of data they might like to share
			if (mShareActionProvider != null) {
				mShareActionProvider.setShareIntent(createShareForecastIntent());
			}
			else {
				Log.d(TAG, "Share action provider is null?");
			}
		}
	}
}
