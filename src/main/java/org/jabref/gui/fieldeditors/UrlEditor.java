package org.jabref.gui.fieldeditors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import org.jabref.gui.DialogService;
import org.jabref.gui.util.ControlHelper;
import org.jabref.model.entry.BibEntry;

import java.util.Optional;

public class UrlEditor extends HBox implements FieldEditorFX {

    private final String fieldName;
    @FXML private UrlEditorViewModel viewModel;
    @FXML private EditorTextArea textArea;
    private Optional<BibEntry> entry;

    public UrlEditor(String fieldName, DialogService dialogService) {
        this.fieldName = fieldName;
        this.viewModel = new UrlEditorViewModel(dialogService);

        ControlHelper.loadFXMLForControl(this);

        textArea.textProperty().bindBidirectional(viewModel.textProperty());
    }

    public UrlEditorViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void bindToEntry(BibEntry entry) {
        this.entry = Optional.of(entry);
        viewModel.bindToEntry(fieldName, entry);
    }

    @Override
    public Parent getNode() {
        return this;
    }

    @FXML
    private void openExternalLink(ActionEvent event) {
        viewModel.openExternalLink();
    }
}
