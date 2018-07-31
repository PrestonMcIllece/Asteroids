/*
 * Preston McIllece's Asteroid's Project
 * 
 * This class extends Polygon and creates methods for an Asteroid
 */
import java.awt.Color;
import java.awt.Graphics;

public class Asteroid extends Polygon {
	
	//constructor creates an asteroid, which is a polygon
	public Asteroid(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
		// TODO Auto-generated constructor stub
	}

	//paints an asteroid on the screen
	@Override
	public void paint(Graphics brush, Color color) {
		// TODO Auto-generated method stub
		Point [] points;
		brush.setColor(color);
		
		points = getPoints();
		int [] xPoints = new int[points.length];
		int [] yPoints = new int[points.length];
		
		
		for (int i = 0; i < points.length; i++) {
			xPoints[i] = (int) points[i].x;
			yPoints[i] = (int) points[i].y;
		}
		
		brush.drawPolygon(xPoints, yPoints, points.length);
	}

	//moves the asteroid on the screen
	@Override
	public void move() {
		position.x += Math.cos(Math.toRadians(rotation));
		position.y += Math.sin(Math.toRadians(rotation));

		// TO-DO
		/**
		 * Have asteroid move back on the screen once they go off the screen.
		 *
		 * You will do this by checking the value of position.x and position.y
		 * and determine if they are outside of the bounds of the screen
		 * specified by Asteroids.SCREEN_WIDTH and Asteroids.SCREEN_HEIGHT
		 * If so, reposition the x and/or y coordinates.
		 * 
		 * i.e. if an asteroid moves off the right-side of the screen
		 * have it re-appear on the left side of the screen.
		 */
		if (position.x > Asteroids.SCREEN_WIDTH)
			position.x -= Asteroids.SCREEN_WIDTH;
		else if (position.x < 0)
			position.x += Asteroids.SCREEN_WIDTH;
	    if (position.y > Asteroids.SCREEN_HEIGHT)
			position.y -= Asteroids.SCREEN_HEIGHT;
		else if (position.y < 0)
			position.y += Asteroids.SCREEN_HEIGHT;
		}

} 
