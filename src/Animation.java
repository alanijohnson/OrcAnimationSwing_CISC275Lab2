//Yat Tung Chan and Alani Johnson
//T Harvey
//based loosely on http://www.java2s.com/Code/JavaAPI/java.awt/GraphicsdrawImageImageimgintxintyImageObserverob.htm

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Animation extends JPanel {

	final int frameCount = 10;
	int picNum = 0;
	BufferedImage[][] pics;
	int xloc = 0;
	int yloc = 0;
	final int xIncr = 8;
	final int yIncr = 2;
	final static int frameWidth = 500;
	final static int frameHeight = 300;
	final static int imgWidth = 165;
	final static int imgHeight = 165;
	boolean x_inc = true;
	boolean y_inc = true;
	
	// Override this JPanel's paint method to cycle through picture array and
	// draw images
	public void paint(Graphics g) {
		picNum = (picNum + 1) % frameCount;
		
		//flags for traveling outside of the frame to change direction
		
		if (xloc >= (frameWidth - 125)) { 
			//the 125 is so the image does not completely go off the screen
			x_inc = false;
		} else if (xloc <= 0) {
			x_inc = true;
		}
		
		if (yloc >= (frameHeight - 150)) {
			//the 150 is so the image does not completely go off the screen
			y_inc = false;
		} else if (yloc <= 0) {
			y_inc = true;
		}
		
		//pic direction changing doesn't account for north, east, south, west
	    //bouncing off the wall
		if (x_inc && y_inc) {
			g.drawImage(pics[0][picNum], xloc += xIncr, yloc += yIncr, Color.gray, this);
		} else if (!x_inc && y_inc) {
			g.drawImage(pics[1][picNum], xloc -= xIncr, yloc += yIncr, Color.gray, this);
		} else if (x_inc && !y_inc) {
			g.drawImage(pics[2][picNum], xloc += xIncr, yloc -= yIncr, Color.gray, this);
		} else {
			g.drawImage(pics[3][picNum], xloc -= xIncr, yloc -= yIncr, Color.gray, this);
		}
	}

	// Make frame, loop on repaint and wait
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Animation());
		frame.setBackground(Color.gray);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);
		for (int i = 0; i < 1000; i++) {
			frame.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Constructor: get image, segment and store in array
	public Animation() {
		pics = new BufferedImage[8][10];

		//creates the image in each directions
		BufferedImage img1 = createImage("southeast");
		BufferedImage img2 = createImage("southwest");
		BufferedImage img3 = createImage("northeast");
		BufferedImage img4 = createImage("northwest");
		BufferedImage img5 = createImage("north");
		BufferedImage img6 = createImage("east");
		BufferedImage img7 = createImage("south");
		BufferedImage img8 = createImage("west");

		//assign each image to a position in the 2-d array "pics"
		//and the for loop is to get all the sub images from each direction
		for (int i = 0; i < frameCount; i++) {
			pics[0][i] = img1.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			pics[1][i] = img2.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			pics[2][i] = img3.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			pics[3][i] = img4.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			pics[4][i] = img5.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			pics[5][i] = img6.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			pics[6][i] = img7.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			pics[7][i] = img8.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
		}
		// TODO: Change this constructor so that at least eight orc animation
		// pngs are loaded
	}

	// Read image from file and return
	private BufferedImage createImage(String dir) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("images/orc/orc_forward_"+dir+".png"));
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
