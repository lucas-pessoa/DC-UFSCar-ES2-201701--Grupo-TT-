package org.jabref.logic.autocompleter;

import org.jabref.logic.journals.JournalAbbreviationLoader;
import org.jabref.model.entry.FieldName;
import org.jabref.model.entry.FieldProperty;
import org.jabref.model.entry.InternalBibtexFields;

import java.util.Objects;

/**
 * Returns an autocompleter to a given fieldname.
 *
 * @author kahlert, cordes
 */
public class AutoCompleterFactory {

    private final AutoCompletePreferences preferences;
    private final JournalAbbreviationLoader abbreviationLoader;


    public AutoCompleterFactory(AutoCompletePreferences preferences, JournalAbbreviationLoader abbreviationLoader) {
        this.preferences = Objects.requireNonNull(preferences);
        this.abbreviationLoader = Objects.requireNonNull(abbreviationLoader);
    }

    public AutoCompleter<String> getFor(String fieldName) {
        Objects.requireNonNull(fieldName);

        if (InternalBibtexFields.getFieldProperties(fieldName).contains(FieldProperty.PERSON_NAMES)) {
            return new NameFieldAutoCompleter(fieldName, preferences);
        } else if (InternalBibtexFields.getFieldProperties(fieldName).contains(FieldProperty.SINGLE_ENTRY_LINK)) {
            return new BibtexKeyAutoCompleter(preferences);
        } else if (InternalBibtexFields.getFieldProperties(fieldName).contains(FieldProperty.JOURNAL_NAME)
                || FieldName.PUBLISHER.equals(fieldName)) {
            return new JournalAutoCompleter(fieldName, preferences, abbreviationLoader);
        } else {
            return new DefaultAutoCompleter(fieldName, preferences);
        }
    }

    public AutoCompleter<String> getPersonAutoCompleter() {
        return new NameFieldAutoCompleter(InternalBibtexFields.getPersonNameFields(), true, preferences);
    }

}
