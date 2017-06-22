package org.jabref.gui.fieldeditors;

import javafx.util.StringConverter;

import java.util.List;

public abstract class OptionEditorViewModel<T> extends AbstractEditorViewModel {

    public abstract StringConverter<T> getStringConverter();

    public abstract List<T> getItems();

    public abstract String convertToDisplayText(T object);
}
