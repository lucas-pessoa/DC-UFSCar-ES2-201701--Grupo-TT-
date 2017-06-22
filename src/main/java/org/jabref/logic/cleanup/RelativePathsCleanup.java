package org.jabref.logic.cleanup;

import org.jabref.logic.util.io.FileUtil;
import org.jabref.model.FieldChange;
import org.jabref.model.cleanup.CleanupJob;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.LinkedFile;
import org.jabref.model.metadata.FileDirectoryPreferences;

import java.nio.file.Paths;
import java.util.*;

public class RelativePathsCleanup implements CleanupJob {

    private final BibDatabaseContext databaseContext;
    private final FileDirectoryPreferences fileDirectoryPreferences;


    public RelativePathsCleanup(BibDatabaseContext databaseContext, FileDirectoryPreferences fileDirectoryPreferences) {
        this.databaseContext = Objects.requireNonNull(databaseContext);
        this.fileDirectoryPreferences = Objects.requireNonNull(fileDirectoryPreferences);
    }

    @Override
    public List<FieldChange> cleanup(BibEntry entry) {
        List<LinkedFile> fileList = entry.getFiles();
        List<LinkedFile> newFileList = new ArrayList<>();
        boolean changed = false;

        for (LinkedFile fileEntry : fileList) {
            String oldFileName = fileEntry.getLink();
            String newFileName = FileUtil
                    .shortenFileName(Paths.get(oldFileName), databaseContext.getFileDirectoriesAsPaths(fileDirectoryPreferences))
                    .toString();

            LinkedFile newFileEntry = fileEntry;
            if (!oldFileName.equals(newFileName)) {
                newFileEntry = new LinkedFile(fileEntry.getDescription(), newFileName, fileEntry.getFileType());
                changed = true;
            }
            newFileList.add(newFileEntry);
        }

        if (changed) {
            Optional<FieldChange> change = entry.setFiles(newFileList);
            if (change.isPresent()) {
                return Collections.singletonList(change.get());
            } else {
                return Collections.emptyList();
            }
        }

        return Collections.emptyList();
    }

}
