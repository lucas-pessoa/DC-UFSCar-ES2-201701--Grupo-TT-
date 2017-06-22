package org.jabref.logic.importer.util;

import org.jabref.model.entry.FieldName;
import org.jabref.model.entry.identifier.*;
import org.jabref.model.strings.StringUtil;

import java.util.Optional;
import java.util.function.Function;

public class IdentifierParser {
    public static Optional<? extends Identifier> parse(String fieldName, String input) {
        if (StringUtil.isBlank(input)) {
            return Optional.empty();
        }

        Function<String, Optional<? extends Identifier>> parser = getParserForField(fieldName);
        return parser.apply(input);
    }

    private static Function<String, Optional<? extends Identifier>> getParserForField(String fieldName) {
        switch (fieldName) {
            case FieldName.DOI:
                return DOI::parse;
            case FieldName.ISBN:
                return ISBN::parse;
            case FieldName.EPRINT:
                return Eprint::build;
            case FieldName.MR_NUMBER:
                return MathSciNetId::parse;
        }

        // By default, just return empty optional
        return input -> Optional.empty();
    }
}
