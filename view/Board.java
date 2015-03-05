package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
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
		
		   RenderingHints rh = new RenderingHints(
		             RenderingHints.KEY_ANTIALIASING,
		             RenderingHints.VALUE_ANTIALIAS_OFF);
		  g2.setRenderingHints(rh);

		  rh = new RenderingHints( 
         RenderingHints.KEY_RENDERING,
         RenderingHints.VALUE_RENDER_QUALITY);
		  g2.setRenderingHints(rh);
		  
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
			Stroke s = new BasicStroke(12.5f, BasicStroke.CAP_ROUND, 0);
			g2.setStroke(s);
			g2.drawLine(x, y, f.getx2(), f.gety2());
			s = new BasicStroke(1.0f,0,0);
			g2.setStroke(s);
		}

		FlipperRight fr = gm.getFlipperR();
		if (fr != null){
			g2.setColor(fr.getColour());
			int x = (int) fr.getX();
			int y = (int) fr.getY();
			Stroke s = new BasicStroke(12.5f, BasicStroke.CAP_ROUND, 0);	
			g2.setStroke(s);
			g2.drawLine(x, y, fr.getx2(), fr.gety2());
			s = new BasicStroke(1.0f,0,0);
			g2.setStroke(s);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

}