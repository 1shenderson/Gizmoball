package model.gizmo;

import model.Ball;
import model.Model;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class Absorber extends AbstractSquare {
    private boolean absorbed;   // Boolean value which stores information whether a ball has been absorbed
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
    public Absorber(int x, int y, int width, int height, int velX, int velY, Model parent, String id) {
        super(x, y, width, height, id);
        this.velX = velX;
        this.velY = velY;
        this.parent = parent;
        this.absorbed = false;
    }

    @Override
    /**
     * When absorber is triggered, it will spawn a ball in the bottom right corner
     */
    public void trigger() {
        if (absorbed) {
            double ballX = botRightX - 0.25 * L; // X position of ball to be spawned by the absorber
            double ballY = botRightY - 0.25 * L; // Y position of ball to be spawned by the absorber
            Ball ball = new Ball("", "", ballX, ballY, velX, velY);
            parent.addBall(ball);
        }
    }
}
