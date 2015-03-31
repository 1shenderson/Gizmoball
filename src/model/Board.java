package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import model.gizmo.Gizmo;

public interface Board {
	
	public void tick();

	public void trigger(int keyID);

	public void removeGizmo(int x, int y);

	public void removeGizmo(String gizmoID);

	public void addTriggerGizmo(String gizmoType, String gizmoID, String gizmoTriggerID);

	public void removeTriggerGizmo(String gizmoID, String gizmoTriggerID);

	public int[][] getMap();

	public void addObserver(Observer o);

	public void addGizmo(String gizmoType, String gizmoID, int x, int y);

    public void addGizmo(Gizmo gizmo);

	void addAbsorber(String gizmoType, String id, int x, int y, int width,
			int height);
	
	public void loadBoard(ArrayList<ArrayList<Object>> loadList);

	public void addTriggerKey(String gizmoType, String gizmoID, int keyID, String keyDirection);

	public List<ArrayList<Object>> getTriggerKeys();

	public void removeTriggerKey(String gizmoID, int keyID, String keyDirection);

    public void rotate(String gizmoId);

    public Gizmo getGizmoAtLocation(int x, int y);

	public List<Gizmo> getGizmoList();

	public List<Ball> getBallList();

    public void setBallSpeed(String ballId, int velocityX, int velocityY);

	public String addBall(String string, double x, double y);

	public double getGravity();

	public double getFriction1();

	public double getFriction2();

	public Map<String, Integer> getRotateMap();

	public List<ArrayList<Object>> getConnectList();

}
