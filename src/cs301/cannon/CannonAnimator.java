package cs301.cannon;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.*;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;

/**
 * class that animates a ball repeatedly moving diagonally on simple background
 *
 * @author Shamus Murray
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version March 2015
 * 
 * Extra Credit: 
 * -Ball bounces and rolls 
 * -Ball stops when hitting ground (after bouncing)
 * -Plays sound when fired
 * -Shoot multiple balls at once (just fire rapidly)
 */
public class CannonAnimator extends Activity implements Animator {

	// determines if targets have been hit
	private boolean target1Visible = true;
	private boolean target2Visible = true;

	// used for run ui thread as iterator
	private int i = 0;

	// mediaplayer to play cannon fire
	MediaPlayer mp = null;

	// define new cannonball to fire
	private CannonBall cannonball;
	private ArrayList<CannonBall> cannons = new ArrayList<CannonBall>();

	// used to draw cannon
	private int left = 50;
	private int top = 450;
	private int right = 400;
	private int bottom = 550;

	private float dx1;
	private float dx2;
	private float dy1;
	private float dy2;

	// holds canvas height
	private int cHeight;

	// define objects of type target
	private Target target1;
	private Target target2;

	SoundPool soundPool;
	HashMap<Integer, Integer> soundPoolMap;
	int soundID = 1;

	/**
	 * Interval between animation frames: .03 seconds (i.e., about 33 times per
	 * second).
	 * 
	 * @return the time interval between frames, in milliseconds.
	 */
	public int interval() {
		return 30;
	}

	/**
	 * The background color: a light blue.
	 * 
	 * @return the background color onto which we will draw the image.
	 */
	public int backgroundColor() {
		// create/return the background color
		return Color.rgb(180, 200, 255);
	}

	/**
	 * Action to perform on clock tick
	 * 
	 * @param g
	 *            the graphics object on which to draw
	 */
	public void tick(Canvas g) {

		// get height of campus
		cHeight = g.getHeight();

		// create two new targets
		target1 = new Target(g.getWidth() - 100, g.getHeight() - 300);
		target2 = new Target(g.getWidth() - 300, g.getHeight() - 500);

		// if target not hit, paint normal
		if (target1Visible == true) {
			target1.paintTarget(g);
		}
		// if hit, paint hit target
		else {
			target1.paintTargetHit(g);
		}
		// ditto for target2
		if (target2Visible == true) {
			target2.paintTarget(g);
		} else {
			target2.paintTargetHit(g);
		}

		// define paint for cannon
		Paint greyPaint = new Paint();
		greyPaint.setColor(Color.GRAY);
		Paint dkGrayPaint = new Paint();
		dkGrayPaint.setColor(Color.DKGRAY);

		// save current canvas to restore later
		g.save();
		// rotate canvas by angle given by seekbar in main activity
		// set pivot point to be bottom left of cannon
		g.rotate(-CannonMainActivity.angle, 50, 500);
		// draw cannon parts
		RectF oval = new RectF(left - 30, top, right - 30, bottom);
		RectF stand = new RectF(left - 50, top + 10, right - 150, bottom + 50);
		RectF hole = new RectF(320, 525, 370, 475);
		// draw cannon barrel and hole
		g.drawOval(oval, greyPaint);
		g.drawOval(hole, dkGrayPaint);
		// restore canvas to stop rotating other pars
		g.restore();
		// draw stand
		g.drawRect(stand, dkGrayPaint);

		//paint each cannonball object created
		for (CannonBall name : cannons) {
			if (name != null) {
				name.paintCannonBall(g);

				//
				//draw cannom again so cannonball is behind it
				//
				
				// define paint for cannon
				greyPaint.setColor(Color.GRAY);
				dkGrayPaint.setColor(Color.DKGRAY);
				
				// save current canvas to restore later
				g.save();
				// rotate canvas by angle given by seekbar in main activity
				// set pivot point to be bottom left of cannon
				g.rotate(-CannonMainActivity.angle, 50, 500);
				// draw cannon parts
				oval = new RectF(left - 30, top, right - 30, bottom);
				stand = new RectF(left - 50, top + 10, right - 150, bottom + 50);
				hole = new RectF(320, 525, 370, 475);
				// draw cannon barrel and hole
				g.drawOval(oval, greyPaint);
				g.drawOval(hole, dkGrayPaint);
				// restore canvas to stop rotating other pars
				g.restore();
				// draw stand
				g.drawRect(stand, dkGrayPaint);

				// if all 3 of these exist
				if (name != null && target1 != null && target2 != null) {

					// calculate how far cannonball x and y is from each
					// target x and y coord

					dx1 = name.getX() - target1.getX();
					dy1 = name.getY() - target1.getY();

					dx2 = name.getX() - target2.getX();
					dy2 = name.getY() - target2.getY();

					// if cannonball intersects a target
					if (intersection(20, dx1, dy1, 30) == true) {
						// change ball color
						name.targetHit();
						// stop x direction of ball
						name.stopX(target1.getX());
						// change target to hit
						target1.paintTargetHit(g);
						// keep it as hit
						target1Visible = false;
						// change textview to say hit
						runThread1();
					}
					// ditto but with target2
					if (intersection(20, dx2, dy2, 30) == true) {
						name.targetHit();
						name.stopX(target2.getX());
						target2.paintTargetHit(g);
						target2Visible = false;
						runThread1();
					}

				}
			}
		}
	}

	/**
	 * Tells that we never pause.
	 * 
	 * @return indication of whether to pause
	 */
	public boolean doPause() {
		return false;
	}

	/**
	 * Tells that we never stop the animation.
	 * 
	 * @return indication of whether to quit.
	 */
	public boolean doQuit() {
		return false;
	}

	/**
	 * returns whether cannonball has hit a target
	 * 
	 * @param ballRadius
	 *            holds cannonball radius
	 * @param dx
	 *            holds x distance from cannon to target
	 * @param dy
	 *            holds y distance from cannon to target
	 * @param targetRadius
	 *            holds target radius
	 * @return
	 */
	public boolean intersection(float ballRadius, float dx, float dy,
			float targetRadius) {

		// ball and target radius combined and raised to 2nd power to see if
		// bigger then distance x^2 + y^2, if so return true, if not false
		if ((ballRadius + targetRadius) * (ballRadius + targetRadius) > (dx * dx)
				+ (dy * dy)) {
			// there is an intersection
			return true;

		} else {
			// there is not an intersection
			return false;
		}
	}

	/*
	 * External Citation Date: 27 February 2015
	 * 
	 * Problem: Needed to setText of textview in thread
	 * 
	 * Resource:
	 * http://stackoverflow.com/questions/11140285/how-to-use-runonuithread
	 * 
	 * Solution: use this runOnUiThread to access UI
	 */

	/**
	 * used to set textview text using runOnUIThread
	 * 
	 */
	private void runThread1() {

		new Thread() {
			public void run() {
				i = 0;
				while (i++ < 1000) {
					try {
						// must extend Activity to run this
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// if target is hit, say "Target Hit!"
								if (target1Visible == false
										|| target2Visible == false) {
									CannonMainActivity.message
											.setText("Target Hit!");
									// otherwise say nothing
								} else {
									CannonMainActivity.message.setText("");
								}
							}
						});
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	/**
	 * onTouch of the canvas
	 */
	public void onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// reset targets
			target1Visible = true;
			target2Visible = true;
			// have textview say nothing
			runThread1();

			// create new mediaplayer and load cannon sound
			//use context MyApp class to getContext()
			mp = MediaPlayer.create(MyApp.getContext(), R.raw.cannon);
			// play sound
			mp.start();

			// create new cannonball object, add to arraylist cannons
			cannons.add(new CannonBall(20, cHeight - 20));
		}
	}
}// class TextAnimator
