package controller;
// API
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.Timer;
// Local
import model.Model;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class RunListener implements ActionListener {

    private Timer timer;
    private Model model;

    public RunListener(Model m) {
        model = m;
        timer = new Timer(50, this);

    }

    @Override
    public final void actionPerformed(final ActionEvent e) {

        final JFileChooser fc = new JFileChooser();
        int returnVal;

        if (e.getSource() == timer) {
            model.moveBall();
        } else
            switch (e.getActionCommand()) {
                case "Start":
                    timer.start();
                    break;
                case "Stop":
                    timer.stop();
                    break;
                case "Tick":
                    model.moveBall();
                    break;
                case "Quit":
                    System.exit(0);
                    break;
            }
    }
}
