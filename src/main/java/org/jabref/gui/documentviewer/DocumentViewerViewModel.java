package org.jabref.gui.documentviewer;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fxmisc.easybind.EasyBind;
import org.jabref.Globals;
import org.jabref.gui.AbstractViewModel;
import org.jabref.gui.StateManager;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.LinkedFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class DocumentViewerViewModel extends AbstractViewModel {

    private StateManager stateManager;
    private ObjectProperty<DocumentViewModel> currentDocument = new SimpleObjectProperty<>();
    private ListProperty<LinkedFile> files = new SimpleListProperty<>();
    private BooleanProperty liveMode = new SimpleBooleanProperty();
    private ObjectProperty<Integer> currentPage = new SimpleObjectProperty<>();
    private IntegerProperty maxPages = new SimpleIntegerProperty();

    public DocumentViewerViewModel(StateManager stateManager) {
        this.stateManager = Objects.requireNonNull(stateManager);

        this.stateManager.getSelectedEntries().addListener((ListChangeListener<? super BibEntry>) c -> {
            // Switch to currently selected entry in live mode
            if (isLiveMode()) {
                setCurrentEntries(this.stateManager.getSelectedEntries());
            }
        });

        this.liveMode.addListener((observable, oldValue, newValue) -> {
            // Switch to currently selected entry if mode is changed to live
            if (oldValue != newValue && newValue) {
                setCurrentEntries(this.stateManager.getSelectedEntries());
            }
        });

        maxPages.bindBidirectional(
                EasyBind.monadic(currentDocument).selectProperty(DocumentViewModel::maxPagesProperty));

        setCurrentEntries(this.stateManager.getSelectedEntries());
    }

    private int getCurrentPage() {
        return currentPage.get();
    }

    public ObjectProperty<Integer> currentPageProperty() {
        return currentPage;
    }

    public IntegerProperty maxPagesProperty() {
        return maxPages;
    }

    private boolean isLiveMode() {
        return liveMode.get();
    }

    public ObjectProperty<DocumentViewModel> currentDocumentProperty() {
        return currentDocument;
    }

    public ListProperty<LinkedFile> filesProperty() {
        return files;
    }

    private void setCurrentEntries(List<BibEntry> entries) {
        if (!entries.isEmpty()) {
            BibEntry firstSelectedEntry = entries.get(0);
            setCurrentEntry(firstSelectedEntry);
        }
    }

    private void setCurrentEntry(BibEntry entry) {
        stateManager.getActiveDatabase().ifPresent(database -> {
            List<LinkedFile> linkedFiles = entry.getFiles();
            // We don't need to switch to the first file, this is done automatically in the UI part
            files.setValue(FXCollections.observableArrayList(linkedFiles));
        });
    }

    private void setCurrentDocument(Path path) {
        try {
            currentDocument.set(new PdfDocumentViewModel(PDDocument.load(path.toFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToFile(LinkedFile file) {
        if (file != null) {
            stateManager.getActiveDatabase().ifPresent(database ->
                    file.findIn(database, Globals.prefs.getFileDirectoryPreferences())
                            .ifPresent(this::setCurrentDocument));
        }
    }

    public BooleanProperty liveModeProperty() {
        return liveMode;
    }

    public void showNextPage() {
        currentPage.set(getCurrentPage() + 1);
    }

    public void showPreviousPage() {
        currentPage.set(getCurrentPage() - 1);
    }
}
