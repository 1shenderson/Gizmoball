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
    
    
    
    /**
     * Constructor for the Absorber
     * @param x Position X on the board
     * @param y Position Y on the board
     * @param width Width in L. Has to be at least 1.
     * @param height Height in L. Has to be at least 1.
     * @param parent Parent board containing the absorber
     * @param id ID of the gizmo
     */
    public Absorber(String gizmoType, String id, int x, int y, int width, int height, Board parent) {
    	super(gizmoType, id, x, y);
    	this.parent = parent;
        velX = 0;
        velY = -50 * L;
        this.absorbed = 0;
        color = Color.MAGENTA;
        System.out.println("width is " + width);
        this.width = width;
        this.height = height;
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
