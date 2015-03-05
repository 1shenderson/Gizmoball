package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import model.Model;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class RunListener implements ActionListener {

    private Timer timer;
    private Model model;

    public RunListener(Model m) {
        model = m;
        timer = new Timer(50, this);

    }

    @Override
    public final void actionPerformed(final ActionEvent e) {

        if (e.getSource() == timer) {
			model.moveBall();
		} else
			if (e.getActionCommand().equals("Start")) {
				timer.start();
			}
			else if (e.getActionCommand().equals("Stop")) {
				timer.stop();
			}
			else if (e.getActionCommand().equals("Tick")) {
				model.moveBall();
			}
			else if (e.getActionCommand().equals("Quit")) {
				System.exit(0);
			}
    }
}
