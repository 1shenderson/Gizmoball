package model.gizmo;

import java.awt.Color;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class SquareBumper extends AbstractSquare {
    public SquareBumper(int x, int y, int width, int height, String id) {
        super(x, y, width, height, id);
        color = Color.RED;
    }
}
