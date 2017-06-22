package org.jabref.logic.importer;

import org.jabref.model.entry.BibEntry;

import java.util.List;

/**
 * Searches web resources for bibliographic information based on a {@link BibEntry}.
 * Useful to complete an existing entry with fetched information.
 * May return multiple search hits.
 */
public interface EntryBasedFetcher extends WebFetcher {

    /**
     * Looks for hits which are matched by the given {@link BibEntry}.
     *
     * @param entry entry to search bibliographic information for
     * @return a list of {@link BibEntry}, which are matched by the query (may be empty)
     */
    List<BibEntry> performSearch(BibEntry entry) throws FetcherException;
}
