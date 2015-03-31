package view;


import javax.swing.*;
import java.io.File;
import java.util.Observer;

public interface Display extends Observer{

	public void toggleBuild();

    /**
     * Toggles the play mode. Changes the display to real-time,
     */
	public void togglePlay();

	public File load();

	public String save();

	public void help();

	public void about();

	public void exit();

    public int showLinkWindow();

    public void setSelectedButton(JButton button);

    public void changeTitle(String title);
}
