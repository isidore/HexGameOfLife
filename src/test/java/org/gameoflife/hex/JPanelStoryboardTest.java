package org.gameoflife.hex;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class JPanelStoryboardTest {

    @Test
    @UseReporter(MyFileCaptureReporter.class)
    void testInitialJPanel() {
        JPanelStoryboard storyboard = new JPanelStoryboard();
        storyboard.addPanel(new TestStoryPanel(3));
        storyboard.addPanel(new TestStoryPanel(2));
        AwtApprovals.verify(storyboard);
    }

    class TestStoryPanel extends JPanel {
        int number;

        public TestStoryPanel(int number) {
            this.number = number;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.BLUE);

            int y = 0;

            for (int i = 0; i < number; i++) {
                int x = 0;
                int height = y + this.getHeight() / number;
                for (int j = 0; j < number; j++) {
                    int width = x + this.getWidth() / number;

                    if (i % 2 == j % 2) {
                        g.fillRect(x, y, width, height);
                    }

                    x = width;
                }
                y = height;
            }
        }
    }
}
