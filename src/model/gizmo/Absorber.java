package model.gizmo;

import java.awt.Color;

import model.Ball;
import model.Board;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class Absorber extends AbstractSquare {
	private int absorbed;       // Number of absorbed balls. Not a boolean to support multiple balls.
	private double velX;        // Starting X velocity of the ball spawned by this absorber
	private double velY;        // Starting Y velocity of the ball spawned by this absorber
	private Board parent;       // Parent board where the ball should be spawned
	private int x2;
	private int y2;



	/**
	 * Constructor for the Absorber
	 * @param x Position X on the board
	 * @param y Position Y on the board
	 * @param width Width in L. Has to be at least 1.
	 * @param height Height in L. Has to be at least 1.
	 * @param parent Parent board containing the absorber
	 * @param id ID of the gizmo
	 */
	public Absorber(String gizmoType, String id, int x, int y, int x2, int y2, Board parent) {
		super(gizmoType, id, x, y);
		this.parent = parent;
		this.x2 = x2;
		this.y2 = y2;
		velX = 0;
		velY = -50 * L;
		this.absorbed = 0;
		color = Color.MAGENTA;
		System.out.println("width is " + x2);
		if(x < x2){
			this.width = x2 - x;;
		}
		else if(x > x2){
			this.width = x - x2;
		}
		if(y < y2){
			this.height = y2 - y;
		}
		else if(y > y2){
			this.height = y - y2;
		}
		setPoints(x, y);

		System.out.println("width is " + this.width);


		this.addTrigger(this);  // TODO Remove this test method. At the moment it will trigger itself when hit.
	}

	public int getWidth(){
		return width;
	}

	/**
	 * Increments the number of absorbed balls.
	 */
	public void absorb() {
		absorbed++;
	}

	@Override
	public void setPoints(int startX, int startY){
		if(startX < x2){
			topLeftX = startX * L;
			botLeftX = topLeftX;
			topRightX = x2 * L;
			botRightX = topRightX;
		}
		else if(startX > x2){
			topLeftX = x2 * L;
			botLeftX = topLeftX;
			topRightX = startX * L;
			botRightX = topRightX;
		}
		if(startY < y2){
			topLeftY = startY * L;
			botLeftY = topLeftY;
			topRightY = y2 * L;
			botRightY = topRightY;
		}
		else if(startY > y2){
			topLeftY = y2 * L;
			botLeftY = topLeftY;
			topRightY = startY * L;
			botRightY = topRightY;
		}
	}


	@Override
	/**
	 * When absorber is triggered, it will spawn a ball in the bottom right corner
	 */
	public void trigger() {
		if (absorbed > 0) {
			double ballX = botRightX / L - 1; // X position of ball to be spawned by the absorber
			double ballY = topLeftY / L - 1; // Y position of ball to be spawned by the absorber
			String ballId = parent.addBall(null, ballX, ballY);
			parent.setBallSpeed(ballId, (int) velX, (int) velY);
			absorbed--;
			// TODO Spawn the ball INSIDE of the absorber, not outside.
		}
	}

	@Override
	public String toString(){
		return "Absorber " + getID() + " " + getX() + " " + getY() + " " + getWidth() + " " + getHeight();
	}

	@Override
	public Gizmo moveTo(int x, int y) {
		return null;    // Absorber can't be moved (because of different possible sizes, it could clip into walls)
	}
}
