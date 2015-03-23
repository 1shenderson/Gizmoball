package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

import view.Display;
import view.GizmoballGui;
import model.Board;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class RunListener implements ActionListener, MouseListener {
    private enum Mode {
        build, play
    }
    private Mode mode;
    private Timer timer;
    private Board board;
    private Display gui;
    private String activeTool;
    private int lastX, lastY;
    private int L = 25;   // Physics constant

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
            switch (command) {
                // MODE CHANGES
                case "SWITCH TO BUILD MODE":
                    mode = Mode.build;
                    gui.toggleBuild();
                    timer.stop();
                    break;
                case "SWITCH TO PLAY MODE":
                    mode = Mode.play;
                    activeTool = null;
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
                case "Save":
                    board.saveBoard(gui.save());
                    break;
                case "Load":
                    board.loadBoard(gui.load());
                    break;
                case "Quit":
                    System.exit(0);
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
                case "Link":
                case "Unlink":
                case "Remove":
                case "Move":
                    activeTool = command;
                    System.out.println("Set tool to "+command);
                    break;
                default:
                    throw new RuntimeException("Unrecognized command '" + command + "', add handling for this button.");

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX() / L;
        lastY = e.getY() / L;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX() / L,
            y = e.getY() / L;
        if (lastX == x && lastY == y) {
            switch (activeTool) {
                case "Square":
                case "Circle":
                case "Triangle":
                    board.addGizmo(activeTool, "TEST", lastX, lastY); // TODO Make a proper ID
                    break;
                case "Ball":
                    board.addBall("TEST", x, y);
                    break;
                case "Left Flipper":
                    board.addGizmo("LeftFlipper", "TEST", lastX, lastY); // TODO Make a proper ID
                    break;
                case "Right Flipper":
                    board.addGizmo("RightFlipper", "TEST", lastX, lastY); // TODO Make a proper ID
                    break;
            }
        } else {
            if (activeTool.equals("Absorber")) {
                board.addAbsorber(activeTool, "TEST", lastX, lastY, x-lastX+1, y-lastY+1);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
