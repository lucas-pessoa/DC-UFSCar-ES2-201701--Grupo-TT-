package org.jabref.logic.pdf;

import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.LinkedFile;
import org.jabref.model.pdf.FileAnnotation;
import org.jabref.preferences.JabRefPreferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Here all PDF files attached to a BibEntry are scanned for annotations using a PdfAnnotationImporter.
 */
public class EntryAnnotationImporter {

    private final BibEntry entry;

    /**
     * @param entry The BibEntry whose attached files are scanned for annotations.
     */
    public EntryAnnotationImporter(BibEntry entry) {
        this.entry = entry;
    }

    /**
     * Filter files with a web address containing "www."
     *
     * @return a list of file parsed files
     */
    private List<LinkedFile> getFilteredFileList() {
        return entry.getFiles().stream()
                .filter(parsedFileField -> parsedFileField.getFileType().equalsIgnoreCase("pdf"))
                .filter(parsedFileField -> !parsedFileField.isOnlineLink()).collect(Collectors.toList());
    }

    /**
     * Reads the annotations from the files that are attached to a BibEntry.
     *
     * @param databaseContext The context is needed for the importer.
     * @return Map from each PDF to a list of file annotations
     */
    public Map<String, List<FileAnnotation>> importAnnotationsFromFiles(BibDatabaseContext databaseContext) {
        Map<String, List<FileAnnotation>> annotations = new HashMap<>();
        AnnotationImporter importer = new PdfAnnotationImporter();

        //import annotationsOfFiles if the selected files are valid which is checked in getFilteredFileList()
        for (LinkedFile linkedFile : this.getFilteredFileList()) {
            linkedFile.findIn(databaseContext, JabRefPreferences.getInstance().getFileDirectoryPreferences())
                    .ifPresent(file -> annotations.put(file.getFileName().toString(), importer.importAnnotations(file)));
        }
        return annotations;
    }
}
