package org.jabref.gui.fieldeditors;

import javafx.util.StringConverter;
import org.jabref.model.database.BibDatabaseMode;
import org.jabref.model.entry.Month;
import org.jabref.model.strings.StringUtil;

import java.util.Arrays;
import java.util.List;

public class MonthEditorViewModel extends OptionEditorViewModel<Month> {
    private BibDatabaseMode databaseMode;

    public MonthEditorViewModel(BibDatabaseMode databaseMode) {
        this.databaseMode = databaseMode;
    }

    @Override
    public StringConverter<Month> getStringConverter() {
        return new StringConverter<Month>() {
            @Override
            public String toString(Month object) {
                if (object == null) {
                    return null;
                } else {
                    if (databaseMode == BibDatabaseMode.BIBLATEX) {
                        return String.valueOf(object.getNumber());
                    } else {
                        return object.getJabRefFormat();
                    }
                }
            }

            @Override
            public Month fromString(String string) {
                if (StringUtil.isNotBlank(string)) {
                    return Month.parse(string).orElse(null);
                } else {
                    return null;
                }
            }
        };
    }

    @Override
    public List<Month> getItems() {
        return Arrays.asList(Month.values());
    }

    @Override
    public String convertToDisplayText(Month object) {
        return object.getFullName();
    }
}
