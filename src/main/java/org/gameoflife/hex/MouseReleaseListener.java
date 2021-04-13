package org.gameoflife.hex;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseReleaseListener extends MouseAdapter {
    GameOfLifePanel gameOfLifePanel;
    @Override
    public void mouseReleased(MouseEvent e) {
        Coordinates hex = gameOfLifePanel.setAliveAt(e.getPoint());
    }
}
