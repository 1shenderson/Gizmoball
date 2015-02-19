package Model;

import java.util.Map;
import physics.Vect;

public interface Gizmo {
	
	public Vect getVect();
	
	public int getX();

	public int getY();

	public List<Gizmo> getTriggers();

	public void trigger();

}
