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
import model.gizmo.SquareGizmo;
import model.VerticalLine;
import model.gizmo.CircleGizmo;

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
			//g2.fillRect(currSquare.getxPos(), currSquare.getyPos(), currSquare.getWidth(), currSquare.getWidth());
			g2.fillRect(currSquare.getTLeftX() ,currSquare.getTLeftY(),currSquare.getWidth(),currSquare.getWidth());
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}

}