package org.jabref.logic.importer;

import org.jabref.model.entry.BibEntry;

import java.io.InputStream;
import java.util.List;

/**
 * A parser converts an {@link InputStream} into a list of {@link BibEntry}.
 */
public interface Parser {

    List<BibEntry> parseEntries(InputStream inputStream) throws ParseException;
}
