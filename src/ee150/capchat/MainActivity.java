package ee150.capchat;

import java.io.File;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.glass.content.Intents;

//import org.opencv.android.BaseLoaderCallback;


public class MainActivity extends Activity {
	
	public final static String TAG = MainActivity.class.getSimpleName();
	
	private static final int TAKE_VIDEO_REQUEST_CODE = 1;
	private static final int REQUEST_VIDEO_CAPTURE = 2;


	TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView = (TextView)findViewById(R.id.output);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Set the textView back to default_ouput
    	// when you open the menu.
    	textView.setText(R.string.default_output);
		switch (item.getItemId()) {
			case R.id.menu_item_capchat:
				return true;
			case R.id.menu_item_team:
				startActivity(new Intent(this, Team.class));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_CENTER:
				this.openOptionsMenu();
				return true; // return true if you handled this event
		}
		return super.onKeyDown(keyCode, event);
	}
    
    
    private void takeVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, TAKE_VIDEO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_VIDEO_REQUEST_CODE && resultCode == RESULT_OK) {
            String thumbnailPath = data.getStringExtra(Intents.EXTRA_THUMBNAIL_FILE_PATH);
            String picturePath = data.getStringExtra(Intents.EXTRA_VIDEO_FILE_PATH);

            processPictureWhenReady(picturePath);
            // TODO: Show the thumbnail to the user while the full picture is being
            // processed.
        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
        	 Uri videoUri = data.getData();
             
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processPictureWhenReady(final String picturePath) {
        final File pictureFile = new File(picturePath);

        if (pictureFile.exists()) {
            // The picture is ready; process it.
        } else {
            // The file does not exist yet. Before starting the file observer, you
            // can update your UI to let the user know that the application is
            // waiting for the picture (for example, by displaying the thumbnail
            // image and a progress indicator).

            final File parentDirectory = pictureFile.getParentFile();
            FileObserver observer = new FileObserver(parentDirectory.getPath(),
                    FileObserver.CLOSE_WRITE | FileObserver.MOVED_TO) {
                // Protect against additional pending events after CLOSE_WRITE
                // or MOVED_TO is handled.
                private boolean isFileWritten;

                @Override
                public void onEvent(int event, String path) {
                    if (!isFileWritten) {
                        // For safety, make sure that the file that was created in
                        // the directory is actually the one that we're expecting.
                        File affectedFile = new File(parentDirectory, path);
                        isFileWritten = affectedFile.equals(pictureFile);

                        if (isFileWritten) {
                            stopWatching();

                            // Now that the file is ready, recursively call
                            // processPictureWhenReady again (on the UI thread).
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    processPictureWhenReady(picturePath);
                                }
                            });
                        }
                    }
                }
            };
            observer.startWatching();
        }
    }
    
	private void dispatchTakeVideoIntent() {
	    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
	    }
	}
    
    
}
