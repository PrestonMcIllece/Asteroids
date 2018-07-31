/*
 * Preston McIllece's Asteroid Project
/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */
import java.awt.*;
import java.util.*;

public class Asteroids extends Game {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	public boolean shipCollision = false;
	public boolean bulletCollision = false;
	public int collisionTimer = 100;
	public int starTimer = 5;
	public int secondTimer = 5;

	static int counter = 0;

	private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();
	private Star[] randomStars;

	private Ship ship;

	//constructor
	public Asteroids() {
		super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();

		// create a number of random asteroid objects
		randomAsteroids = createRandomAsteroids(50,60,30);
		randomStars = createStars(80, 5);
		// create the ship
		ship = createShip();

		this.addKeyListener(ship);
	}

	// private helper method to create the Ship
	private Ship createShip() {
		// Look of ship
		Point[] shipShape = {
				new Point(0, 0),
				new Point(Ship.SHIP_WIDTH/3.5, Ship.SHIP_HEIGHT/2),
				new Point(0, Ship.SHIP_HEIGHT),
				new Point(Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT/2)
		};
		// Set ship at the middle of the screen
		Point startingPosition = new Point((width -Ship.SHIP_WIDTH)/2, (height - Ship.SHIP_HEIGHT)/2);
		int startingRotation = 0; // Start facing to the right
		return new Ship(shipShape, startingPosition, startingRotation);
	}

	//  Create an array of random asteroids
	private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
			int minAsteroidWidth) {
		java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);

		for(int i = 0; i < numberOfAsteroids; ++i) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation));
		}
		return asteroids;
	}

	// Create a certain number of stars with a given max radius
	public Star[] createStars(int numberOfStars, int maxRadius) {
		Star[] stars = new Star[numberOfStars];
		for(int i = 0; i < numberOfStars; ++i) {
			Point center = new Point
					(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);


			int radius = (int) (Math.random() * maxRadius);
			if(radius < 1) {
				radius = 1;
			}
			stars[i] = new Star(center, radius);
		}


		return stars;
	}

	//draws everything
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter,10,10);
		brush.drawString("Lives: " + ship.LIVES, 10, 30);

		// display the random asteroids
		for (Asteroid asteroid : randomAsteroids) {
			asteroid.paint(brush,Color.white);
			asteroid.move();
			if (asteroid.collision(ship))
				shipCollision = true;
		}

		//makes the stars twinkle
		Color color = Color.yellow;
		starTimer--;
		if (starTimer <= 0) {
			color = Color.black;
			secondTimer--;
			if (secondTimer == 0) {
				starTimer = 5;
				secondTimer = 5;
				color = Color.yellow;
			}
		}
		//display the random stars
		for (Star star : randomStars) {
			star.paint(brush, color);
		}
		

		ArrayList<Bullet> needToBeRemoved = new ArrayList<Bullet>();
		ArrayList<Bullet> currentBullets = ship.getBullets();
		ArrayList<Asteroid> removableAsteroids = new ArrayList<Asteroid>();
		// display the bullets
		for (Bullet bullet : currentBullets) {
			bullet.paint(brush,Color.white);
			bullet.move();
			if (bullet.outOfBounds())
				needToBeRemoved.add(bullet);
			Point centerOfBullet = bullet.getCenter();
			for(int i = 0; i < randomAsteroids.size(); i++) {
				if (randomAsteroids.get(i).contains(centerOfBullet)) {
					removableAsteroids.add(randomAsteroids.get(i));
					needToBeRemoved.add(bullet);
				}
				
			}
		}

		//removes necessary bullets and asteroids
		for (Bullet bullet : needToBeRemoved)
			currentBullets.remove(bullet);
		for (Asteroid a : removableAsteroids)
			randomAsteroids.remove(a);
		/**
		 * The above for loop (known as a "for each" loop)
		 * is equival  xxzzent to what is shown below.
		 */

		/**
		for (int i = 0; i < randomAsteroids.size(); i++) {
			randomAsteroids.get(i).paint(brush, Color.white);
			randomAsteroids.get(i).move();

		}
		 */

		//checks for collision, changes color and sets the timer for changing the color back
		if (shipCollision) {
			ship.paint(brush, Color.red);
			collisionTimer--;

			if (collisionTimer == 0) {
				shipCollision = false;
				ship.paint(brush, Color.magenta);
				collisionTimer = 100; 
				ship.LIVES--;
			}
		}   
		else
			ship.paint(brush, Color.magenta);

		ship.move();
		
		//checks if you've won
		if (randomAsteroids.size() == 0) {
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.white);
			brush.drawString("Congratulations, you won!", 10, 10);
			on = false;
		}
		
		//checks if you've lost
		if (ship.LIVES == 0) {
			brush.setColor(Color.black);
			brush.fillRect(0, 0, width, height);
			brush.setColor(Color.white);
			brush.drawString("You ran out of lives, you lost!", 10, 10);
			on = false;
		}
			
	}

	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		a.repaint();
	}
}