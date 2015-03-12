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

import model.gizmo.*;
import physics.Circle;
import physics.LineSegment;
import model.Ball;
import model.Model;
import model.VerticalLine;

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
        List<Gizmo> gizmoList = gm.getGizmoList();

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

        // Draw all gizmos
        for (Gizmo gizmo : gizmoList) {
            List<LineSegment> sides = gizmo.getSides();
            List<Circle> corners = gizmo.getCorners();
            if (sides.isEmpty()){
            	CircleBumper circle = (CircleBumper) gizmo;
            	g2.setColor(gizmo.getColour());
            	int x = (int) (gizmo.getX() - circle.getRadius());
              	int y = (int) (gizmo.getY() - circle.getRadius());
              	int width = (int) (2 * circle.getRadius());
              	g2.fillOval(x, y, width, width);
            } else {
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