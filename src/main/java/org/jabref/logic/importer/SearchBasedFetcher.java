package org.jabref.logic.importer;

import org.jabref.model.entry.BibEntry;

import java.util.List;

/**
 * Searches web resources for bibliographic information based on a free-text query.
 * May return multiple search hits.
 */
public interface SearchBasedFetcher extends WebFetcher {

    /**
     * Looks for hits which are matched by the given free-text query.
     *
     * @param query search string
     * @return a list of {@link BibEntry}, which are matched by the query (may be empty)
     */
    List<BibEntry> performSearch(String query) throws FetcherException;
}
