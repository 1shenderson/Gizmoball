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

import model.Ball;
import model.Model;
import model.VerticalLine;

import model.gizmo.AbstractSquare;
import model.gizmo.CircleBumper;
import model.gizmo.Gizmo;
import model.gizmo.SquareBumper;
import model.gizmo.TriangleBumper;

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


        for (Gizmo gizmo : gizmoList) {
            if (gizmo instanceof CircleBumper) {
                // Gizmo is a circle
                CircleBumper circle = (CircleBumper) gizmo;
                g2.setColor(circle.getColour());
                int x = (int) (circle.getX() -
                		circle.getRadius());
                int y = (int) (circle.getY() - circle.getRadius());
                int width = (int) (2 * circle.getRadius());
                g2.fillOval(x, y, width, width);
            } else if (gizmo instanceof SquareBumper){
                // Gizmo is a square (didn't take triangles into account at the moment)
                AbstractSquare square = (AbstractSquare) gizmo;
                g2.setColor(square.getColour());
                g2.fillRect(square.getTopLeftX(), square.getTopLeftY(), square.getWidth(), square.getWidth());
            } else {
            	TriangleBumper triangle = (TriangleBumper) gizmo;
            	g2.setColor(triangle.getColour());
            	int[] xCoordinates = triangle.getAllXPos();
            	int[] yCoordinates = triangle.getAllYPos();
            	g2.fillPolygon(xCoordinates, yCoordinates, 3);

            }
        }
	}

	@Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}

}
