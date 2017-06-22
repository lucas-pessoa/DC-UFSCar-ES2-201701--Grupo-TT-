package org.jabref.gui.actions;

import org.jabref.JabRefGUI;
import org.jabref.logic.l10n.Localization;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SearchForUpdateAction extends AbstractAction {

    public SearchForUpdateAction() {
        super(Localization.lang("Check for updates"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JabRefGUI.checkForNewVersion(true);
    }
}
