package view;


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
}
