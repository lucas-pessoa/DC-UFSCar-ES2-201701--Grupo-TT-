package org.jabref.logic.integrity;

import org.jabref.logic.l10n.Localization;

import java.util.Optional;

public class UrlChecker implements ValueChecker {

    @Override
    public Optional<String> checkValue(String value) {
        if (!value.contains("://")) {
            return Optional.of(Localization.lang("should contain a protocol") + ": http[s]://, file://, ftp://, ...");
        }

        return Optional.empty();
    }
}
