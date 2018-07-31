/*
 * Preston McIllece's Project
 * 
 * This class creates a ship to move among the asteroids
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener{
	public static final int SHIP_WIDTH = 40;
	public static final int SHIP_HEIGHT = 25;
	public int LIVES = 5;
	public boolean forward = false;
	public boolean right = false;
	public boolean left = false;
	public boolean bullet = false;
	public boolean created = false;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	//constructor
	public Ship(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
	}

	//draws the ship
	@Override
	public void paint(Graphics brush, Color color) {
		brush.setColor(color);
		
		Point [] points = getPoints();
		int [] xPoints = new int[points.length];
		int [] yPoints = new int[points.length];
		
		for (int i = 0; i < points.length; i++) {
			xPoints[i] = (int) points[i].x;
			yPoints[i] = (int) points[i].y;
		}
		brush.fillPolygon(xPoints, yPoints, points.length);
		

	}

	//moves the ship
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		//does the work for checking if the ship is off the screen
		//and then puts it on the otherside
			if (position.x > Asteroids.SCREEN_WIDTH)
				position.x -= Asteroids.SCREEN_WIDTH;
			else if (position.x < 0)
				position.x += Asteroids.SCREEN_WIDTH;
		    if (position.y > Asteroids.SCREEN_HEIGHT)
				position.y -= Asteroids.SCREEN_HEIGHT;
			else if (position.y < 0)
				position.y += Asteroids.SCREEN_HEIGHT;
		    
		    
		    //checks if the keys are pressed and adjusts the ship's movement
		    if (forward) {
		    	position.x += 3 * Math.cos(Math.toRadians(rotation));
		    	position.y += 3 * Math.sin(Math.toRadians(rotation));
		    }
		    if (left) 
		    	rotate(-5);
		    if (right)
		    	rotate(5);
		    if (bullet && !created) {
		    	Point[] points = getPoints();
		    	Point frontOfShip = points[3];
		    	Bullet b = new Bullet(frontOfShip, rotation);
		    	bullets.add(b);
		    	created = true;
		    }

	}
	
	//returns the bullets that are currently supposed to be on the screen
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}


	//we don't use this method
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//checks if a key is pressed
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP)
			forward = true;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			left =  true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = true;	
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			bullet = true;
			
		}
	}

	//checks if a key is released
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP)
			forward = false;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			left = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = false;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			bullet = false;
			created = false;
		}
	}

}
