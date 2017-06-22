package org.jabref.gui.journals;

import javafx.application.Platform;
import org.jabref.gui.actions.MnemonicAwareAction;
import org.jabref.logic.l10n.Localization;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageJournalsAction extends MnemonicAwareAction {

    public ManageJournalsAction() {
        super();
        putValue(Action.NAME, Localization.menuTitle("Manage journal abbreviations"));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Platform.runLater(() -> new ManageJournalAbbreviationsView().show());
    }
}
