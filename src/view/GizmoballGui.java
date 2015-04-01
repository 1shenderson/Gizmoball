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
    private JMenuItem menuPlayMode;
    private JMenuItem menuBuildMode;
    private JFrame aboutWindow;

	public GizmoballGui(Board board, int L) {
		this.board = board;
        this.L = L;
		listener = new RunListener(board, this);
		keyList = new keyListen(board);
        loadImages();
	}

    public void listenForBindings(boolean b) {
        if (b) {
            frame.removeKeyListener(keyList);
            frame.addKeyListener((KeyListener) listener);
        } else {
            frame.removeKeyListener((KeyListener) listener);
            frame.addKeyListener(keyList);
        }
    }

    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu subMenu;
        JMenuItem menuItem;
        JSeparator separator = new JSeparator();
        // GAME SUBMENU
        subMenu = new JMenu("Game");
        // Build Mode
        menuBuildMode = new JMenuItem("Build Mode");
        menuBuildMode.addActionListener(listener);
        subMenu.add(menuBuildMode);
        // Play Mode
        menuPlayMode = new JMenuItem("Play Mode");
        menuPlayMode.addActionListener(listener);
        subMenu.add(menuPlayMode);
        subMenu.add(separator);
        // Save
        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(listener);
        subMenu.add(menuItem);
        // Load
        menuItem = new JMenuItem("Load");
        menuItem.addActionListener(listener);
        subMenu.add(menuItem);
        menuBar.add(subMenu);
        // ABOUT SUBMENU
        subMenu = new JMenu("About");
        menuItem = new JMenuItem("About");
        menuItem.addActionListener(listener);
        subMenu.add(menuItem);
        menuBar.add(subMenu);

        frame.setJMenuBar(menuBar);
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

        buildMenuBar();
        toggleBuild();

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
        JComponent component;

        modeButton.setText("<html>SWITCH TO<br>PLAY MODE</html>");

        /* GridLayout first fills each row, before filling the next row. Because our layout is on the bottom, it we need
           to fill each column first, then each row. GridLayout does not support that, so we'll just have to add these
           buttons in a very specific order. */

        // FIRST ROW - 4 placements, 1 space between sections, 1 storage
        component = new JButton("Ball");
        buttonList.add(component);

        component = new JButton("Square");
        buttonList.add(component);

        component = new JButton("Triangle");
        buttonList.add(component);

        component = new JButton("Circle");
        buttonList.add(component);

        component = new JLabel();
        buttonList.add(component);

        component = new JButton("Move");
        buttonList.add(component);

        component = new JButton("Link");
        buttonList.add(component);

        component = new JButton("Delete");
        buttonList.add(component);

        // SECOND ROW - 4 placements (one of them is a filler space), 1 space between sections, 1 storage
        component = new JButton("Absorber");
        buttonList.add(component);

        component = new JButton("Left Flipper");
        buttonList.add(component);

        component = new JButton("Right Flipper");
        buttonList.add(component);

        component = new JLabel();
        buttonList.add(component);

        component = new JLabel();
        buttonList.add(component);

        component = new JButton("Rotate");
        buttonList.add(component);

        component = new JButton("Unlink");
        buttonList.add(component);

        component = new JLabel();
        buttonList.add(component);

        setButtonPanel(buttonList, new Dimension(32, 32));
        boardView.addMouseListener((MouseListener) listener);
        boardView.addKeyListener(keyList);
        boardView.setGridEnabled(true);
        menuBuildMode.setEnabled(false);
        menuPlayMode.setEnabled(true);
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
        menuBuildMode.setEnabled(true);
        menuPlayMode.setEnabled(false);
        changeTitle("Play Mode");
	}

	@Override
	public void showAbout() {
        if (aboutWindow == null) {
            aboutWindow = new JFrame("About Gizmoball");
            JPanel panel = new JPanel(new BorderLayout());
            JTextArea textArea = new JTextArea();
            aboutWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            aboutWindow.setContentPane(panel);
            JButton button = new JButton("OK");
            button.addActionListener(listener);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setText("Designed by Sebastian Korkosz, Scott Henderson, Alan Wong, Andrew Mortimer and Craig " +
                    "Chalmers.\n" +
                    "\n" +
                    "We created this game as an assignment for CS308 Building Software Systems at the University of " +
                    "Strathclyde\n" +
                    "\n\n" +
                    "Q. How do I create a board? A. Select ‘build mode’ then you have the option of loading in a saved " +
                    "gizmoball format file or creating your own board.\n" +
                    "\n" +
                    "Q. How do I load in a file?\n" +
                    "A. From build mode, select ‘load’ then go through your file directories and select the file you " +
                    "wish to load. Then change to play mode and select play and the board will be loaded in. \n" +
                    "\n" +
                    "Q. How do I create a custom board?\n" +
                    "A. When in build mode, to add a circle, triangle, square or flipper gizmo, select the gizmo you wish " +
                    "to add, then click on the grid position you wish to add the gizmo. To add an absorber, click on the " +
                    "absorber then on the grid, click the position of a corner then drag the mouse to another corner and " +
                    "release the mouse to draw the absorber in that area. To add a ball you can select a ball then the grid " +
                    "position you want the ball to be placed.\n" +
                    "\n" +
                    "Q. How do I move a gizmo already on the board?\n" +
                    "A. Select the move option and then the gizmo you wish to move. Click on it’s new position on the " +
                    "grid.\n" +
                    "\n" +
                    "Q. How do I rotate a gizmo?\n" +
                    "A. The only gizmo, which can be rotated, are triangles, you can rotate a triangle by selecting " +
                    "triangle, then the rotate button, this rotates the triangle. You can then add it to the board. \n" +
                    "\n" +
                    "Q. How do I delete a gizmo?\n" +
                    "A. Select the delete option then the gizmo you wish to delete.\n" +
                    "\n" +
                    "Q. How do I connect a gizmo to a key?\n" +
                    "A. Select the connect option followed by the gizmo you wish to have the key correspond to. Then enter " +
                    "the key for the gizmo. This gizmo will now be triggered when the key is pressed.\n" +
                    "\n" +
                    "Q. How do I connect a gizmo to a gizmo? \n" +
                    "A. Select the connect option followed by the gizmo you wish to have the gizmo correspond to. Then \n" +
                    "select the other gizmo. The first gizmo selected will now be triggered when the other gizmo is \n" +
                    "triggered.\n" +
                    "\n" +
                    "Q. How do I save a game?\n" +
                    "A. When in build mode, select save game, then give the game a name and select an appropriate place " +
                    "in your file directory system. \n" +
                    "\n" +
                    "Q. How do I play the game?\n" +
                    "A. Once you have your board set up, select ‘play mode’ and then click on start to begin the game. ");


            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 700));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.add(button, BorderLayout.AFTER_LAST_LINE);
            aboutWindow.pack();
            aboutWindow.setLocationRelativeTo(null);
            aboutWindow.setVisible(true);
        } else {
            aboutWindow.toFront();
        }
	}

    public void closeAbout() {
        if (aboutWindow != null) {
            aboutWindow.setVisible(false);
            aboutWindow.dispose();
            aboutWindow = null;
        }
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

    public int showKeyWindow() {
        System.out.println("GOT HERE");
        Object[] options = {"Key LIFT", "Key PRESS"};
        return JOptionPane.showOptionDialog(frame, "<HTML>Would you like the trigger to happen when you're" +
                        "<BR>LIFTING the key or PRESSING it?</HTML>",
                "Gizmo Key Link Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
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