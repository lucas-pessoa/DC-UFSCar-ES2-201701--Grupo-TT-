package org.jabref.collab;

import javax.swing.*;
import java.awt.*;

class InfoPane extends JEditorPane {

    public InfoPane() {
        setEditable(false);
        setContentType("text/html");
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g2);
    }
}
