import java.util.Observer;

public interface Game {

	public void start();

	public void stop();

	public void tick();

	public void trigger(int keyID);

	public void addGizmo(int x, int y, int gizmoType);

	public void removeGizmo(int x, int y);

	public void removeGizmo(int gizmoID);

	public void addTriggerKey(int gizmoID, int keyID);

	public void addTriggerGizmo(int gizmoID, int gizmoTriggerID);

	public void removeTriggerKey(int gizmoID, int keyID);

	public void removeTriggerGizmo(int gizmoID, int gizmoTriggerID);

	public int[][] getMap();

	public void addObserver(Observer o);

}
