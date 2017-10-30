import java.awt.*;

/**
 * Block class to create blocks
 * that the game requires
 * 
 * @author Susan
 *
 */
public class Block extends Rectangle  {
	/**Data member representative of color of block**/
	private Color color;
	/**Data member which indicates whether block has been destroyed**/
	private boolean destroyed;
	
	/**
	 * Constructor for Block class
	 * @param xLoc of block
	 * @param yLoc of block
	 * @param w is width of block
	 * @param h is height of block
	 * @param c is color of block
	 */
	public Block(int xLoc, int yLoc, int w, int h, Color c){
		setBounds(xLoc,yLoc,w, h);
		color=c;
		destroyed = false;
	}
	
	/**
	 * Draws the block
	 * 
	 * @param g is Graphics to allow drawing
	 */
	public void draw (Graphics g){
		g.setColor(color);
		g.fillRect(x,y,width,height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
	}
	
	/**
	 * Method if the block is destroyed
	 */
	public void destroy() {
		destroyed = true;
	}
	
	/**
	 * Method to check if block is destroyed
	 * @return true if it is destroyed and false if it is not
	 */
	public boolean isDestroyed() {
		return destroyed;
	}
	
}
