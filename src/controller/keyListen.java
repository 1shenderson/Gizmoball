package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Model;
import model.gizmo.Gizmo;

public class keyListen implements KeyListener {

	private Board board;
	private List<Integer> keysDown = new ArrayList<Integer>();
	private List<Object> triggerKey;
	private List<Integer> keys = new ArrayList<Integer>();
	private List<String> gizmoID = new ArrayList<String>();
	private List<String> direction = new ArrayList<String>();
	private List<Gizmo> giz = new ArrayList<Gizmo>();;

	public keyListen(Board boar) {
		board = boar;
	}


	// keycode 81 = q
	@Override
	public void keyPressed(KeyEvent e) {
		triggerKey = board.getTriggerKeys();
		gizmoID.clear();
		direction.clear();
		keys.clear();
		for (int i = 0; i < triggerKey.size()-2; i= i +3){
			gizmoID.add((String) triggerKey.get(i));
			direction.add((String) triggerKey.get(i+1));
			keys.add((Integer) triggerKey.get(i+2));
		}
		giz = board.getGizmoList();
		int kID = e.getKeyCode();
		if (!keysDown.contains(kID)){
			keysDown.add(kID);
		}
		System.out.println(keysDown);
		int index = 0;
		for (int k :keysDown){
			System.out.println("keys "+ keys);
			if (keys.contains(k)){
				index = keys.indexOf(k);
				if (direction.get(index).equals("down")){
					String gizID = gizmoID.get(index);
					for (Gizmo gizmo : giz){
						if (gizmo.getID().equals(gizID)){
							if (gizmo.getType().equals("LeftFlipper") || gizmo.getType().equals("RightFlipper" )){
								gizmo.trigger(true);
							} else {
								gizmo.trigger();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		triggerKey = board.getTriggerKeys();
		gizmoID.clear();
		direction.clear();
		keys.clear();
		for (int i = 0; i < triggerKey.size()-2; i= i +3){
			String dir = (String) triggerKey.get(i+1);
			if (dir.equals("up")){
				direction.add(dir);
				gizmoID.add((String) triggerKey.get(i));
				keys.add((Integer) triggerKey.get(i+2));

			}
		}
		giz = board.getGizmoList();
		int kID = e.getKeyCode();
		//keysDown.remove((Object)kID);

		int index = 0;
		for (int k :keysDown){
			if (keys.contains(k)){
				index = keys.indexOf(kID);
				if (direction.get(index).equals("up")){
					String gizID = gizmoID.get(index);
					for (Gizmo gizmo : giz){
						if (gizmo.getID().equals(gizID)){
							if (gizmo.getType().equals("LeftFlipper") || gizmo.getType().equals("RightFlipper" )){
								gizmo.trigger(false);
							} else {
								gizmo.trigger();
							}
						}
					}
				}
			}
		}
		keysDown.remove((Object)kID);

	}
	@Override
	public void keyTyped(KeyEvent e) {

	}

}
