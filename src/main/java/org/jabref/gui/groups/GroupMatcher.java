package org.jabref.gui.groups;

import ca.odell.glazedlists.matchers.Matcher;
import org.jabref.model.entry.BibEntry;

/**
 * Matcher for filtering or sorting the table according to whether entries
 * are tagged as group matches.
 */
public class GroupMatcher implements Matcher<BibEntry> {

    public static final GroupMatcher INSTANCE = new GroupMatcher();

    @Override
    public boolean matches(BibEntry entry) {
        return entry.isGroupHit();
    }
}
