package org.jabref.logic.formatter.bibtexfields;

import org.jabref.logic.l10n.Localization;
import org.jabref.model.cleanup.Formatter;

import java.util.Objects;

public class ClearFormatter implements Formatter {

    @Override
    public String getName() {
        return Localization.lang("Clear");
    }

    @Override
    public String getKey() {
        return "clear";
    }

    @Override
    public String format(String oldString) {
        Objects.requireNonNull(oldString);
        return "";
    }

    @Override
    public String getDescription() {
        return Localization.lang("Clears the field completely.");
    }

    @Override
    public String getExampleInput() {
        return "Obsolete text";
    }

}
