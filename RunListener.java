

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
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

		final JFileChooser fc = new JFileChooser();
		int returnVal;
		
		if (e.getSource() == timer) {
			model.moveBall();
		} else
			switch (e.getActionCommand()) {
			case "Start":
				timer.start();
				break;
			case "Stop":
				timer.stop();
				break;
			case "Tick":
				model.moveBall();
				break;
			case "Save":
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            String fileName = file + ".txt";
		            model.saveBoard(file, fileName);
				}
				break;
			case "Load":
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            model.loadBoard(file);
				}
				break;
			case "Quit":
				System.exit(0);
				break;
			}
	}
}
