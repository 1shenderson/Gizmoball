package view;


import javax.swing.*;
import java.io.File;
import java.util.Observer;

public interface Display extends Observer{

	public void toggleBuild();

	public void togglePlay();

	public File load();

	public String save();

	public void showAbout();

    public void closeAbout();

    public int showLinkWindow();

    public int showKeyWindow();

    public void listenForBindings(boolean b);

    public void setSelectedButton(JButton button);

    public void changeTitle(String title);
}
