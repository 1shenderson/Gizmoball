package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

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
	private Ball ball2;
	private CircleGizmo circle;
	private CircleGizmo circle2;
	private SquareGizmo square;
	private SquareGizmo square2;
	private SquareGizmo square3;
	private SquareGizmo square4;
	private SquareGizmo square5;


	private Walls gws;
	private List<Ball> ballsList;
	private List<CircleGizmo> circlesList;
	private List<SquareGizmo> squaresList;
	private List<TriangleGizmo> triangleList;


	public Model() {

		ballsList = new ArrayList<Ball>();
		circlesList = new ArrayList<CircleGizmo>();
		squaresList = new ArrayList<SquareGizmo>();
		triangleList = new ArrayList<TriangleGizmo>();
		// Ball position (25, 25) in pixels. Ball velocity (100, 100) pixels per tick
		ball = new Ball(25, 25, 100, 100);
		ball2 = new Ball(300, 300, 100, 100);
		Ball ball3 = new Ball(230, 256, 100, 100);
		Ball ball4 = new Ball(450, 256, 100, 100);
		Ball ball5 = new Ball(230, 370, 100, 100);
		Ball ball6 = new Ball(150, 256, 100, 100);
		Ball ball7 = new Ball(350, 250, 100, 100);

		circle = new CircleGizmo(180,47);
		circle2 = new CircleGizmo(140,50);
		CircleGizmo circle3 = new CircleGizmo(470,160);
		CircleGizmo circle4 = new CircleGizmo(70,400);
		CircleGizmo circle5 = new CircleGizmo(362,390);

		square = new SquareGizmo(244,90);
		square2 = new SquareGizmo(300,120);
		square3 = new SquareGizmo(160,180);
		square4 = new SquareGizmo(300,270);
		square5 = new SquareGizmo(400,260);
		SquareGizmo square6 = new SquareGizmo(400,270);
		SquareGizmo square7 = new SquareGizmo(400,280);
		SquareGizmo square8 = new SquareGizmo(400,290);
		SquareGizmo square9 = new SquareGizmo(400,300);

		TriangleGizmo triangle = new TriangleGizmo(240, 300);
		TriangleGizmo triangle2 = new TriangleGizmo(270, 100);
		TriangleGizmo triangle3 = new TriangleGizmo(150, 460);
		TriangleGizmo triangle4 = new TriangleGizmo(390, 260);
		TriangleGizmo triangle5 = new TriangleGizmo(120, 170);



		triangleList.add(triangle);
		triangleList.add(triangle2);
		triangleList.add(triangle3);
		triangleList.add(triangle4);
		triangleList.add(triangle5);


/*		circlesList.add(circle);
		circlesList.add(circle2);
		circlesList.add(circle3);
		circlesList.add(circle4);
		circlesList.add(circle5);
*/

		ballsList.add(ball);
		ballsList.add(ball2);
		ballsList.add(ball3);
		ballsList.add(ball4);
		ballsList.add(ball5);
		ballsList.add(ball6);
		ballsList.add(ball7);


/*		squaresList.add(square);
		squaresList.add(square2);
		squaresList.add(square3);
		squaresList.add(square4);
		squaresList.add(square5);
		squaresList.add(square6);
		squaresList.add(square7);
		squaresList.add(square8);
		squaresList.add(square9);*/



		// Wall size 500 x 500 pixels
		gws = new Walls(0, 0, 500, 500);

		// Lines added in Main
		lines = new ArrayList<VerticalLine>();
	}

	public void moveBall() {

		double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball


		for (int i = 0; i < ballsList.size(); i++){
			Ball currentBall = ballsList.get(i);
			currentBall.getExactX();
			if (currentBall != null && !currentBall.stopped()) {
				CollisionDetails cd = timeUntilCollision(currentBall);
				double tuc = cd.getTuc();
				if (tuc > moveTime) {
					// No collision ...
					currentBall = movelBallForTime(currentBall, moveTime);
				} else {
					// We've got a collision in tuc
					currentBall = movelBallForTime(currentBall, tuc);
					// Post collision velocity ...
					currentBall.setVelo(cd.getVelo());

				}

				// Notify observers ... redraw updated view
				this.setChanged();
				this.notifyObservers();
			}
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

	private CollisionDetails timeUntilCollision(Ball currBall) {
		// Find Time Until Collision and also, if there is a collision, the new speed vector.
		// Create a physics.Circle from Ball
		Circle ballCircle = currBall.getCircle();
		Vect ballVelocity = currBall.getVelo();
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
				newVelo = Geometry.reflectWall(line, currBall.getVelo(), 1.0);
			}
		}


		// Time to collide with Circles
		for (CircleGizmo circle: circlesList){
			Circle currCircle = circle.getCircle();
			time = Geometry.timeUntilCircleCollision(currCircle, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectCircle(currCircle.getCenter(), ballCircle.getCenter(), currBall.getVelo(), 1.0);
			}
		}


		// time to collide with squares
		for (SquareGizmo square:squaresList){
			List<LineSegment> squareSides = square.getSides();
			List<Circle> squareCorners = square.getCorners();
			for (LineSegment currSide:squareSides){
				time = Geometry.timeUntilWallCollision(currSide,ballCircle,ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectWall(currSide, currBall.getVelo(), 1.0);
				}
			}
			for (Circle corner:squareCorners){
				time = Geometry.timeUntilCircleCollision(corner,ballCircle,ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectCircle(corner.getCenter(), ballCircle.getCenter(), currBall.getVelo(),1.0);
				}
			}
		}

		//time to collide with triangles
		for (TriangleGizmo triangle: triangleList){
			List<LineSegment> triangleSides = triangle.getSides();
			List<Circle> triangleCorners = triangle.getCorners();
			for (LineSegment currSide:triangleSides){
				time = Geometry.timeUntilWallCollision(currSide,ballCircle,ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectWall(currSide, currBall.getVelo(), 1.0);
				}
			}
			for (Circle corner:triangleCorners){
				time = Geometry.timeUntilCircleCollision(corner,ballCircle,ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectCircle(corner.getCenter(), ballCircle.getCenter(), currBall.getVelo(),1.0);
				}
			}
		}


		// Time to collide with any vertical lines
		for (VerticalLine line : lines) {
			LineSegment ls = line.getLineSeg();
			time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectWall(ls, currBall.getVelo(), 1.0);
			}
		}
		return new CollisionDetails(shortestTime, newVelo);
	}

	public Ball getBall() {
		return ball;
	}

	public ArrayList<VerticalLine> getLines() {
		return lines;
	}

	public void addLine(VerticalLine l) {
		lines.add(l);
	}

	public void setBallSpeed(int x, int y) {
		for (int i = 0; i < ballsList.size(); i++){
			ballsList.get(i).setVelo(new Vect(x, y));
		}
	}

	public List<Ball> getBallList(){
		return ballsList;
	}

	public List<CircleGizmo> getCircleList(){
		return circlesList;
	}

	public List<SquareGizmo> getSquareList(){
		return squaresList;
	}

	public List<TriangleGizmo> getTriangleList(){
		return triangleList;
	}
}
