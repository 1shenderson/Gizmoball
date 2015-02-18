package Model;

import java.util.Map;
import physics.Vect;

public interface Gizmo {

	public Vect getVect();

	public double getX();

	public double getY();

	public List<Gizmo> getTriggers();

	public void trigger();


}
