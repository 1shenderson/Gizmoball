package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Ball;
import model.Model;
import model.VerticalLine;
import model.gizmo.FlipperLeft;
import model.gizmo.FlipperRight;

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
		
		// Draw all the vertical lines
		for (VerticalLine vl : gm.getLines()) {
			g2.fillRect(vl.getX(), vl.getY(), vl.getWidth(), 1);
		}
		
		Ball b = gm.getBall();
		if (b != null) {
			g2.setColor(b.getColour());
			int x = (int) (b.getExactX() - b.getRadius());
			int y = (int) (b.getExactY() - b.getRadius());
			int width = (int) (2 * b.getRadius());
			g2.fillOval(x, y, width, width);
		}
		
		FlipperLeft f = gm.getFlipper();
		if (f != null){
			g2.setColor(f.getColour());
			int x = (int) f.getX();
			int y = (int) f.getY();
			g2.drawLine(x, y, f.getx2(), f.gety2());
		    //g2.rotate(Math.toRadians(60),f.getX(),f.getY());
			//g2.fillRoundRect(x, y, f.getWidth(), f.getLength(), 25, 25);
		}
		
		FlipperRight fr = gm.getFlipperR();
		if (fr != null){
			g2.setColor(fr.getColour());
			int x = (int) fr.getX();
			int y = (int) fr.getY();
			int xArray[] = new int [4];
			int yArray[] = new int [4];
			xArray[0] = x;
			xArray[1] = x+25;
			xArray[2] = fr.getx2() + 25;
			xArray[3] = fr.getx2();
			
			
			yArray[0] = y;
			yArray[1] = y;
			yArray[2] = fr.gety2();
			yArray[3] = fr.gety2();
			
			g2.drawLine(x, y, fr.getx2(), fr.gety2());

			//g2.fillPolygon(xArray, yArray, 4);
		    //g2.rotate(Math.toRadians(60),f.getX(),f.getY());
			//g2.fillRoundRect(x, y, f.getWidth(), f.getLength(), 25, 25);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}
	
}
