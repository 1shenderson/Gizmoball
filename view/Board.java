package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import physics.LineSegment;
import model.Ball;
import model.Model;
import model.SquareGizmo;
import model.TriangleGizmo;
import model.VerticalLine;
import model.CircleGizmo;
import model.SquareGizmo;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public  class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected Model gm;


	public Board(int w, int h, Model m) {
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		gm = m;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		List<Ball> ballList = gm.getBallList();
		List<CircleGizmo> circleList = gm.getCircleList();
		List<SquareGizmo> squareList = gm.getSquareList();
		List<TriangleGizmo> triangleList = gm.getTriangleList();

		// Draw all the vertical lines
		for (VerticalLine vl : gm.getLines()) {
			g2.fillRect(vl.getX(), vl.getY(), vl.getWidth(), 1);
		}

		//Draws all balls
		for (int i = 0; i < ballList.size(); i++){
			Ball b = ballList.get(i);
			if (b != null) {
				g2.setColor(b.getColour());
				int x = (int) (b.getExactX() - b.getRadius());
				int y = (int) (b.getExactY() - b.getRadius());
				int width = (int) (2 * b.getRadius());
				g2.fillOval(x, y, width, width);
			}
		}

		//Draw all Circles
		for (int i = 0; i < circleList.size(); i++){
			CircleGizmo c = circleList.get(i);
			if (c != null){
				g2.setColor(c.getColour());
				int x = (int) (c.getX() - c.getRadius());
				int y = (int) (c.getY() - c.getRadius());
				int width = (int) (2 * c.getRadius());
				g2.fillOval(x, y, width, width);
			}
		}

		//draw all squares
		for (int i = 0; i < squareList.size(); i++){
			SquareGizmo currSquare = squareList.get(i);
			g2.setColor(currSquare.getColour());
			int xPos = currSquare.getX() - (currSquare.getLength()/2);
			int yPos = currSquare.getY() - (currSquare.getLength()/2);
			g2.fillRect(xPos ,yPos,currSquare.getLength()+1,currSquare.getLength()+1);
		}

		//draw all triangles
		for (int i = 0; i < triangleList.size(); i++){
			TriangleGizmo currTriangle = triangleList.get(i);
			int xPos = currTriangle.getX();
			int yPos = currTriangle.getY();
			int length = currTriangle.getLength();
			int[] xCoordinates = {xPos + (length/2)+1, xPos - (length/2), xPos - (length/2)};
			int[] yCoordinates = {yPos + (length/2)+1, yPos + (length/2)+1, yPos - (length/2)-1};
			g2.setColor(currTriangle.getColour());
			g2.fillPolygon(xCoordinates, yCoordinates, 3);
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}

}
