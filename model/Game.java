package model;

import java.io.File;
import java.util.Observer;

public interface Game {
	
	public void tick();

	public void trigger(int keyID);

	public void removeGizmo(int x, int y);

	public void removeGizmo(String gizmoID);

	public void addTriggerKey(String gizmoID, int keyID);

	public void addTriggerGizmo(String gizmoID, String gizmoTriggerID);

	public void removeTriggerKey(String gizmoID, int keyID);

	public void removeTriggerGizmo(String gizmoID, String gizmoTriggerID);

	public int[][] getMap();

	public void addObserver(Observer o);

	public void addGizmo(String gizmoType, String gizmoID, int x, int y);

	void addAbsorber(String gizmoType, String id, int x, int y, int width,
			int height);

	public void saveBoard(String fileName);
	
	public void loadBoard(File load);

}