package ee150.capchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class Team extends Activity {
	
	public final static String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.team, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//			case R.id.menu_item_capchat:
//				return true;
//			case R.id.menu_item_team:
//				startActivity(new Intent(this, Team.class));
//				return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		switch (keyCode) {
//		case KeyEvent.KEYCODE_DPAD_CENTER:
//			Log.d(TAG, "Tapped (DPAD_CENTER)");
//			openOptionsMenu(); // open the option menu on tap
//			return true; // return true if you handled this event
//		}
//		return super.onKeyDown(keyCode, event);
//	}
}
