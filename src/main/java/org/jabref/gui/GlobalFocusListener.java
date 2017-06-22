package org.jabref.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GlobalFocusListener implements FocusListener {
    private Component focused;

    @Override
    public void focusGained(FocusEvent e) {
        if (!e.isTemporary()) {
            focused = (Component) e.getSource();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        // Do nothing
    }

    public JComponent getFocused() {
        return (JComponent) focused;
    }

    public void setFocused(Component c) {
        focused = c;
    }
}
