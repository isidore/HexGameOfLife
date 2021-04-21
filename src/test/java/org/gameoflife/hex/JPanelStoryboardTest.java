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
        storyboard.addPanel(new JPanel());
        AwtApprovals.verify(storyboard);
    }

}
