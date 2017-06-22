package org.jabref.model.database;

import org.jabref.model.entry.BibEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EntrySorter {

    private final List<BibEntry> entries;

    public EntrySorter(List<BibEntry> entries, Comparator<BibEntry> comparator) {
        this.entries = new ArrayList<>(entries);
        Collections.sort(this.entries, comparator);
    }

    public BibEntry getEntryAt(int pos) {
        return entries.get(pos);
    }

    public int getEntryCount() {
        return entries.size();
    }

}
