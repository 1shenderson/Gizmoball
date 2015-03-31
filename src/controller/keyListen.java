package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.gizmo.Gizmo;

public class keyListen implements KeyListener {

    private Board board;
    private List<ArrayList<Object>> triggerKey;
    private List<Integer> keys = new ArrayList<Integer>();
    private List<String> gizmoID = new ArrayList<String>();
    private List<String> direction = new ArrayList<String>();
    private List<Gizmo> giz = new ArrayList<Gizmo>();
    private List<Integer> keypressed = new ArrayList<Integer>();


    public keyListen(Board board) {
        this.board = board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        triggerKey = board.getTriggerKeys();
        gizmoID.clear();
        direction.clear();
        keys.clear();
        for(ArrayList<Object> t: triggerKey){
            gizmoID.add((String) t.get(1));
            direction.add((String) t.get(2));
            keys.add((Integer) t.get(3));
        }
        giz = board.getGizmoList();
        int kID = e.getKeyCode();
        int index = 0;

        if (keys.contains(kID)){
            index = keys.indexOf(kID);
            if (direction.get(index).equals("down")){
                String gizID = gizmoID.get(index);
                for (Gizmo gizmo : giz){
                    if (gizmo.getID().equals(gizID)){
                        if (gizmo.getType().equals("LeftFlipper") || gizmo.getType().equals("RightFlipper" )){
                            gizmo.trigger(true);
                        } else if (!keypressed.contains(kID)){
                            keypressed.add(kID);
                            gizmo.trigger();
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
        for(ArrayList<Object> t: triggerKey){
            String dir = (String) t.get(2);
            if (dir.equals("up")){
                direction.add(dir);
                gizmoID.add((String) t.get(1));
                keys.add((Integer) t.get(3));
            }
        }
        giz = board.getGizmoList();
        int kID = e.getKeyCode();
        keypressed.remove((Object)kID);
        int index = 0;

        if (keys.contains(kID)){
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
    @Override
    public void keyTyped(KeyEvent e) {

    }

}
