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

import model.gizmo.*;
import model.Ball;
import model.Model;

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
		setBackground(Color.BLACK);
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
//		for (int i = 0; i < circleList.size(); i++){
//			CircleGizmo c = circleList.get(i);
//			if (c != null){
//				g2.setColor(c.getColour());
//				int x = (int) (c.getX() - c.getRadius());
//				int y = (int) (c.getY() - c.getRadius());
//				int width = (int) (2 * c.getRadius());
//				g2.fillOval(x, y, width, width);
//			}
//		}
//
//		//draw all squares
//
//		for (int i = 0; i < squareList.size(); i++){
//			SquareGizmo currSquare = squareList.get(i);
//			g2.setColor(currSquare.getColour());
//			//g2.fillRect(currSquare.getxPos(), currSquare.getyPos(), currSquare.getEndX(), currSquare.getEndX());
//			g2.fillRect(currSquare.getTLeftX() ,currSquare.getTLeftY(),currSquare.getWidth(),currSquare.getWidth());
//		}

		// Draw all gizmos
        for (Gizmo gizmo : gizmoList) {
            if (gizmo instanceof CircleBumper) {
                // Gizmo is a circle
                CircleBumper circle = (CircleBumper) gizmo;
                g2.setColor(circle.getColour());
                int x = (int) (circle.getX());
                int y = (int) (circle.getY());
                int width = (int) (2 * circle.getRadius());
                g2.fillOval(x, y, width, width);
            } else if (gizmo instanceof SquareBumper){
                AbstractSquare square = (AbstractSquare) gizmo;
                g2.setColor(square.getColour());
                g2.fillRect(square.getTopLeftX(), square.getTopLeftY(), square.getWidth(), square.getWidth());
            } else if (gizmo instanceof TriangleBumper){
            	TriangleBumper triangle = (TriangleBumper) gizmo;
            	g2.setColor(triangle.getColour());
            	int[] xCoordinates = triangle.getAllXPos();
            	int[] yCoordinates = triangle.getAllYPos();
            	g2.fillPolygon(xCoordinates, yCoordinates, 3);
            } else if (gizmo instanceof FlipperLeft){
            	FlipperLeft fl = (FlipperLeft) gizmo;
            	g2.setColor(fl.getColour());
    			int x = (int) fl.getX();
    			int y = (int) fl.getY();
    			// rendering hints
    			// anti alliases
    			Stroke s = new BasicStroke(12.5f, BasicStroke.CAP_ROUND, 0);
    			g2.setStroke(s);
    			g2.drawLine(x, y, fl.getx2(), fl.gety2());
    		} else if (gizmo instanceof FlipperRight){
            	FlipperRight fr = (FlipperRight) gizmo;
            	g2.setColor(fr.getColour());
    			int x = (int) fr.getX();
    			int y = (int) fr.getY();
    			// rendering hints
    			// anti alliases
    			Stroke s = new BasicStroke(12.5f, BasicStroke.CAP_ROUND, 0);
    			g2.setStroke(s);
    			g2.drawLine(x, y, fr.getx2(), fr.gety2());
    		}
    		else if (gizmo instanceof Absorber){
                Absorber ab = (Absorber) gizmo;
                g2.setColor(ab.getColour());
                g2.fillRect(ab.getTopLeftX(), ab.getTopLeftY(), ab.getBotRightX(), ab.getBotRightY());
    		}
    	}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}

}