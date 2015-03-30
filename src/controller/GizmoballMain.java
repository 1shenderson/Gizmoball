package controller;

import javax.swing.UIManager;

import model.Model;
import view.GizmoballGui;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class GizmoballMain {

	public static void main(String[] args) {
        int L = 25;
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}
		Model model = new Model(L);
		GizmoballGui gui = new GizmoballGui(model, L);
		gui.createAndShowGUI();
	}
}