import java.awt.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

/**
 * Frame class which is where all the graphics 
 * of the game is drawn and all the objects are 
 * created in order for the GUI aspect of the 
 * program is implemented 
 * 
 * @author Susan
 *
 */
public class Frame extends JFrame {
	/**
	 * Main function where the frame/window is created
	 * @param args is standard to use main in java
	 */
	public static void main(String[] args) {
		Frame f = new Frame();
	}

	/**
	 * Frame constructor that sets the size
	 * of the window and creates the instance
	 * of Panel where GUI is implemented
	 */
	public Frame() {
		setBounds(100, 100, 600, 500);
		setTitle("BreakOut Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel p = new Panel();
		setContentPane(p);
		Thread t = new Thread(p);
		t.start();
		setVisible(true);
	}

	/**
	 * Panel class where the objects are created 
	 * and paintComponent can be overriden to 
	 * draw all the objects onto the screen
	 * @author Susan
	 *
	 */
	public class Panel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
		/**Data member for array of blocks**/
		private Block[] blocks;
		/**Data member for block count**/
		private int blockCount;
		/**Data member for ball count**/
		private int ballCount;
		/**Data member for the paddle gamplay**/
		private Paddle paddle;
		/**Data member for ball for gameplay**/
		private Ball ball;
		/**Data member to indicate whether the ball is release**/
		private boolean ballRelease;
		/**Data member for blocks width**/
		private int blockWidth = 50;
		/**Data member for image**/
		private BufferedImage image;
		/**Data member for image for pause screen**/
		private BufferedImage pawsed;
		/**Data member to indicate whether ball is destoryed**/
		private boolean ballDestroyed;
		/**Data member for sound**/
		private boolean soundPlayed;
		/**Data member to indicate whether game is paused**/
		private boolean paused;
		/**Data member to indicate how many blocks are left**/
		private int blocksLeft;

		/**
		 * The constructor for panel where objects are
		 * instantiated 
		 */
		public Panel() {
			blockCount = 36;
			blocksLeft=blockCount;
			paused = false;
			int blockHeight = 20;
			int startBlX = 40;
			int startBlY = 50;
			blocks = new Block[blockCount];
			ballCount = 3;
			setBackground(Color.BLUE);
			ballDestroyed = false;
			soundPlayed = false;

			try {
				image = ImageIO.read(new File("kitty.jpg"));
				pawsed = ImageIO.read(new File("pawsed.png"));
			} catch (IOException ex) {
				System.out.println(ex);
			}

			for (int i = 0; i < blockCount; i++) {

				if (i < 10) {
					blocks[i] = new Block(startBlX * (i + 1) + (10 * i), startBlY, blockWidth, blockHeight, Color.BLUE);
				} else if (i > 9 && i < 20) {
					blocks[i] = new Block(startBlX * (i - 9) + (10 * (i - 10)), startBlY + 20, blockWidth, blockHeight,
							Color.CYAN);
				} else if (i > 19 && i < 30) {
					blocks[i] = new Block(startBlX * (i - 19) + (10 * (i - 20)), startBlY + 40, blockWidth, blockHeight,
							Color.LIGHT_GRAY);
				} else {
					blocks[i] = new Block(startBlX * (i - 26) + (10 * (i - 32)), startBlY + 60, blockWidth, blockHeight,
							Color.WHITE);
				}
			}

			paddle = new Paddle(280, 312, blockWidth, blockHeight / 2);
			ball = new Ball((int) paddle.getX() + blockWidth / 2, (int) paddle.getY() - blockHeight / 2, 5, Color.WHITE,
					2);

			addMouseListener(this);
			addKeyListener(this);
			setFocusable(true);
			addMouseMotionListener(this);

		}

		/**
		 * Overriden method from extending Jpanel,
		 * this is where all the drawing is implemented
		 * 
		 * @param g is Graphics which is used to draw items on the screen
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			boolean touchPaddle=false;
			boolean touchBlock=false;

			g.drawImage(image, 0, 0, this);

			if (paused) {
				Font font = new Font("Verdana", Font.BOLD, 48);
				g.setFont(font);
				g.drawImage(pawsed, 0, 0, this);
				g.drawString("PAWSED", 170, 200);
			} else {
				if (ballCount == 0 || blocksLeft == 0) {
					Font font = new Font("Verdana", Font.BOLD, 48);
					g.setFont(font);
					g.drawString("GAME OVER", 130, 200);
					if (!soundPlayed) {
						System.out.println("Game over");
						System.out.println("balls: " + ballCount);
						System.out.println("blocks: " + blocksLeft);
						try {
							// Open an audio input stream.
							File soundFile = new File("cat.wav");

							if (blocksLeft == 0) {
								soundFile = new File("purr.wav");
							}
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
							// Get a sound clip resource.
							Clip clip = AudioSystem.getClip();
							// Open audio clip and load samples from the audio
							// input
							// stream.
							clip.open(audioIn);
							clip.start();
							clip.loop(0);
							soundPlayed = true;
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				} else {

					for (Block b : blocks) {
						if (!b.isDestroyed()) {
							b.draw(g);
						}
					}
					paddle.draw(g);
					// Paddle logic
					if ((paddle.contains(new Point(ball.x, ball.y)) || paddle.contains(new Point((int) (ball.x + ball.width / 2.0), ball.y + ball.height)))) {
						System.out.println("Paddle bounce");
						touchPaddle =true;
					}

					// Block logic
					for (Block b : blocks) {
						if ((b.contains(new Point(ball.x, ball.y)) || b.contains(new Point((int) (ball.x + ball.width / 2.0), ball.y + ball.height)))
								&& !b.isDestroyed()) {
							System.out.println("Block bounce");
							touchPaddle=true;
							b.destroy();
							blocksLeft--;
						}
					}
					if (ballRelease == true) {
						ball.draw(g);
						if (ball.move(getWidth(), getHeight(), paddle,touchPaddle, touchBlock)) {
							ballCount--;
							System.out.println("Ball destroyed!");
							long seed = System.nanoTime();
							Random generator = new Random(seed);
							int ballSpeed = generator.nextInt(4) + 1;
							System.out.println("sp: " + ballSpeed);
							ball = new Ball((int) (paddle.getX() + paddle.getWidth() / 2.0) - 1,
									(int) (paddle.getY() - paddle.getHeight() - 2), 5, Color.WHITE, ballSpeed);

							ballRelease = false;
						}
					} else {
						ball.setLocation((int) (paddle.getX() + paddle.getWidth() / 2.0) - 2,
								(int) (paddle.getY() - paddle.getHeight() - 1));
						ball.draw(g);
					}

					g.drawString("ball(s):" + ballCount, 500, 450);
				}
			}

		}

		/**
		 * Run method which calls repaint
		 * to make the program redraw the screen
		 */
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(16); // ~60 fps
				} catch (InterruptedException e) {
				}
			}
		}

		/**
		 * Method that calls specific actions on paddle 
		 * depending on whether the keys on the board 
		 * 
		 *@param e is KeyEvent which indicates whether 
		 *an action occurred
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (!paused) {
				if (keyCode == KeyEvent.VK_LEFT) {
					System.out.println("L");
					paddle.move((int) (paddle.getX() - 12), getWidth());
				}
				if (keyCode == KeyEvent.VK_RIGHT) {
					System.out.println("R");
					paddle.move((int) (paddle.getX() + 12), getWidth());
				}
			}
			if (keyCode == KeyEvent.VK_P) {
				System.out.println("P");
				paused = true;
			}
			if (keyCode == KeyEvent.VK_S) {
				System.out.println("S");
				paused = false;
			}

			repaint();

		}

		/**
		 * Overriden method when the space bar is pressed 
		 * will set the ballRelease to true
		 * 
		 * @param e indicates whether an action occurred
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if (!paused) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					ballRelease = true;
				}
			}

		}

		/**
		 *Overriden method when the mouse is moved the 
		 * paddle moves as well
		 * 
		 * @param e indicates whether an action occurred
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			if (!paused) {
				paddle.move(e.getX(), getWidth());
			}
		}

		/**
		 * Method when the mouse is pressed 
		 * will set the ballRelease to true
		 * 
		 * @param e indicates whether an action occurred
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!paused) {
				ballRelease = true;
			}
		}

		
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}