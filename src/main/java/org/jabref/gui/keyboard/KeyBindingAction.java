package org.jabref.gui.keyboard;

import javafx.application.Platform;
import org.jabref.gui.IconTheme;
import org.jabref.logic.l10n.Localization;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyBindingAction extends AbstractAction {


    public KeyBindingAction() {
        super(Localization.lang("Customize key bindings"));
        this.putValue(Action.SMALL_ICON, IconTheme.JabRefIcon.KEY_BINDINGS.getSmallIcon());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Platform.runLater(() -> {
            KeyBindingsDialogView view = new KeyBindingsDialogView();
            view.show();
        });
    }

}
