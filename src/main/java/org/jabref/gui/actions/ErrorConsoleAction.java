package org.jabref.gui.actions;

import javafx.application.Platform;
import org.jabref.gui.errorconsole.ErrorConsoleView;
import org.jabref.logic.l10n.Localization;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Such an error console can be
 * useful in getting complete bug reports, especially from Windows users,
 * without asking users to run JabRef in a command window to catch the error info.
 * <p/>
 * It offers a separate tab for the log output.
 */
public class ErrorConsoleAction extends AbstractAction {

    public ErrorConsoleAction() {
        super(Localization.menuTitle("View event log"));
        putValue(Action.SHORT_DESCRIPTION, Localization.lang("Display all error messages"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Platform.runLater(() -> new ErrorConsoleView().show());
    }

}
