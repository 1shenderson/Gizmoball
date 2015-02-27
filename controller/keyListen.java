package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//import model.Flipper;
import model.Model;
import model.gizmo.FlipperLeft;

public class keyListen implements KeyListener {

	private Model model;

	public keyListen(Model m) {
		model = m;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if (c == 'l'){
			model.rotateFlip(true);
		}
		if (c == 'r'){
			model.rotateFlipR(true);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();
		if (c == 'l'){
			model.rotateFlip(false);
		}
		if (c == 'r'){
			model.rotateFlipR(false);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		//  System.out.println("Key Release: " + c);

	}

}
