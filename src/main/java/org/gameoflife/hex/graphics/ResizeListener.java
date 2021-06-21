package org.gameoflife.hex.graphics;

import org.gameoflife.hex.GameOfLifePanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizeListener extends ComponentAdapter {
    private final GameOfLifePanel gameOfLifePanel;

    public ResizeListener(GameOfLifePanel gameOfLifePanel) {
        this.gameOfLifePanel = gameOfLifePanel;
    }

    public void componentResized(ComponentEvent e) {
        gameOfLifePanel.onResize(e.getComponent().getSize());
    }
}
