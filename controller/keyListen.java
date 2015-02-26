package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//import model.Flipper;
import model.Model;

public class keyListen implements KeyListener {

	private Model model;

	public keyListen(Model m) {
		model = m;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	    char c = e.getKeyChar();
	    System.out.println("Key Typed: " + c);
	    model.changeFlipperColour();	
	    model.rotateFlip();
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
	    char c = e.getKeyChar();
	   // System.out.println("Key Pressed: " + c);
				
	}

	@Override
	public void keyTyped(KeyEvent e) {
	    char c = e.getKeyChar();
	  //  System.out.println("Key Release: " + c);
				
	}

}
