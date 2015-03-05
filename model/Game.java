package model;

import java.io.File;
import java.util.Observer;

import model.gizmo.Absorber;

public interface Game {
	
	public void tick();

	public void trigger(int keyID);

	public void addGizmo(String gizmoType, String gizmoID, int x, int y);

    public void addAbsorber(Absorber ab);

	public void removeGizmo(int x, int y);

	public void removeGizmo(String gizmoID);

	public void addTriggerKey(String gizmoID, int keyID);

	public void addTriggerGizmo(String gizmoID, String gizmoTriggerID);

	public void removeTriggerKey(String gizmoID, int keyID);

	public void removeTriggerGizmo(String gizmoID, String gizmoTriggerID);

	public int[][] getMap();

	public void addObserver(Observer o);
	
	public void loadBoard(File file);

	public void saveBoard(String save);

}
