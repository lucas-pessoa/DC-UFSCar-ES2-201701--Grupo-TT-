package org.jabref.logic.integrity;

import org.jabref.logic.l10n.Localization;
import org.jabref.model.entry.identifier.DOI;

import java.util.Optional;

public class DOIValidityChecker implements ValueChecker {

    @Override
    public Optional<String> checkValue(String value) {
        if (DOI.isValid(value)) {
            return Optional.empty();
        } else {
            return Optional.of(Localization.lang("DOI %0 is invalid", value));
        }
    }
}
