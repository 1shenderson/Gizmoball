package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Board;
import controller.RunListener;
import controller.keyListen;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class GizmoballGui implements Display {
    private int L;
	private Board board;
	private JFrame frame;
	private ActionListener listener;
	private KeyListener keyList;
	private BoardView boardView;
    private JButton modeButton;
    private JButton selectedButton;
    private JPanel modeButtonPanel;
    private Map<String, Image> imageList;

	public GizmoballGui(Board board, int L) {
		this.board = board;
        this.L = L;
		listener = new RunListener(board, this);
		keyList = new keyListen(board);
        loadImages();
	}

	public void createAndShowGUI() {
		frame = new JFrame("Gizmoball");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addKeyListener(keyList);

		// Board is passed the Model so it can act as Observer
		boardView = new BoardView(20, 20, board, L);

        Font gf = new Font("Arial", Font.BOLD, 12);
		Container contentPane = frame.getContentPane();
        contentPane.addKeyListener(keyList);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.addKeyListener(keyList);

        modeButtonPanel = new JPanel();
        modeButtonPanel.setFocusable(false);

        modeButton = new JButton();
        modeButton.setFont(gf);
        modeButton.addActionListener(listener);
        modeButton.setMaximumSize(new Dimension(200, 100));
        modeButton.setFocusable(false);
        modeButton.setPreferredSize(new Dimension(128, 64));

        buttonPanel.add(modeButton, BorderLayout.LINE_START);
        buttonPanel.add(modeButtonPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
		contentPane.add(boardView, BorderLayout.CENTER);


        togglePlay();

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

    public void loadImages() {
        try {
            Image image;
            String imageName;
            imageList = new HashMap<>();

            imageName = "Absorber";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Ball";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Circle";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Delete";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Left Flipper";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Link";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Move";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Right Flipper";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Rotate";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Square";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Triangle";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);
            imageName = "Unlink";
            image = ImageIO.read(new File("images/"+imageName+".png"));
            imageList.put(imageName, image);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

        ArrayList<JComponent> buttonList = new ArrayList<>();
        modeButtonPanel.removeAll();
        modeButtonPanel.setLayout(new GridLayout(2, 10));
        JComponent button;

        modeButton.setText("<html>SWITCH TO<br>PLAY MODE</html>");

        /* GridLayout first fills each row, before filling the next row. Because our layout is on the bottom, it we need
           to fill each column first, then each row. GridLayout does not support that, so we'll just have to add these
           buttons in a very specific order. */

        // FIRST ROW - 4 placements, 1 space between sections, 1 storage
        button = new JButton("Ball");
        buttonList.add(button);

        button = new JButton("Square");
        buttonList.add(button);

        button = new JButton("Triangle");
        buttonList.add(button);

        button = new JButton("Circle");
        buttonList.add(button);

        button = new JLabel();
        buttonList.add(button);

        button = new JButton("Move");
        buttonList.add(button);

        button = new JButton("Link");
        buttonList.add(button);

        button = new JButton("Delete");
        buttonList.add(button);

        button = new JLabel();
        buttonList.add(button);

        button = new JButton("Save");
        buttonList.add(button);

        // SECOND ROW - 4 placements (one of them is a filler space), 1 space between sections, 1 storage
        button = new JButton("Absorber");
        buttonList.add(button);

        button = new JButton("Left Flipper");
        buttonList.add(button);

        button = new JButton("Right Flipper");
        buttonList.add(button);

        button = new JLabel();
        buttonList.add(button);

        button = new JLabel();
        buttonList.add(button);

        button = new JButton("Rotate");
        buttonList.add(button);

        button = new JButton("Unlink");
        buttonList.add(button);

        button = new JLabel();
        buttonList.add(button);

        button = new JLabel();
        buttonList.add(button);

        button = new JButton("Load");
        buttonList.add(button);

        setButtonPanel(buttonList, new Dimension(32, 32));
        boardView.addMouseListener((MouseListener) listener);
        boardView.addKeyListener(keyList);
        boardView.setGridEnabled(true);
        changeTitle("Build Mode");
	}

	@Override
	public void togglePlay() {
        // TODO Change the board

        ArrayList<JComponent> buttonList = new ArrayList<>();
        modeButtonPanel.removeAll();
        modeButtonPanel.setLayout(new GridLayout(1, 3));
        JButton button;

        modeButton.setText("<html>SWITCH TO<br>BUILD MODE</html>");

        button = new JButton("Start");
        buttonList.add(button);

        button = new JButton("Stop");
        buttonList.add(button);

        button = new JButton("Tick");
        buttonList.add(button);

        modeButton.addKeyListener(keyList);

        setButtonPanel(buttonList, new Dimension(64, 64));
        boardView.removeMouseListener((MouseListener) listener);
        boardView.setGridEnabled(false);
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

    public int showLinkWindow() {
        Object[] options = {"Another Gizmo", "A Key"};
        return JOptionPane.showOptionDialog(frame, "What would you like to trigger the selected gizmo?",
               "Gizmo Link Options",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE,
               null,
               options,
               options[1]);
    }

    public void setSelectedButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setSelected(false);
        }
        if (button != null) {
            button.setSelected(true);
            selectedButton = button;
        }
    }

    public void changeTitle(String s) {
        frame.setTitle("Gizmoball - "+s);
    }

    private void setButtonPanel(List<JComponent> components, Dimension size) {
        Font font = new Font("Arial", Font.BOLD, 12);
        for (JComponent comp : components) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setToolTipText(button.getText());
                button.addActionListener(listener);
                // Check if the button has a corresponding image to replace the text
                if (imageList.containsKey(button.getText())) {
                    // Load the image from the image list
                    Image image = imageList.get(button.getText());
                    // Rescale the image to the button size
                    image = image.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
                    // Turn it into an icon
                    ImageIcon icon = new ImageIcon(image);
                    button.setIcon(icon);
                    // Remove the text, manually set its action command instead (for the controller)
                    button.setActionCommand(button.getText());
                    button.setText("");
                }
            }
            comp.setFont(font);
            comp.setPreferredSize(size);
            comp.setFocusable(false);
            modeButtonPanel.add(comp);
        }
    }

}