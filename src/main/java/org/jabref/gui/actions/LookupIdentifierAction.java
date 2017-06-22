package org.jabref.gui.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jabref.gui.BasePanel;
import org.jabref.gui.JabRefFrame;
import org.jabref.gui.worker.LookupIdentifiersWorker;
import org.jabref.logic.importer.IdFetcher;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LookupIdentifierAction extends MnemonicAwareAction {

    private static final Log LOGGER = LogFactory.getLog(LookupIdentifierAction.class);

    private final JabRefFrame frame;
    private final IdFetcher fetcher;

    public LookupIdentifierAction(JabRefFrame frame, IdFetcher fetcher) {
        super();
        this.frame = frame;
        this.fetcher = fetcher;

        putValue(Action.NAME, fetcher.getIdentifierName());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            BasePanel.runWorker(new LookupIdentifiersWorker(frame, fetcher));
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
