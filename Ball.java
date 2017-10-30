import java.awt.*;

/**
 * The ball class, the ball bounces around the screen. It has a location, a
 * size, a color, a speed, and x and y direction.
 * 
 * @author Susan
 *
 */
public class Ball extends Rectangle {

	/** Data member representative of the color **/
	private Color color;
	/** Data member representative of the speed **/
	private double speed;
	/** Data member representative of the x direction **/
	private int dx;
	/** Data member representative of the y direction **/
	private int dy;
	// private String message;

	/**
	 * Ball constructor 
	 * @param x location of ball
	 * @param y location of ball
	 * @param radius of ball
	 * @param c is color of ball
	 * @param sp is speed of ball
	 */
	public Ball(int x, int y, int radius, Color c, int sp) {
		setBounds(x, y, 2 * radius, 2 * radius);
		dx = sp;
		dy = -sp;
		color = c;
		// message = "";
	}

	/**
	 * Draws the ball
	 * @param g is of Graphics which is used for drawing
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
		// g.drawString(message, 200, 450);
	}

	/**
	 * Move function which changes the location of the ball
	 * @param winWidth is the Window's width
	 * @param winHeight is the Window's height
	 * @param p is the paddle
	 * @param tP is true if the ball touches the paddle or false if it does not
	 * @param tB is true if the ball touches a blcok and false if it does not
	 * @return true if the ball is destroyed and false if it doesn't hit the bottom of the window
	 */
	public boolean move(int winWidth, int winHeight, Paddle p, boolean tP, boolean tB) {
		boolean ballDestroyed = false;
		// Paddle logic
		if (tP) {
			System.out.println("Paddle bounce");
			if (dx == 0) {
				// If ball is going up and down
				// then the ball will go in dir of paddle on contact
				dx = p.getXDirection();
				dy = -dy;
				// Simple friction
			} else if ((p.getXDirection() < 0 && dx < 0) || (p.getXDirection() > 0 && dx > 0)) {
				dy = -dy; // If ball moves same dir of paddle
			} else {
				dx = (dx / 2);// If ball moves opp dir of paddle, ball bounce
								// more directly upward
				dy = -dy;
			}

		}

		// Block logic

		if (tB) {
			System.out.println("Block bounce");
			dy = -dy;
		}

		if (dx > 0 && x > winWidth - width) {
			dx = -dx;
		}

		if (dx < 0 && x < 0) {
			dx = -dx;
		}

		// Hit bottom logic
		if (dy > 0 && y > winHeight - height) {
			System.out.println("Hit Bottom");
			ballDestroyed = true;
			dy = -dy;
		}

		if (dy < 0 && y < 0) {

			dy = -dy;
		}
		translate(dx, dy);
		// message = "(" + x + ", " + y + ") @ (" + dx + ", " + dy + ")";
		return ballDestroyed;
	}

}
