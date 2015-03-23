package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.*;

import model.Board;
import controller.RunListener;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class GizmoballGui implements Display {
    private int L;
	private Board board;
	private JFrame frame;
	private ActionListener listener;
	private BoardView boardView;
    private JButton modeButton;
    private JPanel modeButtonPanel;

	public GizmoballGui(Board board, int L) {
		this.board = board;
        this.L = L;
		listener = new RunListener(board, this);
	}

	public void createAndShowGUI() {

		frame = new JFrame("Hit load to select text file and hit start!");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Board is passed the Model so it can act as Observer
		boardView = new BoardView(500, 500, board);
        Font gf = new Font("Arial", Font.BOLD, 12);
		Container contentPane = frame.getContentPane();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        modeButtonPanel = new JPanel();
        modeButton = new JButton();
        modeButton.setFont(gf);
        modeButton.addActionListener(listener);
        modeButton.setMaximumSize(new Dimension(200, 100));
        buttonPanel.add(modeButton, BorderLayout.BEFORE_FIRST_LINE);
        buttonPanel.add(modeButtonPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.LINE_START);
		contentPane.add(boardView, BorderLayout.CENTER);

        togglePlay();

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	@Override
	public File load(){
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
	       return fc.getSelectedFile();
		}
		return null;
	}
	
	@Override
	public String save(){
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fc.showSaveDialog(null);
        String fileName = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            fileName = file + ".txt";
        }
        return fileName;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toggleBuild() {
        // TODO Change the board to grid mode

        ArrayList<JButton> buttonList = new ArrayList<>();
        modeButtonPanel.removeAll();
        modeButtonPanel.setLayout(new GridLayout(4, 1));
        JButton button;

        modeButton.setText("SWITCH TO PLAY MODE");

        button = new JButton("Ball");
        buttonList.add(button);

        button = new JButton("Square");
        buttonList.add(button);

        button = new JButton("Triangle");
        buttonList.add(button);

        button = new JButton("Circle");
        buttonList.add(button);

        button = new JButton("Absorber");
        buttonList.add(button);

        button = new JButton("Left Flipper");
        buttonList.add(button);

        button = new JButton("Right Flipper");
        buttonList.add(button);

        setButtonPanel(buttonList);
        boardView.addMouseListener((MouseListener) listener);
        changeTitle("Build Mode");
	}

	@Override
	public void togglePlay() {
        // TODO Change the board

        ArrayList<JButton> buttonList = new ArrayList<>();
        modeButtonPanel.removeAll();
        modeButtonPanel.setLayout(new GridLayout(4, 1));
        JButton button;

        modeButton.setText("SWITCH TO BUILD MODE");

        button = new JButton("Start");
        buttonList.add(button);

        button = new JButton("Stop");
        buttonList.add(button);

        button = new JButton("Tick");
        buttonList.add(button);

        button = new JButton("Quit");
        buttonList.add(button);

        button = new JButton("Save");
        buttonList.add(button);

        button = new JButton("Load");
        buttonList.add(button);

        setButtonPanel(buttonList);
        boardView.removeMouseListener((MouseListener) listener);
        changeTitle("Play Mode");
	}

	@Override
	public void help() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void about() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

    private void changeTitle(String s) {
        frame.setTitle("Gizmoball - "+s);
    }

    private void setButtonPanel(List<JButton> buttons) {
        Font font = new Font("Arial", Font.BOLD, 12);
        for (JButton b : buttons) {
            b.setFont(font);
            b.addActionListener(listener);
            b.setMaximumSize(new Dimension(100, 100));
            modeButtonPanel.add(b);
        }
    }

}
