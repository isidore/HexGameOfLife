package org.gameoflife.hex;

import javax.swing.*;

public class JPanelStoryboard extends JPanel{

    public JPanelStoryboard() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void addPanel(JPanel jPanel) {
        this.add(jPanel);
    }
}
