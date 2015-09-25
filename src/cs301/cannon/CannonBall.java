package cs301.cannon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


/**
 * class that creates a cannonball and handles it's movement on a canvas
 * 
 * @author Shamus Murray
 * @version Feburary 2015
 */
public class CannonBall {

	// moves ball's location
	protected int currentPositionX; // my x start coordinate
	protected int currentPositionY; // my y start coordinate

	// vars used to calculate projectile motion
	private int currentVelocityY = -5;
	private int currentVelocityX = 10;
	private int initialVelocityY = CannonMainActivity.angle + 20;
	private int gravity = 3;
	private int initialGravity = 3;

	boolean freefall = false; // is cannonball at peak of height?
	boolean targetHit = false; // has ball hit a target?

	private int centerX, centerY; // holds center of cannonball

	private int cWidth; // holds width of canvas

	/**
	 * Constructor
	 * 
	 * @param startX
	 *            start of x position
	 * 
	 * @param startY
	 *            start of y position
	 */
	public CannonBall(int startX, int startY) {
		this.currentPositionX = startX;
		this.currentPositionY = startY;
	}

	/**
	 * Method in charge of painting cannonball in motion
	 * 
	 * @param g
	 *            the graphics object on which to draw
	 */
	public void paintCannonBall(Canvas g) {
		// get width of canvas
		cWidth = g.getWidth();
		// cannonball will be red if it hasn't hit target, yellow if so
		Paint paint = new Paint();
		if (targetHit == false) {
			paint.setColor(Color.RED);
		} else {
			paint.setColor(Color.YELLOW);
		}

		// call projectile motion methods
		initialVelocity();
		Gravity();

		// draw the cannonball according to coordinates
		RectF ball = new RectF(currentPositionX - 10, currentPositionY - 10,
				currentPositionX + 10, currentPositionY + 10);
		g.drawOval(ball, paint);

		// constantly update values to get center of ball as it moves
		centerX = (int) ball.centerX();
		centerY = (int) ball.centerY();

	}

	/**
	 * Method in charge of applying upward velocity to cannonball x and y coords
	 * 
	 */
	public void initialVelocity() {
		// if there is no more initial velocity, switch to free fall
		if (initialVelocityY <= 0) {
			freefall = true;
		}

		// if still initial velocity...
		if (freefall == false) {
			// ball slows when going up
			this.currentPositionY = this.currentPositionY
					- this.initialVelocityY;

			// velocity is slowed by gravity
			initialVelocityY = initialVelocityY - initialGravity;

			// if we reach edge of screen, stop ball in x direction
			if (this.currentPositionX >= cWidth - 60) {
				currentPositionX = cWidth - 60;
			} else {
				// otherwise keep ball constantly traveling in x
				currentPositionX = currentPositionX + currentVelocityX * 5;
			}
		}
	}

	/*
	 * External Citation Date: 27 February 2015
	 * 
	 * Problem: Needed bouncing effect for gravity
	 * 
	 * Resource:
	 * http://stackoverflow.com/questions/23175625/gravity-in-a-java-ball
	 * -bouncing-program-that-refreshes-every-game-tick
	 * 
	 * Solution: modified this code snippet to aid in gravity bounce
	 */

	/**
	 * Method in charge of applying gravity to cannonball x and y coords
	 * 
	 */
	public void Gravity() {
		// if in freefall
		if (freefall == true) {
			// slowly increase y values to speed ball descent
			this.currentPositionY = this.currentPositionY
					+ this.currentVelocityY;
			// if ball reaches ground, invert velocity to bounce up
			if (this.currentPositionY >= 587) {
				this.currentPositionY = 587;
				this.currentVelocityY = -1 * this.currentVelocityY / 2;
			}
			// apply gravity to y coord
			currentVelocityY = currentVelocityY + gravity;

			// if we reach edge of screen, stop ball in x direction
			if (this.currentPositionX >= cWidth - 60) {
				currentPositionX = cWidth - 60;
			}
			// otherwise keep ball constantly traveling in x
			currentPositionX = currentPositionX + currentVelocityX * 5;
		}
	}

	/**
	 * returns center x coord of cannonball
	 * 
	 */
	public int getX() {
		return centerX;
	}

	/**
	 * returns center y coord of cannonball
	 * 
	 */
	public int getY() {
		return centerY;
	}

	/**
	 * stops the x travel of ball (hits target)
	 * 
	 */
	public void stopX(int targetX) {
		currentPositionX = targetX;
		currentVelocityX = 0;
	}

	/**
	 * used to change color of ball if hit
	 * 
	 */
	public void targetHit() {
		targetHit = true;
	}

}
