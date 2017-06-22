package org.jabref.logic.formatter;

import org.jabref.logic.formatter.bibtexfields.*;
import org.jabref.logic.formatter.casechanger.*;
import org.jabref.logic.formatter.minifier.MinifyNameListFormatter;
import org.jabref.logic.layout.format.LatexToUnicodeFormatter;
import org.jabref.model.cleanup.Formatter;

import java.util.*;

public class Formatters {

    public static final List<Formatter> CONVERTERS = Arrays.asList(
            new HtmlToLatexFormatter(),
            new HtmlToUnicodeFormatter(),
            new LatexToUnicodeFormatter(),
            new UnicodeToLatexFormatter()
    );

    public static final List<Formatter> CASE_CHANGERS = Arrays.asList(
            new CapitalizeFormatter(),
            new LowerCaseFormatter(),
            new ProtectTermsFormatter(),
            new SentenceCaseFormatter(),
            new TitleCaseFormatter(),
            new UpperCaseFormatter()
    );

    public static final List<Formatter> OTHERS = Arrays.asList(
            new ClearFormatter(),
            new LatexCleanupFormatter(),
            new MinifyNameListFormatter(),
            new NormalizeDateFormatter(),
            new NormalizeMonthFormatter(),
            new NormalizeNamesFormatter(),
            new NormalizePagesFormatter(),
            new OrdinalsToSuperscriptFormatter(),
            new RemoveBracesFormatter(),
            new UnitsToLatexFormatter(),
            new EscapeUnderscoresFormatter()
    );

    public static final List<Formatter> ALL = new ArrayList<>();

    private Formatters() {
    }

    public static Optional<Formatter> getFormatterForModifier(String modifier) {
        Objects.requireNonNull(modifier);
        Optional<Formatter> formatter = ALL.stream().filter(f -> f.getKey().equals(modifier)).findAny();
        if (formatter.isPresent()) {
            return formatter;
        }
        switch (modifier) {
            case "lower":
                return Optional.of(new LowerCaseFormatter());
            case "upper":
                return Optional.of(new UpperCaseFormatter());
            default:
                return Optional.empty();
        }
    }

    static {
        ALL.addAll(CONVERTERS);
        ALL.addAll(CASE_CHANGERS);
        ALL.addAll(OTHERS);
    }
}
