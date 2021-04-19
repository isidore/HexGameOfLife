package org.gameoflife.hex;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class ResizeListener extends ComponentAdapter {
    private final GameOfLifePanel gameOfLifePanel;

    public ResizeListener(GameOfLifePanel gameOfLifePanel) {
        this.gameOfLifePanel = gameOfLifePanel;
    }

    public void componentResized(ComponentEvent e) {
        gameOfLifePanel.onResize();
    }
}
