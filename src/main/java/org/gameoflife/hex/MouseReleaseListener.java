package org.gameoflife.hex;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseReleaseListener extends MouseAdapter {
    GameOfLifePanel gameOfLifePanel;

    public MouseReleaseListener(GameOfLifePanel gameOfLifePanel) {
        this.gameOfLifePanel = gameOfLifePanel;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gameOfLifePanel.setAliveAt(e.getPoint());
    }
}
