package controller;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import model.gizmo.Gizmo;
import view.Display;
import view.GizmoballGui;
import model.Board;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class RunListener implements ActionListener, MouseListener, KeyListener {
    private enum Mode {
        build, play
    }
    private Mode mode;
    private Timer timer;
    private Board board;
    private Display gui;
    private File file;
    FileHandling fileHand = new FileHandling();
    private String fileName;
    private String activeTool;
    private int lastX, lastY;
    private int L = 25;   // Physics constant
    private Gizmo selectedGizmo;

    public RunListener(Board board, GizmoballGui gui) {
        this.board = board;
        this.gui = gui;
        timer = new Timer(1000/60, this);
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            board.tick();
        } else {
            String command = e.getActionCommand();
            selectedGizmo = null;
            gui.listenForBindings(false);
            switch (command) {
                // MODE CHANGES
                case "Build Mode":
                case "<html>SWITCH TO<br>BUILD MODE</html>":
                    mode = Mode.build;
                    gui.toggleBuild();
                    timer.stop();
                    break;
                case "Play Mode":
                case "<html>SWITCH TO<br>PLAY MODE</html>":
                    mode = Mode.play;
                    activeTool = null;
                    gui.setSelectedButton(null);
                    gui.togglePlay();
                    break;
                // PLAY MODE
                case "Start":
                    timer.start();
                    break;
                case "Stop":
                    timer.stop();
                    break;
                case "Tick":
                    board.tick();
                    break;
                // BUILD MODE
                case "Ball":
                case "Square":
                case "Circle":
                case "Triangle":
                case "Absorber":
                case "Left Flipper":
                case "Right Flipper":
                case "Rotate":
                case "Unlink":
                case "Move":
                case "Delete":
                    JButton button = ((JButton) e.getSource());
                    gui.setSelectedButton(button);
                    activeTool = command;
                    System.out.println("Set tool to "+command);
                    break;
                case "Save":
                    fileName = gui.save();
                    fileHand.save(board.getGizmoList(), board.getBallList(), board.getTriggerKeys(), board.getConnectList(), board.getRotateMap(), fileName, board.getGravity(), board.getFriction1(), board.getFriction2());
                    break;
                case "Load":
                	file = gui.load();
                	ArrayList<ArrayList<Object>> loadList = fileHand.load(file);
                    board.loadBoard(loadList);
                    break;
                case "Link":
                    int choice = gui.showLinkWindow();
                    if (choice == 0) {
                        // User wants to link gizmo to gizmo
                        gui.changeTitle("Select gizmo to be triggered");
                        activeTool = "LinkGizmo";
                    } else {
                        // User wants to link key to gizmo
                        activeTool = "LinkKey";
                        gui.changeTitle("Select gizmo to be triggered");
                    }
                    break;
                case "About":
                    gui.showAbout();
                    break;
                case "OK":
                    gui.closeAbout();
                    break;
                default:
                    throw new RuntimeException("Unrecognized command '" + command + "', add handling for this button.");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX() / L;
        lastY = e.getY() / L;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (activeTool == null) {
            return;
        }
        int x = e.getX() / L,
            y = e.getY() / L;
        if (lastX == x && lastY == y) {
            switch (activeTool) {
                case "Square":
                case "Circle":
                case "Triangle":
                    board.addGizmo(activeTool, null, lastX, lastY);
                    break;
                case "Ball":
                    board.addBall(null, x, y);
                    break;
                case "Left Flipper":
                    board.addGizmo("LeftFlipper", null, lastX, lastY);
                    break;
                case "Right Flipper":
                    board.addGizmo("RightFlipper", null, lastX - 1, lastY);
                    break;
                case "Delete":
                    Gizmo gizmo = board.getGizmoAtLocation(x, y);
                    if (gizmo != null) {
                        board.removeGizmo(gizmo.getID());
                    }
                    break;
                case "Rotate":
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        board.rotate(board.getGizmoAtLocation(x, y).getID());
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        board.rotate(board.getGizmoAtLocation(x, y).getID());
                        board.rotate(board.getGizmoAtLocation(x, y).getID());
                        board.rotate(board.getGizmoAtLocation(x, y).getID());
                    }
                    break;
                case "LinkGizmo":
                    if (selectedGizmo == null) {
                        selectedGizmo = board.getGizmoAtLocation(x, y);
                        gui.changeTitle("Select the gizmo to act as a trigger");
                    } else {
                        Gizmo g = board.getGizmoAtLocation(x, y);
                        if (g != null) {
                            board.linkGizmos(selectedGizmo.getID(), g.getID());
                            gui.changeTitle("Build Mode");
                            activeTool = null;
                            gui.setSelectedButton(null);
                        }
                    }
                case "LinkKey":
                    if (selectedGizmo == null) {
                        selectedGizmo = board.getGizmoAtLocation(x, y);
                        gui.changeTitle("Press the key you'd like trigger the gizmo");
                        gui.listenForBindings(true);
                    }
                case "Unlink":
                    Gizmo g = board.getGizmoAtLocation(x, y);
                    if (g != null) {
                        board.removeLinks(g.getID());
                    }
                    break;
            }
        } else {
            if (activeTool.equals("Absorber")) {
                board.addAbsorber(activeTool, null, lastX, lastY, x-lastX+1, y-lastY+1);
            } else if (activeTool.equals("Move")) {
                System.out.printf("\nUsing move from (%d, %d) to (%d, %d)", lastX, lastY, x, y);
                if (board.getGizmoAtLocation(x, y) == null && board.getGizmoAtLocation(lastX, lastY) != null) {
                    Gizmo gizmo = board.getGizmoAtLocation(lastX, lastY);
                    gizmo = gizmo.moveTo(x, y);
                    board.removeGizmo(gizmo.getID());
                    board.addGizmo(gizmo);
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (activeTool.equals("LinkKey") && selectedGizmo != null) {
            int choice = gui.showKeyWindow();
            if (choice == 0) {
                // User choice key UP
                board.addTriggerKey(selectedGizmo.getType(), selectedGizmo.getID(), e.getKeyCode(), "up");
                activeTool = null;
                gui.setSelectedButton(null);
                gui.changeTitle("Build Mode");
                gui.listenForBindings(false);
            } else if (choice == 1) {
                // User choice key DOWN
                board.addTriggerKey(selectedGizmo.getType(), selectedGizmo.getID(), e.getKeyCode(), "down");
                activeTool = null;
                gui.setSelectedButton(null);
                gui.changeTitle("Build Mode");
                gui.listenForBindings(false);
            }
        }
    }
    public void keyReleased(KeyEvent e) {}
}
