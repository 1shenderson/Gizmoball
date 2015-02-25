package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import controller.FileHandling;
import model.Ball;
import model.CollisionDetails;
import model.VerticalLine;
import model.Walls;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Model extends Observable {

    private ArrayList<VerticalLine> lines;
    private Ball ball;
    private Walls gws;
    private FileHandling file;
    private ArrayList<ArrayList<Object>> gizmoList;

    public Model() {

        // Ball position (25, 25) in pixels. Ball velocity (100, 100) pixels per tick
        ball = new Ball(25, 25, 100, 100);

        // Wall size 500 x 500 pixels
        gws = new Walls(0, 0, 500, 500);

        // Lines added in Main
        lines = new ArrayList<VerticalLine>();

        file = new FileHandling();

        gizmoList = new ArrayList<ArrayList<Object>>();
    }

    public void moveBall() {

        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball

        if (ball != null && !ball.stopped()) {

            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            if (tuc > moveTime) {
                // No collision ...
                ball = movelBallForTime(ball, moveTime);
            } else {
                // We've got a collision in tuc
                ball = movelBallForTime(ball, tuc);
                // Post collision velocity ...
                ball.setVelo(cd.getVelo());
            }

            // Notify observers ... redraw updated view
            this.setChanged();
            this.notifyObservers();
        }

    }

    private Ball movelBallForTime(Ball ball, double time) {

        double newX = 0.0;
        double newY = 0.0;
        double xVel = ball.getVelo().x();
        double yVel = ball.getVelo().y();
        newX = ball.getExactX() + (xVel * time);
        newY = ball.getExactY() + (yVel * time);
        ball.setExactX(newX);
        ball.setExactY(newY);
        return ball;
    }

    private CollisionDetails timeUntilCollision() {
        // Find Time Until Collision and also, if there is a collision, the new speed vector.
        // Create a physics.Circle from Ball
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);

        // Now find shortest time to hit a vertical line or a wall line
        double shortestTime = Double.MAX_VALUE;
        double time = 0.0;

        // Time to collide with 4 walls
        ArrayList<LineSegment> lss = gws.getLineSegments();
        for (LineSegment line : lss) {
            time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
            }
        }

        // Time to collide with any vertical lines
        for (VerticalLine line : lines) {
            LineSegment ls = line.getLineSeg();
            time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
            }
        }
        return new CollisionDetails(shortestTime, newVelo);
    }

    public void saveBoard(File filed, String fileName){
        file.save(gizmoList, filed, fileName);
    }

    public void loadBoard(File filed){
        ArrayList<ArrayList<Object>> gizmoLoad = file.load(filed);

        ArrayList<Object> gizmoInfo = new ArrayList<Object>();
        for(int i = 0; i < gizmoLoad.size(); i++){
            gizmoInfo = gizmoLoad.get(i);
            addLine(new VerticalLine((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (int)gizmoInfo.get(2), (int)gizmoInfo.get(3), (int)gizmoInfo.get(4)));
        }
    }

    public Ball getBall() {
        return ball;
    }

    public ArrayList<VerticalLine> getLines() {
        return lines;
    }

    public void addLine(VerticalLine l) {
        lines.add(l);
        gizmoList.add(l.getGizmoInfo());
    }

    public void setBallSpeed(int x, int y) {
        ball.setVelo(new Vect(x, y));
    }
}
