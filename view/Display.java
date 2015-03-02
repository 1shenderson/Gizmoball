package view;


import java.io.File;
import java.util.Observer;

public interface Display extends Observer{

	public void toggleBuild();

	public void togglePlay();

	public File load();

	public String save();

	public void help();

	public void about();

	public void exit();

}
