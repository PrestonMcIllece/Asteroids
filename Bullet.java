/*
 * Preston McIllece's Asteroids Project
 * 
 * This class creates a bullet object that can be fired from the ship
 */
import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Circle {
	private static final int RADIUS = 3;
	public double rotation;

	//constructor that initializes the bullet
	public Bullet(Point center, double rotation) {
		super(center, RADIUS); // define RADIUS in Bullet class
		this.rotation = rotation;
	}

	//paints the bullet
	@Override
	public void paint(Graphics brush, Color color) {
		// TODO Auto-generated method stub
		brush.setColor(color);
		brush.fillOval((int) center.x, (int) center.y, RADIUS, RADIUS);
	}

	//moves the bullet in whichever direction the ship was
	//facing when it was fired
	@Override
	public void move() {
		center.x += 5 * Math.cos(Math.toRadians(rotation));
		center.y += 5 * Math.sin(Math.toRadians(rotation));
	}
	
	//determines if a bullet is off the screen so that it
	//can be garbage collected
	public boolean outOfBounds() {
		if (center.x > Asteroids.SCREEN_WIDTH|| center.x < 0 || center.y > Asteroids.SCREEN_HEIGHT || center.y < 0)
			return true;
		else
			return false;
	}
	
	//returns the center of the bullet
	public Point getCenter() {
		return center;
	}

}
