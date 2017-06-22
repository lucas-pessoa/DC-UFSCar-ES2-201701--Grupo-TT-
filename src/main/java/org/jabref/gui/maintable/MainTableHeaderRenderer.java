package org.jabref.gui.maintable;

import org.jabref.gui.GUIGlobals;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MainTableHeaderRenderer implements TableCellRenderer {

    private final TableCellRenderer delegate;

    public MainTableHeaderRenderer(TableCellRenderer delegate) {
        this.delegate = delegate;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        // delegate to previously used TableCellRenderer which styles the component
        Component resultFromDelegate = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Changing style is only possible if both value and resultFromDelegate are JLabels
        if ((value instanceof JLabel) && (resultFromDelegate instanceof JLabel)) {
            String text = ((JLabel) value).getText();
            Icon icon = ((JLabel) value).getIcon();
            if (icon == null) {
                ((JLabel) resultFromDelegate).setText(text);
                resultFromDelegate.setFont(GUIGlobals.currentFont);
            } else {
                ((JLabel) resultFromDelegate).setIcon(icon);
                ((JLabel) resultFromDelegate).setText(null);
            }
        }

        return resultFromDelegate;
    }
}
