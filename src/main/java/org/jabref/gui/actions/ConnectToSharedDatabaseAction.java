package org.jabref.gui.actions;

import org.jabref.gui.JabRefFrame;
import org.jabref.gui.shared.ConnectToSharedDatabaseDialog;
import org.jabref.logic.l10n.Localization;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The action concerned with opening a shared database.
 */
public class ConnectToSharedDatabaseAction extends MnemonicAwareAction {

    private final JabRefFrame jabRefFrame;


    public ConnectToSharedDatabaseAction(JabRefFrame jabRefFrame) {
        super();
        this.jabRefFrame = jabRefFrame;
        putValue(Action.NAME, Localization.menuTitle("Connect to shared database"));
        putValue(Action.SHORT_DESCRIPTION, Localization.lang("Connect to shared database"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ConnectToSharedDatabaseDialog connectToSharedDatabaseDialog = new ConnectToSharedDatabaseDialog(jabRefFrame);
        connectToSharedDatabaseDialog.setVisible(true);
    }
}
