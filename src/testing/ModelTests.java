package testing;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Ball;
import model.Board;
import model.Model;
import model.gizmo.Absorber;
import model.gizmo.AbstractFlipper;
import model.gizmo.Gizmo;

import org.junit.Test;

import controller.FileHandling;

public class ModelTests {

	Board board = new Model(25);
	FileHandling file = new FileHandling();
	List<Gizmo> gizmoList = new ArrayList<Gizmo>();
	List<Ball> ballList = new ArrayList<Ball>();
	List<ArrayList<Object>> triggerList = new ArrayList<ArrayList<Object>>();
	ArrayList<ArrayList<Object>> loadList = new ArrayList<ArrayList<Object>>();
	
	@Test
	public void testAddGizmo() {
		board.addGizmo("Triangle", "T", 19, 0);
		board.addGizmo("Square", "S02", 0, 2);
		board.addGizmo("Circle", "C43", 4, 3);
		board.addGizmo("LeftFlipper", "LF92", 9, 2);
		board.addGizmo("RightFlipper", "RF112", 11, 2);
		board.addGizmo("Triangle", null, 10, 1);
		board.addGizmo("Square", null, 5, 2);
		board.addGizmo("Circle", null, 5, 4);
		board.addGizmo("LeftFlipper", null, 12, 5);
		board.addGizmo("RightFlipper", null, 15, 6);
		
		double delta = 0.1;
		gizmoList = board.getGizmoList();
		
		for (int i = 0; i < gizmoList.size(); i++) {
			
			Gizmo tri0 = gizmoList.get(0);
			Gizmo sq0 = gizmoList.get(1);
			Gizmo cir0 = gizmoList.get(2);
			AbstractFlipper leftFlipper0 = (AbstractFlipper) gizmoList.get(3);
			AbstractFlipper rightFlipper0 = (AbstractFlipper) gizmoList.get(4);
			Gizmo tri1 = gizmoList.get(5);
			Gizmo sq1 = gizmoList.get(6);
			Gizmo cir1 = gizmoList.get(7);
			AbstractFlipper leftFlipper1 = (AbstractFlipper) gizmoList.get(8);
			AbstractFlipper rightFlipper1 = (AbstractFlipper) gizmoList.get(9);
			
			assertEquals(tri0.getType(), "Triangle");
			assertEquals(tri0.getID(), "T");
			assertEquals(tri0.getX()/25, 19);
			assertEquals(tri0.getY()/25, 0);
			
			assertEquals(sq0.getType(), "Square");
			assertEquals(sq0.getID(), "S02");
			assertEquals(sq0.getX()/25, 0);
			assertEquals(sq0.getY()/25, 2);
					
			assertEquals(cir0.getType(), "Circle");
			assertEquals(cir0.getID(), "C43");
			assertEquals(cir0.getX()/25, 4);
			assertEquals(cir0.getY()/25, 3);
			
			assertEquals(leftFlipper0.getType(), "LeftFlipper");
			assertEquals(leftFlipper0.getID(), "LF92");
			assertEquals(leftFlipper0.getX()/25, 9);
			assertEquals(leftFlipper0.getY()/25, 2);
			assertEquals(leftFlipper0.getWidth(), 12.5, delta);
			assertEquals(leftFlipper0.getLength(), 37.5, delta);
			
			assertEquals(rightFlipper0.getType(), "RightFlipper");
			assertEquals(rightFlipper0.getID(), "RF112");
			assertEquals(rightFlipper0.getX()/25, 12);
			assertEquals(rightFlipper0.getY()/25, 2);
			assertEquals(rightFlipper0.getWidth(), 12.5, delta);
			assertEquals(rightFlipper0.getLength(), 37.5, delta);
			
			//these test generateID works for all gizmos:
			
			assertEquals(tri1.getType(), "Triangle");
			assertEquals(tri1.getID(), "T1");
			assertEquals(tri1.getX()/25, 10);
			assertEquals(tri1.getY()/25, 1);
			
			assertEquals(sq1.getType(), "Square");
			assertEquals(sq1.getID(), "S1");
			assertEquals(sq1.getX()/25, 5);
			assertEquals(sq1.getY()/25, 2);
			
			assertEquals(cir1.getType(), "Circle");
			assertEquals(cir1.getID(), "C1");
			assertEquals(cir1.getX()/25, 5);
			assertEquals(cir1.getY()/25, 4);
			
			assertEquals(leftFlipper1.getType(), "LeftFlipper");
			assertEquals(leftFlipper1.getID(), "LF1");
			assertEquals(leftFlipper1.getX()/25, 12);
			assertEquals(leftFlipper1.getY()/25, 5);
			assertEquals(leftFlipper1.getWidth(), 12.5, delta);
			assertEquals(leftFlipper1.getLength(), 37.5, delta);
			
			assertEquals(rightFlipper1.getType(), "RightFlipper");
			assertEquals(rightFlipper1.getID(), "RF1");
			assertEquals(rightFlipper1.getX()/25, 16);
			assertEquals(rightFlipper1.getY()/25, 6);
			assertEquals(rightFlipper1.getWidth(), 12.5, delta);
			assertEquals(rightFlipper1.getLength(), 37.5, delta);
		}
	}
	
	@Test
	public void testAddAbsorber() {
		board.addAbsorber("Absorber", "A", 0, 19, 20, 20);
		board.addAbsorber("Absorber", null, 0, 19, 1, 1);
		
		gizmoList = board.getGizmoList();
		
		for (int i = 0; i < gizmoList.size(); i++) {
			Absorber absorber = (Absorber) gizmoList.get(0);
			assertEquals(absorber.getType(), "Absorber");
			assertEquals(absorber.getID(), "A");
			assertEquals(absorber.getX()/25, 0);
			assertEquals(absorber.getY()/25, 19);
			assertEquals(absorber.getWidth(), 20);
			assertEquals(absorber.getHeight(), 20);
			
			Absorber absorber1 = (Absorber) gizmoList.get(1);
			assertEquals(absorber1.getType(), "Absorber");
			assertEquals(absorber1.getID(), "A1");
			assertEquals(absorber1.getX()/25, 0);
			assertEquals(absorber1.getY()/25, 19);
			assertEquals(absorber1.getWidth(), 1);
			assertEquals(absorber1.getHeight(), 1);
		}
	}
	
	@Test
	public void testAddBall() {
		board.addBall("Ball", 1, 11);
		board.addBall(null, 2, 12);
		double delta = 0.5;
		
		ballList = board.getBallList();
		
		for (int i = 0; i < ballList.size(); i++) {
			assertEquals(ballList.get(0).getID(), "Ball");
			assertEquals(ballList.get(0).getExactX()/25, 1.0, delta);
			assertEquals(ballList.get(0).getExactY()/25, 11.0, delta);
			assertEquals(ballList.get(0).getXVelocity(), 0.0, delta);
			assertEquals(ballList.get(0).getYVelocity(), 0.0, delta);
			assertEquals(ballList.get(1).getID(), "B1");
			assertEquals(ballList.get(1).getExactX()/25, 2.0, delta);
			assertEquals(ballList.get(1).getExactY()/25, 12.0, delta);
			assertEquals(ballList.get(1).getXVelocity(), 0.0, delta);
			assertEquals(ballList.get(1).getYVelocity(), 0.0, delta);
		}
	}
	
	@Test
	public void testAddTriggerGizmo() {
		
	}
	
	@Test
	public void testAddTriggerKey() {
		board.addGizmo("RightFlipper", "RF112", 11, 2);
		gizmoList = board.getGizmoList();
		board.addTriggerKey("Key", "RF112", 87, "down");
		board.addTriggerKey("Key", "RF112", 87, "up");
		
		triggerList = board.getTriggerKeys();
		for (int i = 0; i < triggerList.size(); i++) {
			Gizmo rFlip = gizmoList.get(0);
			assertEquals(triggerList.get(0).get(1), rFlip.getID());
		}
	}
	
	@Test
	public void testLoadBoard() {
		File gizmoFile = new File("Gizmo.txt");
		loadList = file.load(gizmoFile);
		board.loadBoard(loadList);
		board.addGizmo("Triangle", "T2", 1, 1);
		gizmoList = board.getGizmoList();
		for (int i = 0; i < gizmoList.size(); i++) {
			assertEquals(gizmoList.get(0).getID(), "T");
			assertNotEquals(gizmoList.get(1).getID(), "T");
			assertEquals(gizmoList.get(1).getID(), "T2");
			assertNotEquals(gizmoList.get(2).getID(), "T2");
		}	
	}
}
