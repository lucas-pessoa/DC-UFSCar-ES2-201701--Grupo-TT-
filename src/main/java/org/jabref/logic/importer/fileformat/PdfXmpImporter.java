package org.jabref.logic.importer.fileformat;

import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.l10n.Localization;
import org.jabref.logic.util.FileExtensions;
import org.jabref.logic.xmp.XMPPreferences;
import org.jabref.logic.xmp.XMPUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Wraps the XMPUtility function to be used as an Importer.
 */
public class PdfXmpImporter extends Importer {

    private final XMPPreferences xmpPreferences;


    public PdfXmpImporter(XMPPreferences xmpPreferences) {
        this.xmpPreferences = xmpPreferences;
    }

    @Override
    public String getName() {
        return Localization.lang("XMP-annotated PDF");
    }

    @Override
    public FileExtensions getExtensions() {
        return FileExtensions.XMP;
    }

    @Override
    public ParserResult importDatabase(BufferedReader reader) throws IOException {
        Objects.requireNonNull(reader);
        throw new UnsupportedOperationException(
                "PdfXmpImporter does not support importDatabase(BufferedReader reader)."
                        + "Instead use importDatabase(Path filePath, Charset defaultEncoding).");
    }

    @Override
    public ParserResult importDatabase(Path filePath, Charset defaultEncoding) {
        Objects.requireNonNull(filePath);
        try {
            return new ParserResult(XMPUtil.readXMP(filePath, xmpPreferences));
        } catch (IOException exception) {
            return ParserResult.fromError(exception);
        }
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader) throws IOException {
        Objects.requireNonNull(reader);
        throw new UnsupportedOperationException(
                "PdfXmpImporter does not support isRecognizedFormat(BufferedReader reader)."
                        + "Instead use isRecognizedFormat(Path filePath, Charset defaultEncoding).");
    }

    /**
     * Returns whether the given stream contains data that is a.) a pdf and b.)
     * contains at least one BibEntry.
     */
    @Override
    public boolean isRecognizedFormat(Path filePath, Charset defaultEncoding) throws IOException {
        Objects.requireNonNull(filePath);
        return XMPUtil.hasMetadata(filePath, xmpPreferences);
    }

    @Override
    public String getId() {
        return "xmp";
    }

    @Override
    public String getDescription() {
        return "Wraps the XMPUtility function to be used as an Importer.";
    }

}
