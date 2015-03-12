package model.gizmo;

import model.Ball;
import model.Model;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class Absorber extends AbstractSquare {
    private int absorbed;       // Number of absorbed balls. Not a boolean to support multiple balls.
    private double velX;        // Starting X velocity of the ball spawned by this absorber
    private double velY;        // Starting Y velocity of the ball spawned by this absorber
    private Model parent;       // Parent board where the ball should be spawned

    /**
     * Constructor for the Absorber
     * @param x Position X on the board
     * @param y Position Y on the board
     * @param width Width in L. Has to be at least 1.
     * @param height Height in L. Has to be at least 1.
     * @param velX Starting X velocity of ball spawned by this absorber
     * @param velY Starting Y velocity of ball spawned by this absorber
     * @param parent Parent board containing the absorber
     * @param id ID of the gizmo
     */
    public Absorber(String gizmoType, String id, int x, int y, int width, int height) {
    	super(gizmoType, id, x, y);
        velX = 0;
        velY = -50;
        this.absorbed = 0;
//        this.addTrigger(this);  // TODO Remove this test method. At the moment it will trigger itself when hit.
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
            double ballX = botRightX - 0.5 * L; // X position of ball to be spawned by the absorber
            double ballY = topLeftY - 0.25 * L; // Y position of ball to be spawned by the absorber
            Ball ball = new Ball("", "", ballX, ballY, velX, velY);
            ball.setIgnoreAbsorber(true);
            parent.addBall(ball);
            absorbed--;
            // TODO Spawn the ball INSIDE of the absorber, not outside.
        }
    }
}
