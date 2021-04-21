package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class JPanelStoryboardTest {

    @Test
    @UseReporter(ClipboardReporter.class)
    void testInitialJPanel(){
        JPanelStoryboard storyboard = new JPanelStoryboard();
        storyboard.addPanel(new TestStoryPanel());
        AwtApprovals.verify(storyboard);
    }

    class TestStoryPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, this.getWidth()/2, this.getHeight()/2);
            g.fillRect(getWidth()/2, getHeight()/2, getWidth(), getHeight());
        }
    }
}
