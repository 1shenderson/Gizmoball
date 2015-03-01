package model;

import java.util.Observer;

public interface Game {
	
	public void tick();

	public void trigger(int keyID);

	public void addGizmo(int x, int y, String gizmoType, String gizmoID);

    public void addAbsorber(int x, int y, int width, int height, String gizmoID);

	public void removeGizmo(int x, int y);

	public void removeGizmo(String gizmoID);

	public void addTriggerKey(String gizmoID, int keyID);

	public void addTriggerGizmo(String gizmoID, String gizmoTriggerID);

	public void removeTriggerKey(String gizmoID, int keyID);

	public void removeTriggerGizmo(String gizmoID, String gizmoTriggerID);

	public int[][] getMap();

	public void addObserver(Observer o);

}
