package cs301.cannon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


/**
 * class that creates a target and handles it being hit
 * 
 * @author Shamus Murray
 * @version Feburary 2015
 */
public class Target {

	private int currentPositionX; // my x start coordinate
	private int currentPositionY; // my y start coordinate
	
	private RectF target; //shape for outer target
	private RectF targetOuter; //...for inner target
	Paint paint = new Paint(); // paint object

	/**
	 * Constructor
	 * 
	 * @param startX
	 *            start of x position
	 * 
	 * @param startY
	 *            start of y position
	 */
	public Target(int startX, int startY) {
		this.currentPositionX = startX;
		this.currentPositionY = startY;
	}

	/**
	 * Method in charge of painting normal target
	 * 
	 * @param g
	 *            the graphics object on which to draw
	 */
	public void paintTarget(Canvas g) {
		//paint target (in and out shell) based off postion given
		target = new RectF(currentPositionX - 20, currentPositionY - 20,
				currentPositionX + 20, currentPositionY + 20);
		targetOuter = new RectF(currentPositionX - 30, currentPositionY - 30,
				currentPositionX + 30, currentPositionY + 30);
		//paint outer first
		paint.setColor(Color.MAGENTA);
		g.drawOval(targetOuter, paint);
		//then paint inner
		paint.setColor(Color.GREEN);
		g.drawOval(target, paint);

	}// paint
	
	/**
	 * Method in charge of painting a hit target
	 * 
	 * @param g
	 *            the graphics object on which to draw
	 */
	public void paintTargetHit(Canvas g) {
		//paint target (in and out shell) based off postion given
		target = new RectF(currentPositionX - 20, currentPositionY - 20,
				currentPositionX + 20, currentPositionY + 20);
		targetOuter = new RectF(currentPositionX - 30, currentPositionY - 30,
				currentPositionX + 30, currentPositionY + 30);
		//paint outer
		paint.setColor(Color.MAGENTA);
		g.drawOval(targetOuter, paint);
		//inner will be red to show it has been hit
		paint.setColor(Color.RED);
		g.drawOval(target, paint);

	}// paint

	/**
	 * returns center x coord of target
	 * 
	 */
	public int getX() {
		return (int) target.centerX();
	}
	
	/**
	 * returns center y coord of target
	 * 
	 */
	public int getY() {
		return (int) target.centerY();
	}
}
