package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Board;
import model.gizmo.*;
import physics.Circle;
import physics.LineSegment;
import model.Ball;
import model.VerticalLine;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public  class BoardView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected Board board;


	public BoardView(int w, int h, Board board) {
		// Observe changes in Model
		board.addObserver(this);
		width = w;
		height = h;
		this.board = board;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		List<Ball> ballList = board.getBallList();
		List<Gizmo> gizmoList = board.getGizmoList();

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

		// Draw all gizmos
		for (Gizmo gizmo : gizmoList) {
			List<LineSegment> sides = gizmo.getSides();
			List<Circle> corners = gizmo.getCorners();
			if (sides.isEmpty()){
				CircleBumper circle = (CircleBumper) gizmo;
				g2.setColor(gizmo.getColour());
				int x = (int) (gizmo.getX());
				int y = (int) (gizmo.getY());
				int width = (int) (2 * circle.getRadius());
				g2.fillOval(x, y, width, width);
			}
			// flippers
			else if (gizmo.getType().equals("RightFlipper") ||gizmo.getType().equals("LeftFlipper") ){
				g2.setColor(gizmo.getColour());
				int x = (int) gizmo.getX();
				int y = (int) gizmo.getY();
				Stroke s = new BasicStroke(12.5f, BasicStroke.CAP_ROUND, 0);
				g2.setStroke(s);
				g2.drawLine(x, y, gizmo.getx2(), gizmo.gety2());
				s = new BasicStroke(1.0f,0,0);
				g2.setStroke(s);
				g2.setColor(gizmo.getColour());
				g2.fillOval(x-6, y-6, 12, 15);
				g2.fillOval(gizmo.getx2() - 6 , gizmo.gety2() - 6,12, 15);
			}
			else
			{
				g2.setColor(gizmo.getColour());
				int[] xCoordinates = gizmo.getAllXPos();
				int[] yCoordinates = gizmo.getAllYPos();
				g2.fillPolygon(xCoordinates, yCoordinates,xCoordinates.length);
			}
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

}