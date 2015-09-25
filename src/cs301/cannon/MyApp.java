package cs301.cannon;

import android.app.Application;
import android.content.Context;

/**
 * MyApp
 * 
 * Used to get instance of activity for playing sounds.
 * 
 * External Citation Date: 5 March 2015
 * 
 * Problem: needed baseContext in thread
 * 
 * Resource:
 * http://stackoverflow.com/questions/9445661/how-to-get-the-context-from-anywhere
 * 
 * Solution: use class to get context
 *
 * 
 */
public class MyApp extends Application {
	private static MyApp instance;

	public static MyApp getInstance() {
		return instance;
	}

	public static Context getContext() {
		return instance;
		// or return instance.getApplicationContext();
	}

	@Override
	public void onCreate() {
		instance = this;
		super.onCreate();
	}
}
