/* 
 * Preston McIllece's Asteroids Project
 * 
 * This class creates stars in the night sky
 */
import java.awt.Color;
import java.awt.Graphics;

public class Star extends Circle {

	//constructor initializing instance data as well as superclass data
	public Star(Point center, int radius) {
		super(center, radius);
		// TODO Auto-generated constructor stub
	}

	//paints a star on the screen
	@Override
	public void paint(Graphics brush, Color color) {
		// TODO Auto-generated method stub
		brush.setColor(color);
		brush.fillOval((int)center.x, (int)center.y, radius, radius);

	}

	//we don't move stars so we don't need this
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
