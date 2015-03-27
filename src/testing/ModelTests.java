package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Model;
import model.gizmo.Absorber;
import model.gizmo.Gizmo;

import org.junit.Test;

public class ModelTests {
	
	Board board = new Model();
	List<Gizmo> gizmoList = new ArrayList<Gizmo>();

	@Test
	public void testAddGizmo() {
		board.addGizmo("Triangle", "T", 19, 0);
		board.addGizmo("Square", "S02", 0, 2);
		board.addGizmo("Circle", "C43", 4, 3);
		board.addGizmo("LeftFlipper", "LF92", 9, 2);
		board.addGizmo("RightFlipper", "RF112", 11, 2);
		
		gizmoList = board.getGizmoList();
		System.out.println(Math.round(gizmoList.get(4).getX()/25));
		System.out.println(Math.round(gizmoList.get(4).getY()/25));
		for (int i = 0; i < gizmoList.size(); i++) {
			assertEquals(gizmoList.get(0).getType(), "Triangle");
			assertEquals(gizmoList.get(0).getID(), "T");
			assertEquals(gizmoList.get(0).getX()/25, 19);
			assertEquals(gizmoList.get(0).getY()/25, 0);
			
			assertEquals(gizmoList.get(1).getType(), "Square");
			assertEquals(gizmoList.get(1).getID(), "S02");
			assertEquals(gizmoList.get(1).getX()/25, 0);
			assertEquals(gizmoList.get(1).getY()/25, 2);
			
			assertEquals(gizmoList.get(2).getType(), "Circle");
			assertEquals(gizmoList.get(2).getID(), "C43");
			assertEquals(gizmoList.get(2).getX()/25, 4);
			assertEquals(gizmoList.get(2).getY()/25, 3);
			
			assertEquals(gizmoList.get(3).getType(), "LeftFlipper");
			assertEquals(gizmoList.get(3).getID(), "LF92");
			assertEquals(gizmoList.get(3).getX()/25, 9);
			assertEquals(gizmoList.get(3).getY()/25, 2);
			
			assertEquals(gizmoList.get(4).getType(), "RightFlipper");
			assertEquals(gizmoList.get(4).getID(), "RF112");
			assertEquals(Math.round(gizmoList.get(4).getX()/25), 12);
			assertEquals(gizmoList.get(4).getY()/25, 2);
		}
	}
	
	@Test
	public void addAbsorber() {
		board.addAbsorber("Absorber", "A", 0, 19, 20, 20);
		
		gizmoList = board.getGizmoList();
		
		for (int i = 0; i < gizmoList.size(); i++) {
			Absorber absorber = (Absorber) gizmoList.get(0);
			assertEquals(gizmoList.get(0).getType(), "Absorber");
			assertEquals(gizmoList.get(0).getID(), "A");
			assertEquals(gizmoList.get(0).getX()/25, 0);
			assertEquals(gizmoList.get(0).getY()/25, 19);
			assertEquals(absorber.getWidth(), 20);
			assertEquals(absorber.getHeight(), 20);
		}
	}

}
