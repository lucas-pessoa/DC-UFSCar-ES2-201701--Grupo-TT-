package org.jabref.logic.integrity;

import org.jabref.logic.l10n.Localization;

import java.util.Locale;
import java.util.Optional;

public class PersonNamesChecker implements ValueChecker {

    @Override
    public Optional<String> checkValue(String value) {
        String valueTrimmedAndLowerCase = value.trim().toLowerCase(Locale.ROOT);
        if (valueTrimmedAndLowerCase.startsWith("and ") || valueTrimmedAndLowerCase.startsWith(",")) {
            return Optional.of(Localization.lang("should start with a name"));
        } else if (valueTrimmedAndLowerCase.endsWith(" and") || valueTrimmedAndLowerCase.endsWith(",")) {
            return Optional.of(Localization.lang("should end with a name"));
        }
        return Optional.empty();
    }
}
