package org.jabref.logic.formatter;

import org.jabref.logic.formatter.bibtexfields.*;
import org.jabref.logic.formatter.casechanger.*;
import org.jabref.logic.formatter.minifier.MinifyNameListFormatter;
import org.jabref.logic.layout.format.LatexToUnicodeFormatter;
import org.jabref.logic.protectedterms.ProtectedTermsLoader;
import org.jabref.logic.protectedterms.ProtectedTermsPreferences;
import org.jabref.model.cleanup.Formatter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FormatterTest {

    public Formatter formatter;


    public FormatterTest(Formatter formatter) {
        this.formatter = formatter;
    }

    @BeforeClass
    public static void setUp() {
        ProtectTermsFormatter
                .setProtectedTermsLoader(
                        new ProtectedTermsLoader(new ProtectedTermsPreferences(ProtectedTermsLoader.getInternalLists(),
                                Collections.emptyList(), Collections.emptyList(), Collections.emptyList())));
    }

    @Test
    public void getNameReturnsNotNull() {
        assertNotNull(formatter.getName());
    }

    @Test
    public void getNameReturnsNotEmpty() {
        assertNotEquals("", formatter.getName());
    }

    @Test
    public void getKeyReturnsNotNull() {
        assertNotNull(formatter.getKey());
    }

    @Test
    public void getKeyReturnsNotEmpty() {
        assertNotEquals("", formatter.getKey());
    }

    @Test(expected = NullPointerException.class)
    public void formatOfNullThrowsException() {
        formatter.format(null);
    }

    @Test
    public void formatOfEmptyStringReturnsEmpty() {
        assertEquals("", formatter.format(""));
    }

    @Test
    public void formatNotReturnsNull() {
        assertNotNull(formatter.format("string"));
    }

    @Test
    public void getDescriptionAlwaysNonEmpty() {
        assertFalse(formatter.getDescription().isEmpty());
    }

    @Test
    public void getExampleInputAlwaysNonEmpty() {
        assertFalse(formatter.getExampleInput().isEmpty());
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> instancesToTest() {
        // all classes implementing {@link net.sf.jabref.model.cleanup.Formatter}
        // sorted alphabetically
        // Alternative: Use reflection - https://github.com/ronmamo/reflections
        // @formatter:off
        return Arrays.asList(
                new Object[]{new CapitalizeFormatter()},
                new Object[]{new ClearFormatter()},
                new Object[]{new HtmlToLatexFormatter()},
                new Object[]{new HtmlToUnicodeFormatter()},
                new Object[]{new IdentityFormatter()},
                new Object[]{new LatexCleanupFormatter()},
                new Object[]{new LatexToUnicodeFormatter()},
                new Object[]{new LowerCaseFormatter()},
                new Object[]{new MinifyNameListFormatter()},
                new Object[]{new NormalizeDateFormatter()},
                new Object[]{new NormalizeMonthFormatter()},
                new Object[]{new NormalizeNamesFormatter()},
                new Object[]{new NormalizePagesFormatter()},
                new Object[]{new OrdinalsToSuperscriptFormatter()},
                new Object[]{new ProtectTermsFormatter()},
                new Object[]{new RemoveBracesFormatter()},
                new Object[]{new SentenceCaseFormatter()},
                new Object[]{new TitleCaseFormatter()},
                new Object[]{new UnicodeToLatexFormatter()},
                new Object[]{new UnitsToLatexFormatter()},
                new Object[]{new UpperCaseFormatter()}
        );
        // @formatter:on
    }
}
