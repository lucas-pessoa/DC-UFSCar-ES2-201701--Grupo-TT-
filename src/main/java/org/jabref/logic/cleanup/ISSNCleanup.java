package org.jabref.logic.cleanup;

import org.jabref.model.FieldChange;
import org.jabref.model.cleanup.CleanupJob;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.FieldName;
import org.jabref.model.entry.identifier.ISSN;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ISSNCleanup implements CleanupJob {

    @Override
    public List<FieldChange> cleanup(BibEntry entry) {
        Optional<String> issnString = entry.getField(FieldName.ISSN);
        if (!issnString.isPresent()) {
            return Collections.emptyList();
        }

        ISSN issn = new ISSN(issnString.get());
        if (issn.isCanBeCleaned()) {
            String newValue = issn.getCleanedISSN();
            FieldChange change = new FieldChange(entry, FieldName.ISSN, issnString.get(), newValue);
            entry.setField(FieldName.ISSN, newValue);
            return Collections.singletonList(change);
        }
        return Collections.emptyList();
    }

}
