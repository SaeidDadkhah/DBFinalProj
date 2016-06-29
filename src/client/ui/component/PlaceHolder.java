package client.ui.component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeid Dadkhah on 2016-06-20 7:34 AM.
 * Project: Server
 */
public class PlaceHolder extends JButton {

    private Window parent;

    public PlaceHolder(Window parent) {
        this.parent = parent;
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        parent.repaint();
    }
}
