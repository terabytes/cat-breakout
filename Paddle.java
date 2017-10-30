import java.awt.*;

/**
 * Paddle class that creates paddles
 * 
 * @author Susan
 *
 */
public class Paddle extends Rectangle {
	/**Data member to display a string, for debugging**/
	private String message;
	/** Data member representative of the x direction **/
	private int dx;
	
	/**
	 * Paddle constructor 
	 * 
	 * @param xLoc of the paddle
	 * @param yLoc of the paddle
	 * @param w is width of the paddle
	 * @param h is height of the paddle
	 */
	public Paddle(int xLoc, int yLoc, int w,int h){
		super(xLoc,yLoc,w,h);
		setBounds(xLoc, yLoc, w, 10);
		message = "";
	}
	
	/**
	 * Draws the paddle
	 * 
	 * @param g is Graphics used to draw 
	 */
	public void draw (Graphics g){
		g.setColor(Color.black);
		g.fillRect(x,y,width,10);
		g.setColor(Color.white);
		g.drawRect(x, y, width, 10);
		g.drawString(message, 400, 450);
	}
	
	/**
	 * Moves the paddle
	 * 
	 * @param newX is the change in x for paddle
	 * @param winWidth is the window's width
	 */
	public void move(int newX, int winWidth) {
		dx = 0;
		
		if(newX < winWidth-width && newX > 0){
			dx = newX - x;
		} 
		translate( dx , 0 );  
		message = "("+ x + ", " + y + ")";
		
		System.out.println("dx: " + dx);
	}
	
	/**
	 * Getter for the change in x of the paddle
	 * @return dx is change in direction of paddle
	 */
	public int getXDirection() {
		return dx;
	}
	
	
}
