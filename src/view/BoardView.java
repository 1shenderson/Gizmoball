package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
    protected int L;
    private boolean gridEnabled = true;


	public BoardView(int w, int h, Board board, int L) {
        this.L = L;
		// Observe changes in Model
		board.addObserver(this);
		width = w * L;
		height = h * L;
		this.board = board;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

    public void setGridEnabled(boolean b) {
        this.gridEnabled = b;
        repaint();
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
				int x = (int) (gizmo.getX() * L);
				int y = (int) (gizmo.getY() * L);
				int width = (int) (2 * circle.getRadius());
				g2.fillOval(x, y, width, width);
			}
			// flippers
			else if (gizmo.getType().equals("RightFlipper") || gizmo.getType().equals("LeftFlipper") ){
				AbstractFlipper flip = (AbstractFlipper) gizmo;
				g2.setColor(gizmo.getColour());
				int x = (int) gizmo.getX();
				int y = (int) gizmo.getY();
				Stroke s = new BasicStroke(12.5f, BasicStroke.CAP_ROUND, 0);
				g2.setStroke(s);
				g2.drawLine(x, y, flip.getx2(), flip.gety2());
				s = new BasicStroke(1.0f,0,0);
				g2.setStroke(s);
			}
			else
			{
				g2.setColor(gizmo.getColour());
				int[] xCoordinates = gizmo.getAllXPos();
				int[] yCoordinates = gizmo.getAllYPos();
				g2.fillPolygon(xCoordinates, yCoordinates,xCoordinates.length);
			}
		}

        if (gridEnabled) {
            for (int i = 1; i < L; i++) {
                g2.setColor(Color.BLACK);
                g2.drawLine(0, i*L, width, i*L);
                g2.drawLine(i*L, 0, i*L, height);
            }
        }

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

}