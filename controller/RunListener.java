package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.Timer;

import view.Display;
import view.RunGui;
import model.Game;
import model.Model;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class RunListener implements ActionListener {
    private Timer timer;
    private Game game;
    private Display gui;
    private int L = 25;   // Physics constant

    public RunListener(Model m, RunGui gui) {
        game = m;
        this.gui = gui;
        timer = new Timer(50, this);
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            game.tick();
        } else
            switch (e.getActionCommand()) {
                case "Start":
                    timer.start();
                    break;
                case "Stop":
                    timer.stop();
                    break;
                case "Tick":
                    game.tick();
                    break;
                case "Save":
                	game.saveBoard(gui.save());
                	break;
                case "Load":
                	game.loadBoard(gui.load());
                	break;
                case "Quit":
                    System.exit(0);
                    break;
            }
    }
}
