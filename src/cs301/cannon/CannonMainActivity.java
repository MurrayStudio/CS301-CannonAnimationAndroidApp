package cs301.cannon;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * CannonMainActivity
 * 
 * This is the activity for the cannon animation. It creates a AnimationCanvas
 * containing a particular Animator object
 * 
 * @author Shamus Murray
 * @author Andrew Nuxoll
 * @version March 2015
 * 
 * Extra Credit: 
 * -Ball bounces and rolls 
 * -Ball stops when hitting ground (after bouncing)
 * -Plays sound when fired
 * -Shoot multiple balls at once (just fire rapidly)
 */
public class CannonMainActivity extends Activity {
	
	//new seekbar
	private SeekBar cannon;
	//holds value of seekbar
	public static int angle;
	//textview for displaying message
	public static TextView message;

	/**
	 * creates an AnimationCanvas containing a TestAnimator.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cannon_main);

		// Create an animation canvas and place it in the main layout
		Animator anim = new CannonAnimator();
		AnimationCanvas myCanvas = new AnimationCanvas(this, anim);
		LinearLayout mainLayout = (LinearLayout) this
				.findViewById(R.id.topLevelLayout);
		mainLayout.addView(myCanvas);
		
		message = (TextView) findViewById(R.id.message);
		cannon = (SeekBar) findViewById(R.id.cannon_seek);
		//set up listener for seekbar
		cannon.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				//have public var angle be assigned to progress of seekbar
				angle = progress;
			}
		});

	}

	/**
	 * This is the default behavior (empty menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cannon_main, menu);
		return true;
	}
}
